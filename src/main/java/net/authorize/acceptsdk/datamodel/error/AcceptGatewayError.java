package net.authorize.acceptsdk.datamodel.error;

/**
 * Describes all error types, which can occurs during payment transactions.
 */
public enum AcceptGatewayError implements AcceptError {

  SDK_GATEWAY_ERROR_GENERAL("2999", "Unknown error.");

  private String errorCode;
  private String errorMessage;
  private String errorExtraMessage;

  AcceptGatewayError(String error, String message) {
    errorCode = error;
    errorMessage = message;
  }

  @Override public String getErrorCode() {
    return errorCode;
  }

  @Override public String getErrorMessage() {
    return errorMessage;
  }

  @Override public String getErrorExtraMessage() {
    return errorExtraMessage;
  }

  @Override public void setErrorExtraMessage(String extraMessage) {
    errorExtraMessage = extraMessage;
  }

  @Override public AcceptErrorType getErrorType() {
    return AcceptErrorType.SDK_ERROR_TYPE_GATEWAY;
  }

  public static AcceptGatewayError getGatewayErrorByErrorCode(String errorCode) {
    switch (errorCode) {
      case "2999":
        return AcceptGatewayError.SDK_GATEWAY_ERROR_GENERAL;
      default:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_GENERAL;
    }
  }
}
