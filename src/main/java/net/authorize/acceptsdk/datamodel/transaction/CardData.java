package net.authorize.acceptsdk.datamodel.transaction;

import java.io.Serializable;
import net.authorize.acceptsdk.ValidationCallback;
import net.authorize.acceptsdk.ValidationManager;
import net.authorize.acceptsdk.datamodel.error.SDKErrorCode;
import net.authorize.acceptsdk.datamodel.transaction.response.ErrorTransactionResponse;

/**
 * Created by Kiran Bollepalli on 07,July,2016.
 * kbollepa@visa.com
 */
public class CardData implements Serializable {

  private static final long serialVersionUID = 2L;
  private static final String MONTH_PREFIX = "0";
  private static final String YEAR_PREFIX = "20";

  //Required
  private String cardNumber;
  private String expirationMonth;
  private String expirationYear;

  //Optional
  private String cvvCode;
  private String zipCode;
  private String cardHolderName;

  /**
   * Creates an instance of object to store keyed card data. Also it sets a
   */
  private CardData(Builder builder) {
    this.cardNumber = trimString(builder.cardNumber);
    this.expirationMonth = prefixMonth(builder.expirationMonth);
    this.expirationYear = prefixYear(builder.expirationYear);
    this.cvvCode = trimString(builder.cvvCode);
    this.zipCode = trimString(builder.zipCode);
    this.cardHolderName = trimString(builder.cardHolderName);
  }

  private String trimString(String data) {
    if (ValidationManager.isValidString(data)) {
      data = data.trim();
    }
    return data;
  }

  private String prefixMonth(String month) {
    if (ValidationManager.isValidString(month)) {
      month = month.trim();
      if (month.length() == 1) month = MONTH_PREFIX + month;
    }

    return month;
  }

  private String prefixYear(String year) {
    if (ValidationManager.isValidString(year)) {
      year = year.trim();
      if (year.length() == 2) year = YEAR_PREFIX + year;
    }

    return year;
  }

  public boolean validateCardData(ValidationCallback callback) {
    boolean result = false;
    if (!ValidationManager.isValidCardNumber(cardNumber)) {
      callback.OnValidationFailed(
          ErrorTransactionResponse.createErrorResponse(SDKErrorCode.E_WC_05));
      return result;
    }
    if (!ValidationManager.isValidExpirationMonth(expirationMonth)) {
      callback.OnValidationFailed(
          ErrorTransactionResponse.createErrorResponse(SDKErrorCode.E_WC_06));
      return result;
    }

    if (!ValidationManager.isValidExpirationYear(expirationYear)) {
      callback.OnValidationFailed(
          ErrorTransactionResponse.createErrorResponse(SDKErrorCode.E_WC_07));
      return result;
    }

    if (!ValidationManager.isValidExpirationDate(expirationMonth, expirationYear)) {
		
      callback.OnValidationFailed(
          ErrorTransactionResponse.createErrorResponse(SDKErrorCode.E_WC_08));
      return result;
    }

    //COMMENT: since cvv,zip and card Holder name are optional, validate only if client
    // application provides value
    if (cvvCode != null && !ValidationManager.isValidCVV(cvvCode)) {
      callback.OnValidationFailed(
          ErrorTransactionResponse.createErrorResponse(SDKErrorCode.E_WC_15));
      return result;
    }

    if (zipCode != null && !ValidationManager.isValidZipCode(zipCode)) {
      callback.OnValidationFailed(
          ErrorTransactionResponse.createErrorResponse(SDKErrorCode.E_WC_16));
      return result;
    }
    if (cardHolderName != null && !ValidationManager.isValidCardHolderName(cardHolderName)) {
      callback.OnValidationFailed(
          ErrorTransactionResponse.createErrorResponse(SDKErrorCode.E_WC_17));
      return result;
    }

    // callback.OnValidationSuccessful();
    return true;
  }

  public String getCvvCode() {
    return cvvCode;
  }

  public void setCvvCode(String cvvCode) {
    this.cvvCode = cvvCode;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public String getCardHolderName() {
    return cardHolderName;
  }

  public void setCardHolderName(String cardHolderName) {
    this.cardHolderName = cardHolderName;
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public String getExpirationMonth() {
    return expirationMonth;
  }

  public void setExpirationMonth(String expirationMonth) {
    this.expirationMonth = expirationMonth;
  }

  public String getExpirationYear() {
    return expirationYear;
  }

  public void setExpirationYear(String expirationYear) {
    this.expirationYear = expirationYear;
  }

  public String getExpirationInFormatMMYYYY() {
    return expirationMonth + expirationYear;
  }

  public static class Builder {
    //	required
    private final String cardNumber;
    private final String expirationMonth;
    private final String expirationYear;
    private String cvvCode;
    private String zipCode;
    private String cardHolderName;

    public Builder(String cardNumber, String expirationMonth, String expirationYear) {
      this.cardNumber = cardNumber;
      this.expirationMonth = expirationMonth;
      this.expirationYear = expirationYear;
    }

    public CardData.Builder cvvCode(String cvvCode) {
      this.cvvCode = cvvCode;
      return this;
    }

    public CardData.Builder zipCode(String zipCode) {
      this.zipCode = zipCode;
      return this;
    }

    public CardData.Builder cardHolderName(String cardHolderName) {
      this.cardHolderName = cardHolderName;
      return this;
    }

    public CardData build() {
      return new CardData(this);
    }
  }
}
