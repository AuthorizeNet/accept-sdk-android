package net.authorize.acceptsdk.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import net.authorize.acceptsdk.datamodel.common.Message;
import net.authorize.acceptsdk.datamodel.common.ResponseMessages;
import net.authorize.acceptsdk.datamodel.merchant.ClientKeyBasedMerchantAuthentication;
import net.authorize.acceptsdk.datamodel.merchant.MerchantAuthenticationType;
import net.authorize.acceptsdk.datamodel.transaction.CardData;
import net.authorize.acceptsdk.datamodel.transaction.EncryptTransactionObject;
import net.authorize.acceptsdk.datamodel.transaction.response.EncryptTransactionResponse;
import net.authorize.acceptsdk.datamodel.transaction.response.ErrorTransactionResponse;
import net.authorize.acceptsdk.datamodel.transaction.response.TransactionResponse;
import net.authorize.acceptsdk.util.LogUtil;
import net.authorize.acceptsdk.util.LogUtil.LOG_LEVEL;
import net.authorize.acceptsdk.util.SDKUtils;
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

    // Json related to card data
    JSONObject tokenData = new JSONObject();
    CardData cardData = transactionObject.getCardData();
    tokenData.put(Card.CARD_NUMBER, cardData.getCardNumber());
    tokenData.put(Card.EXPIRATION_DATE, cardData.getExpirationInFormatMMYYYY());

    JSONObject data = new JSONObject();
    data.put(TYPE, TYPE_VALUE_TOKEN);
    data.put(ID, transactionObject.getGuid());
    data.put(TOKEN, tokenData);

    // Json related to merchant authentication
    JSONObject authentication = new JSONObject();
    authentication.put(Authentication.NAME,
        transactionObject.getMerchantAuthentication().getApiLoginID());

    MerchantAuthenticationType authenticationType =
        transactionObject.getMerchantAuthentication().getMerchantAuthenticationType();
    if (authenticationType == MerchantAuthenticationType.CLIENT_KEY) {
      ClientKeyBasedMerchantAuthentication clientKeyAuth =
          (ClientKeyBasedMerchantAuthentication) transactionObject.getMerchantAuthentication();
      authentication.put(Authentication.CLIENT_KEY, clientKeyAuth.getClientKey());
    } else if (authenticationType
        == MerchantAuthenticationType.FINGERPRINT) { //TODO : Need to implement this

    }

    JSONObject paymentContainer = new JSONObject();
    paymentContainer.put(MERCHANT_AUTHENTICATION, authentication);
    paymentContainer.put(DATA, data);

    JSONObject request = new JSONObject();
    request.put(CONTAINER_REQUEST, paymentContainer);
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

  /**
   * Creates AcceptError Object from error stream.
   *
   * @return {@link ErrorTransactionResponse}
   * @throws IOException
   */
  public static ErrorTransactionResponse createErrorResponse(int resultCode,
      InputStream errorStream) throws IOException {

    String errorString = SDKUtils.convertStreamToString(errorStream);
    LogUtil.log(LOG_LEVEL.INFO, errorString);
    Message message = new Message(String.valueOf(resultCode), errorString);
    return ErrorTransactionResponse.createErrorResponse(message);
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

    ResponseMessages responseMessages = new ResponseMessages(json.getString(RESULT_CODE));
    responseMessages.setMessageList(parseMessagesList(json.getJSONArray(MESSAGE)));
    return responseMessages;
  }

  private static List<Message> parseMessagesList(JSONArray jsonArray) throws JSONException {
    int arrayLength = jsonArray.length();
    List<Message> messageList = new ArrayList<Message>(arrayLength);
    for (int index = 0; index < arrayLength; index++) {
      messageList.add(parseMessage(jsonArray.getJSONObject(index)));
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
