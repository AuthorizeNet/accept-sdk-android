package net.authorize.acceptsdk.exception;

/**
 * Created by Kiran Bollepalli on 13,July,2016.
 * kbollepa@visa.com
 */
public class AcceptSDKException extends Exception {

  public static final String NULL_CONTEXT = "Context must not be null";
  public static final String NULL_TRANSACTION_OBJECT = "Transaction Object must not be null";
  private static final String GENERAL_EXCEPTION = "Internal Exception";

  public static final String APIKEY_ERROR = "API login ID cannot be null or empty";
  public static final String CLIENTKEY_ERROR = "Client key cannot be null or empty";
  public static final String FINGER_PRINT_ERROR = "Finger print cannot be null or empty";
  public static final String TRANSACTION_TYPE_ERROR = "TransactionType cannot be null";

  // Parameter less Constructor
  public AcceptSDKException() {
    super(GENERAL_EXCEPTION);
  }

  // Constructor that accepts a message
  public AcceptSDKException(String message) {
    super(message);
  }
}
