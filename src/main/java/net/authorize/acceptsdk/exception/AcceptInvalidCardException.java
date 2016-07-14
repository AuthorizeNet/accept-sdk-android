package net.authorize.acceptsdk.exception;

public class AcceptInvalidCardException extends AcceptSDKException {

  public static final String INVALID_CARD_NUMBER = "Invalid Card Number";

  public static final String INVALID_CARD_DATE_FORMAT = "Invalid Card Date format";
  public static final String INVALID_CARD_EXPIRATION_MONTH = "Invalid Card Expiration Month";

  public static final String INVALID_CARD_EXPIRATION_YEAR = "Invalid Card Expiration Year";

  public static final String INVALID_CARD_EXPIRATION_DATE = "Invalid Card Expiration Date";


  // Parameter less Constructor
  public AcceptInvalidCardException() {
    super(INVALID_CARD_NUMBER);
  }

  // Constructor that accepts a message
  public AcceptInvalidCardException(String message) {
    super(message);
  }
}