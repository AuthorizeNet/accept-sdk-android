package net.authorize.acceptsdk.datamodel.error;

/**
 * Created by Kiran Bollepalli on 15,July,2016.
 * kbollepa@visa.com
 */
public enum SDKErrorCode{

  SDK_INTERNAL_ERROR_TRANSACTION_IN_PROGRESS("1001", "Another transaction is already in progress."),
  SDK_INTERNAL_ERROR_PARSING("1002", "Cannot contact the server."),
  SDK_INTERNAL_ERROR_NETWORK_CONNECTION("1003", "Cannot access the network."),
  SDK_INTERNAL_ERROR_NETWORK_CONNECTION_TIMEOUT("1004", "Network connection timed out."),
  SDK_INTERNAL_ERROR_SERVER("1005", "Cannot contact the server."),
  SDK_INTERNAL_ERROR_GENERAL_FAILURE("1006", "Unknown general error occurred.");

  //E00000("E00000", "Unknown Error"),
  //E00001("E00001", "An error occurred during processing. Please try again."),
  //E00003("E00003", "Schema error"),
  //E00007("E00007", "User authentication failed due to invalid authentication values."),
  //E00059("E00059", "The authentication type is not allowed for this method call."),
  //E00073("E00073", "expirationDate is invalid"),
  //E00117("E00117", "Field validation error.");

  private String errorCode;
  private String errorMessage;


  SDKErrorCode(String errorCode, String errorMessage) {
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

   public String getErrorCode() {
    return errorCode;
  }

   public String getErrorMessage() {
    return errorMessage;
  }

}
