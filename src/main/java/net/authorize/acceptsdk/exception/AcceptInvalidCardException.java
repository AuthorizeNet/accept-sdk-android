package net.authorize.acceptsdk.exception;

public class AcceptInvalidCardException extends AcceptSDKException {

  public static final String INVALID_CARD_NUMBER = "Please provide valid card details.";

  public static final String INVALID_CARD_DATE_FORMAT = "Invalid Card Date format";
  public static final String INVALID_CARD_EXPIRATION_MONTH =
      "Please provide valid expiration month.";

  public static final String INVALID_CARD_EXPIRATION_YEAR = "Please provide valid expiration year.";

  public static final String INVALID_CARD_EXPIRATION_DATE =
      "Expiration date must be in the future.";

  public static final String INVALID_CVV = "Please provide valid CVV.";

  public static final String INVALID_ZIP = "Please provide valid Zip code.";
  public static final String INVALID_CARD_HOLDER_NAME = "Please provide valid card holder name.";

  // Parameter less Constructor
  public AcceptInvalidCardException() {
    super(INVALID_CARD_NUMBER);
  }

  // Constructor that accepts a message
  public AcceptInvalidCardException(String message) {
    super(message);
  }
}