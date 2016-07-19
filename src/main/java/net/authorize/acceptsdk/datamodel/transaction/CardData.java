package net.authorize.acceptsdk.datamodel.transaction;

import java.io.Serializable;
import net.authorize.acceptsdk.ValidationManager;
import net.authorize.acceptsdk.exception.AcceptInvalidCardException;

/**
 * Created by Kiran Bollepalli on 07,July,2016.
 * kbollepa@visa.com
 */
public class CardData implements Serializable {

  private static final long serialVersionUID = 2L;

  //Required
  private String cardNumber;
  private String expirationMonth;
  private String expirationYear;

  //Optional
  private String cvvCode;
  private String zipCode;
  private String cardHolderName;

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

  /**
   * Creates an instance of object to store keyed card data. Also it sets a
   */
  private CardData(Builder builder) throws AcceptInvalidCardException {
    if (ValidationManager.isValidCardNumber(builder.cardNumber)) {
      this.cardNumber = builder.cardNumber;
    }

    if (ValidationManager.isValidExpirationDate(builder.expirationMonth, builder.expirationYear)) {
      this.expirationMonth = builder.expirationMonth;
      this.expirationYear = builder.expirationYear;
    }

    if (builder.cvvCode != null && (ValidationManager.isValidCVV(builder.cvvCode))) {
      this.cvvCode = builder.cvvCode;
    }

    if (builder.zipCode != null && ValidationManager.isValidZipCode(builder.zipCode)) {
      this.zipCode = builder.zipCode;
    }
    if (builder.cardHolderName != null && ValidationManager.isValidCardHolderName(
        builder.cardHolderName)) {
      this.cardHolderName = builder.cardHolderName;
    }
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

    public CardData.Builder setCVVCode(String cvvCode) {
      this.cvvCode = cvvCode;
      return this;
    }

    public CardData.Builder setZipCode(String zipCode) {
      this.zipCode = zipCode;
      return this;
    }

    public CardData.Builder setCardHolderName(String cardHolderName) {
      this.cardHolderName = cardHolderName;
      return this;
    }

    public CardData build() throws AcceptInvalidCardException {
      return new CardData(this);
    }
  }
}
