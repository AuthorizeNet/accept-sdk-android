package net.authorize.acceptsdk;

public class AcceptInvalidCardException extends Exception {

  private static final String INVALID_CARD_NUMBER = "Invalid Card Number";

  // Parameterless Constructor
  public AcceptInvalidCardException() {
    super(INVALID_CARD_NUMBER);
  }

  // Constructor that accepts a message
  public AcceptInvalidCardException(String message) {
    super(message);
  }
}