package net.authorize.acceptsdk.parser;

import android.util.JsonReader;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import net.authorize.acceptsdk.datamodel.common.Message;
import net.authorize.acceptsdk.datamodel.common.ResponseMessages;
import net.authorize.acceptsdk.datamodel.merchant.ClientKeyBasedMerchantAuthentication;
import net.authorize.acceptsdk.datamodel.merchant.MerchantAuthenticationType;
import net.authorize.acceptsdk.datamodel.transaction.CardData;
import net.authorize.acceptsdk.datamodel.transaction.EncryptTransactionObject;
import net.authorize.acceptsdk.datamodel.transaction.response.EncryptTransactionResponse;
import org.json.JSONException;
import org.json.JSONObject;

import static net.authorize.acceptsdk.parser.JSONConstants.Authentication;
import static net.authorize.acceptsdk.parser.JSONConstants.CONTAINER_REQUEST;
import static net.authorize.acceptsdk.parser.JSONConstants.Card;
import static net.authorize.acceptsdk.parser.JSONConstants.DATA;
import static net.authorize.acceptsdk.parser.JSONConstants.DATA_DESCRIPTOR;
import static net.authorize.acceptsdk.parser.JSONConstants.DATA_VALUE;
import static net.authorize.acceptsdk.parser.JSONConstants.ID;
import static net.authorize.acceptsdk.parser.JSONConstants.MERCHANT_AUTHENTICATION;
import static net.authorize.acceptsdk.parser.JSONConstants.MESSAGES_LIST;
import static net.authorize.acceptsdk.parser.JSONConstants.MESSAGE_CODE;
import static net.authorize.acceptsdk.parser.JSONConstants.MESSAGE_TEXT;
import static net.authorize.acceptsdk.parser.JSONConstants.OPAQUE_DATA;
import static net.authorize.acceptsdk.parser.JSONConstants.RESULT_CODE;
import static net.authorize.acceptsdk.parser.JSONConstants.ResultCode;
import static net.authorize.acceptsdk.parser.JSONConstants.TOKEN;
import static net.authorize.acceptsdk.parser.JSONConstants.TYPE;
import static net.authorize.acceptsdk.parser.JSONConstants.TYPE_VALUE_TOKEN;

/**
 * Created by Kiran Bollepalli on 08,July,2016.
 * kbollepa@visa.com
 */
public class AcceptSDKParser {

  private static final String LOG_TAG = "AcceptSDKParser";

  public static String getJsonFromEncryptTransaction(EncryptTransactionObject transactionObject)
      throws JSONException {

    // Json related to data
    JSONObject tokenData = new JSONObject();
    CardData cardData = transactionObject.getCardData();
    tokenData.put(Card.CARD_NUMBER, cardData.getAccountNumber());
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

  public static String getResultCodeFromResponseStream(InputStream inputStream) throws IOException {
    String resultCode = ResultCode.ERROR;
    JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
    reader.beginObject();
    while (reader.hasNext()) {
      String name = reader.nextName();
      if (name.equals(MESSAGES_LIST)) {
        resultCode = getResultCode(reader);
      } else {
        reader.skipValue();
      }
    }
    reader.endObject();

    return resultCode;
  }

  private static String getResultCode(JsonReader reader) throws IOException {
    String resultCode = ResultCode.ERROR;
    reader.beginObject();

    while (reader.hasNext()) {
      String name = reader.nextName();
      if (name.equals(RESULT_CODE)) {
        resultCode = reader.nextString();
      } else {
        reader.skipValue();
      }
    }
    reader.endObject();
    Log.i(LOG_TAG, "Result code " + resultCode);

    return resultCode;
  }

  public static EncryptTransactionResponse createEncryptionResponse(InputStream inputStream)
      throws IOException {
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

    EncryptTransactionResponse response = new EncryptTransactionResponse();

    JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
    reader.beginObject();
    while (reader.hasNext()) {
      String name = reader.nextName();
      if (name.equals(OPAQUE_DATA)) {
        parseOpaqueSection(response, reader);
      } else if (name.equals(MESSAGES_LIST)) {
        parseResponseMessagesSection(response, reader);
      } else {
        reader.skipValue();
      }
    }
    reader.endObject();

    return response;
  }

  /**
   * parse Opaque section of response
   *
   * @throws IOException
   */
  private static void parseOpaqueSection(EncryptTransactionResponse response, JsonReader reader)
      throws IOException {
    /*
    //COMMENT: Sample Json section

    "opaqueData": {
        "dataDescriptor": "COMMON.ACCEPT.INAPP.PAYMENT",
        "dataValue": "9468313632506051305001"
     }
     */
    reader.beginObject();

    while (reader.hasNext()) {
      String name = reader.nextName();
      if (name.equals(DATA_DESCRIPTOR)) {
        response.setDataDescriptor(reader.nextString());
      } else if (name.equals(DATA_VALUE)) {
        response.setDataValue(reader.nextString());
      } else {
        reader.skipValue();
      }
    }
    reader.endObject();
    Log.i(LOG_TAG, "encypted code " + response.getDataValue());
  }

  private static void parseResponseMessagesSection(EncryptTransactionResponse response,
      JsonReader reader) throws IOException {
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
    ResponseMessages responseMessages = new ResponseMessages();
    reader.beginObject();

    while (reader.hasNext()) {
      String name = reader.nextName();
      if (name.equals(RESULT_CODE)) {
        responseMessages.setResultCode(reader.nextString());
      } else if (name.equals(MESSAGES_LIST)) {
        responseMessages.setMessageList(parseMessagesList(reader));
      } else {
        reader.skipValue();
      }
    }
    reader.endObject();

    response.setResponseMessages(responseMessages);
    Log.i(LOG_TAG, "responseMessages result code " + responseMessages.getResultCode());
  }

  private static List<Message> parseMessagesList(JsonReader reader) throws IOException {
    List<Message> messageList = new ArrayList<Message>();
    reader.beginArray();
    while (reader.hasNext()) {
      messageList.add(parseMessage(reader));
    }
    reader.endArray();

    Log.i(LOG_TAG, "Messages count " + messageList.size());

    return messageList;
  }

  private static Message parseMessage(JsonReader reader) throws IOException {
     /*
    //COMMENT: Sample Json  section
     {
        "code": "I00001",
        "text": "Successful."
      }
     */

    Message message = new Message();
    reader.beginObject();
    while (reader.hasNext()) {
      String name = reader.nextName();
      if (name.equals(MESSAGE_CODE)) {
        message.setMessageCode(reader.nextString());
      }
      if (name.equals(MESSAGE_TEXT)) {
        message.setMessageCode(reader.nextString());
      } else {
        reader.skipValue();
      }
    }//End of while

    reader.endObject();
    Log.i(LOG_TAG, " Message : " + message.toString());
    return message;
  }
}
