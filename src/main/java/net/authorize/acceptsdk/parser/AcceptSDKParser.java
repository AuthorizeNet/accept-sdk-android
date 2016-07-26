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
import org.json.JSONStringer;
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
   * @return String json String
   * @throws JSONException, exception will be thrown when creation of Json fails.
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
   * Method create Json Object for Encryption API call. Using this method order of insertion is
   * preserved.
   *
   * @param transactionObject encryption transaction Object. Also see {@link
   * EncryptTransactionObject}
   * @return String json String
   * @throws JSONException, exception will be thrown when creation of Json fails.
   */
  public static String getOrderedJsonFromEncryptTransaction(
      EncryptTransactionObject transactionObject) throws JSONException {

    // Json related to token section
    CardData cardData = transactionObject.getCardData();

    JSONStringer stringer = new JSONStringer().object();
    stringer.key(CONTAINER_REQUEST).object();
    prepareJsonForAuthenticationSection(stringer, transactionObject);
    stringer.key(DATA).object(); //Data section
    stringer.key(TYPE).value(TYPE_VALUE_TOKEN);
    stringer.key(ID).value(transactionObject.getGuid());
    prepareJsonForTokenSection(stringer, cardData);
    stringer.endObject();
    stringer.endObject();
    stringer.endObject();

    LogUtil.log(LOG_LEVEL.INFO, "getJsonFromEncryptTransaction : " + stringer.toString());
    return stringer.toString();
  }

  public static void prepareJsonForTokenSection(JSONStringer stringer, CardData cardData)
      throws JSONException {
    stringer.key(TOKEN).object();
    stringer.key(Card.CARD_NUMBER).value(cardData.getCardNumber());
    stringer.key(Card.EXPIRATION_DATE).value(cardData.getExpirationInFormatMMYYYY());
    if (cardData.getCvvCode() != null) stringer.key(Card.CARD_CODE).value(cardData.getCvvCode());
    if (cardData.getZipCode() != null) stringer.key(Card.ZIP).value(cardData.getZipCode());
    if (cardData.getCardHolderName() != null) {
      stringer.key(Card.CARD_HOLDER_NAME).value(cardData.getCardHolderName());
    }
    stringer.endObject();
  }

  public static void prepareJsonForAuthenticationSection(JSONStringer stringer,
      EncryptTransactionObject transactionObject) throws JSONException {
    String clientKey = null;
    FingerPrintData fData = null;
    String apiLoginId = transactionObject.getMerchantAuthentication().getApiLoginID();
    MerchantAuthenticationType authenticationType =
        transactionObject.getMerchantAuthentication().getMerchantAuthenticationType();
    if (authenticationType == MerchantAuthenticationType.CLIENT_KEY) {
      ClientKeyBasedMerchantAuthentication clientKeyAuth =
          (ClientKeyBasedMerchantAuthentication) transactionObject.getMerchantAuthentication();
      clientKey = clientKeyAuth.getClientKey();
    } else if (authenticationType == MerchantAuthenticationType.FINGERPRINT) {
      FingerPrintBasedMerchantAuthentication fingerPrintAuth =
          (FingerPrintBasedMerchantAuthentication) transactionObject.getMerchantAuthentication();
      fData = fingerPrintAuth.getFingerPrintData();
    }
    stringer.key(MERCHANT_AUTHENTICATION).object(); //Merchant Authentication section
    stringer.key(Authentication.NAME).value(apiLoginId);
    if (clientKey != null) {
      stringer.key(Authentication.CLIENT_KEY).value(clientKey);
    } else if (fData != null) {
      prepareJsonForFingerPrintSection(stringer, fData);
    }

    stringer.endObject();
  }

  public static void prepareJsonForFingerPrintSection(JSONStringer stringer, FingerPrintData fData)
      throws JSONException {
    stringer.key(Authentication.FINGER_PRINT).object(); //Fingerprint section
    stringer.key(FingerPrint.HASH_VALUE).value(fData.getHashValue());
    if (fData.getSequence() != null) stringer.key(FingerPrint.SEQUENCE).value(fData.getSequence());
    stringer.key(FingerPrint.TIME_STAMP).value(fData.getTimestampString());
    if (fData.getCurrencyCode() != null) {
      stringer.key(FingerPrint.CURRENCY_CODE).value(fData.getCurrencyCode());
    }
    if (fData.getAmountString() != null) {
      stringer.key(FingerPrint.AMOUNT).value(fData.getAmountString());
    }
    stringer.endObject();
  }

  /**
   * Method parses Json response and creates Transaction Response Object.
   * If transaction is successful, It creates {@link EncryptTransactionResponse} object.
   * If transaction is unsuccessful, it creates {@link TransactionResponse} Object which contains
   * error messages.
   *
   * @param json jsonString
   * @return TransactionResponse
   * @throws JSONException Json Parsing exception.
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
