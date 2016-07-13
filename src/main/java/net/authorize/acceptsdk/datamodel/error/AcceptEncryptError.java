package net.authorize.acceptsdk.datamodel.error;

import net.authorize.acceptsdk.datamodel.common.Message;

/**
 * Describes all error types, which can occurs during encryption.
 */
public enum AcceptEncryptError implements AcceptError {

  SDK_ENCRYPT_OTHER_GENERAL("E00003", "Schema error");

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

      case "E00003":
        return AcceptEncryptError.SDK_ENCRYPT_OTHER_GENERAL;
      default:
        return AcceptEncryptError.SDK_ENCRYPT_OTHER_GENERAL;
    }
  }
}
