package net.authorize.acceptsdk.datamodel.error;

public enum AcceptInternalError implements AcceptError {
  SDK_INTERNAL_ERROR_TRANSACTION_IN_PROGRESS("1001",
      "Another transaction is already in progress. Cancel/terminate previous transaction first."),
  SDK_INTERNAL_ERROR_PARSING("1002", "Cannot contact the server."),
  SDK_INTERNAL_ERROR_NETWORK_CONNECTION("1003", "Cannot access the network."),
  SDK_INTERNAL_ERROR_NETWORK_CONNECTION_TIMEOUT("1004", "Network connection timed out."),
  SDK_INTERNAL_ERROR_SERVER("1005", "Cannot contact the server."),
  SDK_INTERNAL_ERROR_GENERAL_FAILURE("1006", "Unknown general error occurred.");

  private String errorCode;
  private String errorMessage;
  private String errorExtraMessage;

  AcceptInternalError(String errorCode, String errorMessage) {
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
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

  @Override public void setErrorExtraMessage(String errorExtraMessage) {
    this.errorExtraMessage = errorExtraMessage;
  }

  @Override public AcceptErrorType getErrorType() {
    return AcceptErrorType.SDK_ERROR_TYPE_INTERNAL;
  }

  public static AcceptInternalError getInternalErrorByErrorCode(String errorCode) {
    switch (errorCode) {
      case "1001":
        return AcceptInternalError.SDK_INTERNAL_ERROR_TRANSACTION_IN_PROGRESS;
      case "1002":
        return AcceptInternalError.SDK_INTERNAL_ERROR_PARSING;
      case "1003":
        return AcceptInternalError.SDK_INTERNAL_ERROR_NETWORK_CONNECTION;
      case "1004":
        return AcceptInternalError.SDK_INTERNAL_ERROR_NETWORK_CONNECTION_TIMEOUT;
      case "1005":
        return AcceptInternalError.SDK_INTERNAL_ERROR_SERVER;
      default:
        return AcceptInternalError.SDK_INTERNAL_ERROR_GENERAL_FAILURE;
    }
  }
}
