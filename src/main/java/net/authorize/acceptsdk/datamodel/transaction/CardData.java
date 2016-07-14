package net.authorize.acceptsdk.datamodel.transaction;

import java.io.Serializable;
import net.authorize.acceptsdk.exception.AcceptInvalidCardException;
import net.authorize.acceptsdk.ValidationManager;

/**
 * Created by Kiran Bollepalli on 07,July,2016.
 * kbollepa@visa.com
 */
public class CardData implements Serializable {

  private static final long serialVersionUID = 2L;

  //	required
  private String cardNumber;
  private String expirationMonth;
  private String expirationYear;

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

    public Builder(String cardNumber, String expirationMonth, String expirationYear) {
      this.cardNumber = cardNumber;
      this.expirationMonth = expirationMonth;
      this.expirationYear = expirationYear;
    }

    public CardData build() throws AcceptInvalidCardException {
      return new CardData(this);
    }
  }
}
