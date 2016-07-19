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
  public static final String INVALID_FINGER_PRINT = "Invalid Fingerprint";
  public static final String INVALID_AMOUNT = "Invalid Amount. Only 2 decimal values are allowed";

  public static final String INVALID_FINGER_PRINT_HASH_VALUE = "Fingerprint hash should not be blank.";
  public static final String INVALID_TIME_STAMP = "Please provide valid timestamp in utc.";
  public static final String INVALID_SEQUENCE= "Sequence attribute should not be blank.";

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
