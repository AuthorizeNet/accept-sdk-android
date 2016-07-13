package net.authorize.acceptsdk.parser;

/**
 * Describes all Json strings, which are used in API's.
 *
 * Created by Kiran Bollepalli on 12,July,2016.
 * kbollepa@visa.com
 */
public final class JSONConstants {


  /*
    Sample Request :
    {
        "securePaymentContainerRequest": {
        "merchantAuthentication": {
            "name": "5KP3u95bQpv",
                    "clientKey": "5FcB6WrfHGS76gHW3v7btBCE3HuuBuke9Pj96Ztfn5R32G5ep42vne7MCWZtAucY"
        },
        "data": {
            "type": "TOKEN",
                    "id": "ac210aef-cf2d-656c-69a5-a8e145d8f1fc",
                    "token": {
                "cardNumber": "378282246310005",
                        "expirationDate": "122021"
            }
        }
    }
    }

    Sample Success Response :

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

  /* Request related JSON Strings */
  public static final String CONTAINER_REQUEST = "securePaymentContainerRequest";
  public static final String MERCHANT_AUTHENTICATION = "merchantAuthentication";
  public static final String DATA = "data";
  public static final String TYPE = "type";
  public static final String TYPE_VALUE_TOKEN = "TOKEN";
  public static final String ID = "id";
  public static final String TOKEN = "token";

  public interface Authentication {
    String NAME = "name";
    String CLIENT_KEY = "clientKey";
  }

  public interface Card {
    String CARD_NUMBER = "cardNumber";
    String EXPIRATION_DATE = "expirationDate";
  }

  /* Response related JSON Strings */
  public static final String RESULT_CODE = "resultCode";

  public interface ResultCode {
    String OK = "Ok";
    String ERROR = "Error";
  }

  public static final String OPAQUE_DATA = "opaqueData";
  public static final String DATA_DESCRIPTOR = "dataDescriptor";
  public static final String DATA_VALUE = "dataValue";
  public static final String MESSAGES_LIST = "messages";
  public static final String MESSAGE = "message";
  public static final String MESSAGE_CODE = "code";
  public static final String MESSAGE_TEXT = "text";
}
