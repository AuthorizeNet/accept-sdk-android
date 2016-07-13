package net.authorize.acceptsdk.exception;

/**
 * Created by Kiran Bollepalli on 13,July,2016.
 * kbollepa@visa.com
 */
public class AcceptSDKException extends Exception {

  public static final String NULL_CONTEXT = "Context must not be null";
  public static final String NULL_TRANSACTION_OBJECT = "Transaction Object must not be null";
  private static final String GENERAL_EXCEPTION = "Internal Exception";

  public static final String NULL_APIKEY_CLIENT_KEY = "API login ID or Client Key can not be null";
  public static final String NULL_APIKEY_FINGER_PRINT =
      "API login ID or Finger print can not be null";

  // Parameter less Constructor
  public AcceptSDKException() {
    super(GENERAL_EXCEPTION);
  }

  // Constructor that accepts a message
  public AcceptSDKException(String message) {
    super(message);
  }
}
