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
  SDK_INTERNAL_ERROR_GENERAL_FAILURE("1006", "Unknown general error occurred."),

  //E_WC_01("E_WC_01" , "Please include Accept.js library from cdn."),
  E_WC_02("E_WC_02", "A HTTPS connection is required."),
 // E_WC_03("E_WC_03" , "Accept.js is not loaded correctly."),
  E_WC_04("E_WC_04" , "Please provide mandatory field to library."),
  E_WC_05("E_WC_05" , "Please provide valid credit card number."),
  E_WC_06("E_WC_06" , "Please provide valid expiration month."),
  E_WC_07("E_WC_07" , "Please provide valid expiration year."),
  E_WC_08("E_WC_08" , "Expiration date must be in the future."),
  E_WC_09("E_WC_09" , "Fingerprint hash should not be blank."),
  E_WC_10("E_WC_10" , "Please provide valid api login id."),
  E_WC_11("E_WC_11" , "Please provide valid timestamp in utc."),
  E_WC_12("E_WC_12" , "Sequence attribute should not be blank."),
  E_WC_13("E_WC_13" , "Invalid Fingerprint."),
  E_WC_14("E_WC_14" , "Accept encryption failed."),
  E_WC_15("E_WC_15" , "Please provide valid CVV."),
  E_WC_16("E_WC_16" , "Please provide valid Zip code."),
  E_WC_17("E_WC_17" , "Please provide valid card holder name."),
  E_WC_18("E_W_18" , "Client key is required.");

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
