package net.authorize.acceptsdk.datamodel.transaction;

import java.io.Serializable;
import net.authorize.acceptsdk.AcceptInvalidCardException;
import net.authorize.acceptsdk.ValidationManager;

/**
 * Created by Kiran Bollepalli on 07,July,2016.
 * kbollepa@visa.com
 */
public class CardData implements Serializable {

  private static final long serialVersionUID = 2L;

  //	required
  private String accountNumber;
  private String expirationMonth;
  private String expirationYear;

  /**
   * Creates an instance of object to store keyed card data. Also it sets a
   */
  private CardData(Builder builder) throws AcceptInvalidCardException {
    if (ValidationManager.isValidCardNumber(builder.accountNumber)) {
      this.accountNumber = builder.accountNumber;
    }

    if (ValidationManager.isValidExpirationDate(builder.expirationMonth, builder.expirationYear)) {
      this.expirationMonth = builder.expirationMonth;
      this.expirationYear = builder.expirationYear;
    }
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
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
    private final String accountNumber;
    private final String expirationMonth;
    private final String expirationYear;

    public Builder(String accountNumber, String expirationMonth, String expirationYear) {
      this.accountNumber = accountNumber;
      this.expirationMonth = expirationMonth;
      this.expirationYear = expirationYear;
    }

    public CardData build() throws AcceptInvalidCardException {
      return new CardData(this);
    }
  }
}
