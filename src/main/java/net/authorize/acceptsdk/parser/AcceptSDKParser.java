package net.authorize.acceptsdk.parser;

import java.util.ArrayList;
import java.util.List;
import net.authorize.acceptsdk.datamodel.common.Message;
import net.authorize.acceptsdk.datamodel.common.ResponseMessages;
import net.authorize.acceptsdk.datamodel.error.SDKErrorCode;
import net.authorize.acceptsdk.datamodel.merchant.ClientKeyBasedMerchantAuthentication;
import net.authorize.acceptsdk.datamodel.merchant.FingerPrintBasedMerchantAuthentication;
import net.authorize.acceptsdk.datamodel.merchant.FingerPrintData;
import net.authorize.acceptsdk.datamodel.merchant.MerchantAuthenticationType;
import net.authorize.acceptsdk.datamodel.transaction.CardData;
import net.authorize.acceptsdk.datamodel.transaction.EncryptTransactionObject;
import net.authorize.acceptsdk.datamodel.transaction.response.EncryptTransactionResponse;
import net.authorize.acceptsdk.datamodel.transaction.response.ErrorTransactionResponse;
import net.authorize.acceptsdk.datamodel.transaction.response.TransactionResponse;
import net.authorize.acceptsdk.util.LogUtil;
import net.authorize.acceptsdk.util.LogUtil.LOG_LEVEL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import static net.authorize.acceptsdk.parser.JSONConstants.Authentication;
import static net.authorize.acceptsdk.parser.JSONConstants.CONTAINER_REQUEST;
import static net.authorize.acceptsdk.parser.JSONConstants.Card;
import static net.authorize.acceptsdk.parser.JSONConstants.DATA;
import static net.authorize.acceptsdk.parser.JSONConstants.DATA_DESCRIPTOR;
import static net.authorize.acceptsdk.parser.JSONConstants.DATA_VALUE;
import static net.authorize.acceptsdk.parser.JSONConstants.FingerPrint;
import static net.authorize.acceptsdk.parser.JSONConstants.ID;
import static net.authorize.acceptsdk.parser.JSONConstants.MERCHANT_AUTHENTICATION;
import static net.authorize.acceptsdk.parser.JSONConstants.MESSAGE;
import static net.authorize.acceptsdk.parser.JSONConstants.MESSAGES_LIST;
import static net.authorize.acceptsdk.parser.JSONConstants.MESSAGE_CODE;
import static net.authorize.acceptsdk.parser.JSONConstants.MESSAGE_TEXT;
import static net.authorize.acceptsdk.parser.JSONConstants.OPAQUE_DATA;
import static net.authorize.acceptsdk.parser.JSONConstants.RESULT_CODE;
import static net.authorize.acceptsdk.parser.JSONConstants.TOKEN;
import static net.authorize.acceptsdk.parser.JSONConstants.TYPE;
import static net.authorize.acceptsdk.parser.JSONConstants.TYPE_VALUE_TOKEN;

/**
 * This is utility class for Json Parsing.It creates Json and parse Json.
 *
 * Created by Kiran Bollepalli on 08,July,2016.
 * kbollepa@visa.com
 */
public class AcceptSDKParser {

  /**
   * Method create Json Object for Encryption API call.
   *
   * @param transactionObject encryption transaction Object. Also see {@link
   * EncryptTransactionObject}
   * @throws JSONException
   */
  public static String getJsonFromEncryptTransaction(EncryptTransactionObject transactionObject)
      throws JSONException {

    // Json related to token section
    CardData cardData = transactionObject.getCardData();
    JSONObject tokenData = new JSONObject();
    tokenData.put(Card.CARD_NUMBER, cardData.getCardNumber());
    tokenData.put(Card.EXPIRATION_DATE, cardData.getExpirationInFormatMMYYYY());
    //COMMENT: Optional fields
    if (cardData.getCvvCode() != null) tokenData.put(Card.CARD_CODE, cardData.getCvvCode());
    if (cardData.getZipCode() != null) tokenData.put(Card.ZIP, cardData.getZipCode());
    if (cardData.getCardHolderName() != null) {
      tokenData.put(Card.CARD_HOLDER_NAME, cardData.getCardHolderName());
    }
   // Json Related to data section.
    JSONObject data = new JSONObject();
    data.put(TYPE, TYPE_VALUE_TOKEN);
    data.put(ID, transactionObject.getGuid());
    data.put(TOKEN, tokenData);

    // Json related to merchant authentication section.
    JSONObject authentication = new JSONObject();
    authentication.put(Authentication.NAME,
        transactionObject.getMerchantAuthentication().getApiLoginID());

    MerchantAuthenticationType authenticationType =
        transactionObject.getMerchantAuthentication().getMerchantAuthenticationType();
    if (authenticationType == MerchantAuthenticationType.CLIENT_KEY) {
      ClientKeyBasedMerchantAuthentication clientKeyAuth =
          (ClientKeyBasedMerchantAuthentication) transactionObject.getMerchantAuthentication();
      authentication.put(Authentication.CLIENT_KEY, clientKeyAuth.getClientKey());
    } else if (authenticationType == MerchantAuthenticationType.FINGERPRINT) {
      FingerPrintBasedMerchantAuthentication fingerPrintAuth =
          (FingerPrintBasedMerchantAuthentication) transactionObject.getMerchantAuthentication();
      // Json related to finger print data
      JSONObject fDataJson = new JSONObject();
      FingerPrintData fData = fingerPrintAuth.getFingerPrintData();
      fDataJson.put(FingerPrint.HASH_VALUE, fData.getHashValue());
      fDataJson.put(FingerPrint.TIME_STAMP, fData.getTimestampString());

      //Optional fields
      if (fData.getSequence() != null) {
        fDataJson.put(FingerPrint.SEQUENCE, fData.getSequence());
      }
      if (fData.getCurrencyCode() != null) {
        fDataJson.put(FingerPrint.CURRENCY_CODE, fData.getCurrencyCode());
      }
      if (fData.getAmountString() != null) {
        fDataJson.put(FingerPrint.AMOUNT, fData.getAmountString());
      }
      authentication.put(Authentication.FINGER_PRINT, fDataJson);
    }

    JSONObject paymentContainer = new JSONObject();
    paymentContainer.put(MERCHANT_AUTHENTICATION, authentication);
    paymentContainer.put(DATA, data);

    JSONObject request = new JSONObject();
    request.put(CONTAINER_REQUEST, paymentContainer);

    LogUtil.log(LOG_LEVEL.INFO, "getJsonFromEncryptTransaction : " + request.toString());
    return request.toString();
  }

  /**
   * Method parses Json response and creates Transaction Response Object.
   * If transaction is successful, It creates {@link EncryptTransactionResponse} object.
   * If transaction is unsuccessful, it creates {@link TransactionResponse} Object which contains
   * error messages.
   *
   * @return TransactionResponse
   * @throws JSONException
   */
  public static TransactionResponse createEncryptionTransactionResponse(String json)
      throws JSONException {
   /*
    //COMMENT: Sample Json section

   {
      "opaqueData": {
        "dataDescriptor": "COMMON.ACCEPT.INAPP.PAYMENT",
        "dataValue": "9468313632506051305001"
      },
      "messages": {
        "resultCode": "Ok",
        "message": [
          {
            "code": "I00001",
            "text": "Successful."
          }
        ]
      }
   }
   */

    JSONObject responseObject = (JSONObject) new JSONTokener(json).nextValue();
    if (responseObject.has(OPAQUE_DATA)) {
      EncryptTransactionResponse encryptTransactionResponse = new EncryptTransactionResponse();
      parseOpaqueSection(encryptTransactionResponse, responseObject.getJSONObject(OPAQUE_DATA));
      encryptTransactionResponse.setResponseMessages(
          parseResponseMessagesSection(responseObject.getJSONObject(MESSAGES_LIST)));
      return encryptTransactionResponse;
    } else {
      ResponseMessages responseMessages =
          parseResponseMessagesSection(responseObject.getJSONObject(MESSAGES_LIST));
      ErrorTransactionResponse response = new ErrorTransactionResponse(responseMessages);
      return response;
    }
  }

  private static void parseOpaqueSection(EncryptTransactionResponse response, JSONObject json)
      throws JSONException {
 /*
    //COMMENT: Sample Json section

    "opaqueData": {
        "dataDescriptor": "COMMON.ACCEPT.INAPP.PAYMENT",
        "dataValue": "9468313632506051305001"
     }
     */
    response.setDataDescriptor(json.getString(DATA_DESCRIPTOR));
    response.setDataValue(json.getString(DATA_VALUE));
  }

  private static ResponseMessages parseResponseMessagesSection(JSONObject json)
      throws JSONException {
     /*
    //COMMENT: Sample Json section
     "messages": {
          "resultCode": "Ok",
          "message": [
              {
                "code": "I00001",
                "text": "Successful."
              }
           ]
        }
    */

    String resultCode = json.getString(RESULT_CODE);
    boolean isErrorResponse = false;
    if (resultCode.equals(JSONConstants.ResultCode.ERROR)) isErrorResponse = true;
    ResponseMessages responseMessages = new ResponseMessages(json.getString(RESULT_CODE));
    responseMessages.setMessageList(parseMessagesList(isErrorResponse, json.getJSONArray(MESSAGE)));
    return responseMessages;
  }

  private static List<Message> parseMessagesList(boolean isErrorResponse, JSONArray jsonArray)
      throws JSONException {

    int arrayLength = jsonArray.length();
    List<Message> messageList = new ArrayList<Message>(arrayLength);
    for (int index = 0; index < arrayLength; index++) {
      Message message = parseMessage(jsonArray.getJSONObject(index));
      if (isErrorResponse) {    //COMMENT: if response is of error change api error code to E_WC_14.
        message.setMessageCode(SDKErrorCode.E_WC_14.getErrorCode());
      } else { //COMMENT: if response is of error change api error code to I_WC_01.
        message.setMessageCode("I_WC_01");
      }
      messageList.add(message);
    }
    return messageList;
  }

  private static Message parseMessage(JSONObject json) throws JSONException {
    Message message = new Message();
    message.setMessageCode(json.getString(MESSAGE_CODE));
    message.setMessageText(json.getString(MESSAGE_TEXT));
    return message;
  }
}
