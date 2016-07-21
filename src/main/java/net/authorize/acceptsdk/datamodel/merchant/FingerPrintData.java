package net.authorize.acceptsdk.datamodel.merchant;

import java.io.Serializable;
import net.authorize.acceptsdk.ValidationCallback;
import net.authorize.acceptsdk.ValidationManager;
import net.authorize.acceptsdk.datamodel.error.SDKErrorCode;
import net.authorize.acceptsdk.datamodel.transaction.response.ErrorTransactionResponse;

/**
 * Class of FingerPrint Data
 *
 *
 * Created by Kiran Bollepalli on 07,July,2016.
 * kbollepa@visa.com
 */
public class FingerPrintData implements Serializable {

  //Mandatory
  private String hashValue;
  private long timestamp;

  //Optional
  private String sequence;
  private String currencyCode;
  private double amount;

  public FingerPrintData(Builder builder) {

    this.hashValue = builder.hashValue;
    if (hashValue != null) hashValue = hashValue.trim();

    this.timestamp = builder.timestamp;

    this.sequence = builder.sequence;
    if (sequence != null) sequence = sequence.trim();

    this.currencyCode = builder.currencyCode;
    if (currencyCode != null) currencyCode = currencyCode.trim();

    this.amount = builder.amount;
  }

  /**
   * Validates Finger Print details
   *
   * @param callback {@link ValidationCallback}
   * @return boolean true, if it is success. false if validation fails.
   */
  public boolean validateFingerPrint(ValidationCallback callback) {
    if (!ValidationManager.isValidString(hashValue)) {
      callback.OnValidationFailed(
          ErrorTransactionResponse.createErrorResponse(SDKErrorCode.E_WC_09));
      return false;
    }

    if (!ValidationManager.isValidTimeStamp(timestamp)) {
      callback.OnValidationFailed(
          ErrorTransactionResponse.createErrorResponse(SDKErrorCode.E_WC_11));
      return false;
    }

   /*COMMENT: Since Currency code, amount and sequence are optional,
    validate only if value is not null(i.e., provided by client app).
    */
    if (sequence != null && sequence.isEmpty()) {
      callback.OnValidationFailed(
          ErrorTransactionResponse.createErrorResponse(SDKErrorCode.E_WC_12));
      return false;
    }

    //FIXME :  currency code and amount don't have validation error mapping.

    if (currencyCode != null && currencyCode.isEmpty()) {
      callback.OnValidationFailed(
          ErrorTransactionResponse.createErrorResponse(SDKErrorCode.E_WC_13));
      return false;
    }

    if (amount != 0.0 && !ValidationManager.isValidAmount(amount)) {
      callback.OnValidationFailed(
          ErrorTransactionResponse.createErrorResponse(SDKErrorCode.E_WC_13));
      return false;
    }

    return true;
  }

  public String getHashValue() {
    return hashValue;
  }

  public void setHashValue(String hashValue) {
    this.hashValue = hashValue;
  }

  public double getAmount() {
    return amount;
  }

  public String getAmountString() {
    return String.valueOf(amount);
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public String getTimestampString() {
    return String.valueOf(timestamp);
  }

  public String getSequence() {
    return sequence;
  }

  public void setSequence(String sequence) {
    this.sequence = sequence;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  /**
   * Builder class for FingerPrint Class
   */
  public static class Builder {
    private String hashValue;
    private long timestamp;
    private String sequence;
    private String currencyCode;
    private double amount;

    /**
     * Builder constructor
     *
     * @param hashValue hash value of Fingerprint
     * @param timestamp Time stamp
     */
    public Builder(String hashValue, long timestamp) {
      this.timestamp = timestamp;
      this.hashValue = hashValue;
    }

    public FingerPrintData.Builder setSequence(String sequence) {
      this.sequence = sequence;
      return this;
    }

    public FingerPrintData.Builder setCurrencyCode(String currencyCode) {
      this.currencyCode = currencyCode;
      return this;
    }

    public FingerPrintData.Builder setAmount(double amount) {
      this.amount = amount;
      return this;
    }

    public FingerPrintData build() {
      return new FingerPrintData(this);
    }
  }
}
