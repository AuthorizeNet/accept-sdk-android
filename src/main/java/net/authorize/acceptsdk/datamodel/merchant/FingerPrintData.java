package net.authorize.acceptsdk.datamodel.merchant;

import java.io.Serializable;
import net.authorize.acceptsdk.ValidationCallback;
import net.authorize.acceptsdk.ValidationManager;
import net.authorize.acceptsdk.datamodel.error.SDKErrorCode;
import net.authorize.acceptsdk.datamodel.transaction.response.ErrorTransactionResponse;

/**
 * POJO Class of FingerPrint Data
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
    this.timestamp = builder.timestamp;
    this.sequence = builder.sequence;
    this.currencyCode = builder.currencyCode;
    this.amount = builder.amount;
  }

  public boolean validateFingerPrint(ValidationCallback callback) {
    if (!ValidationManager.isValidString(hashValue)) {
      callback.OnValidationFailed(
          ErrorTransactionResponse.createErrorResponse(SDKErrorCode.E_WC_09));
      return false;
    }
    if (!ValidationManager.isValidString(getTimestampString())) {
      callback.OnValidationFailed(
          ErrorTransactionResponse.createErrorResponse(SDKErrorCode.E_WC_11));
      return false;
    }
    if (sequence != null && !ValidationManager.isValidString(sequence)) {
      callback.OnValidationFailed(
          ErrorTransactionResponse.createErrorResponse(SDKErrorCode.E_WC_12));
      return false;
    }
    //FIXME :  currency code and amount dont have validation error mapping.
    if (currencyCode != null && !ValidationManager.isValidString(currencyCode)) {
      callback.OnValidationFailed(
          ErrorTransactionResponse.createErrorResponse(SDKErrorCode.E_WC_13));
      return false;
    }

    if (getAmountString() != null && !ValidationManager.isValidAmount(getAmountString())) {
      callback.OnValidationFailed(
          ErrorTransactionResponse.createErrorResponse(SDKErrorCode.E_WC_13));
      return false;
    }

//    callback.OnValidationSuccessful();
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

  public static class Builder {
    private String hashValue;
    private long timestamp;
    private String sequence;
    private String currencyCode;
    private double amount;

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
