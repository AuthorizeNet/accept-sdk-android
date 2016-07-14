package net.authorize.acceptsdk.datamodel.error;

/**
 * Describes all error types, which can occurs during encryption.
 */
public enum AcceptEncryptError implements AcceptError {
  E00000("E00000", "Unknown Error"),
  E00001("E00001", "An error occurred during processing. Please try again."),
  E00003("E00003", "Schema error"),
  E00007("E00007", "User authentication failed due to invalid authentication values."),
  E00059("E00059", "The authentication type is not allowed for this method call."),
  E00073("E00073", "expirationDate is invalid"),
  E00117("E00117", "Field validation error.");

  private String errorCode;
  private String errorMessage;
  private String errorExtraMessage;

  AcceptEncryptError(String code, String message) {
    errorCode = code;
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
    return AcceptErrorType.SDK_ERROR_TYPE_ENCRYPTION;
  }

  public static AcceptEncryptError getEncryptErrorByErrorCode(String errorCode) {
    switch (errorCode) {
      case "E00001":
        return AcceptEncryptError.E00001;
      case "E00003":
        return AcceptEncryptError.E00003;
      case "E00007":
        return AcceptEncryptError.E00007;
      case "E00059":
        return AcceptEncryptError.E00059;
      case "E00073":
        return AcceptEncryptError.E00073;
      case "E00117":
        return AcceptEncryptError.E00117;
      default:
        return AcceptEncryptError.E00000;
    }
  }
}
