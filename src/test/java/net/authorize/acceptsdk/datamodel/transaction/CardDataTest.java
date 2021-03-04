package net.authorize.acceptsdk.datamodel.transaction;

import junit.framework.Assert;
import net.authorize.acceptsdk.ValidationCallback;
import net.authorize.acceptsdk.datamodel.transaction.response.ErrorTransactionResponse;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Kiran Bollepalli on 14,July,2016.
 * kbollepa@visa.com
 */
public class CardDataTest {

  private String cardNumber = "4111111111111111";
  private String month = "11";
  private String year = "2050";
  private String cvvCode = "111";
  private String zipCode = "bangalore";
  private String cardHolderName = "kiran bollepalli";

  ValidationCallback callBack;

  @Before public void setUp() throws Exception {
    callBack = new ValidationCallback() {
      @Override public void OnValidationSuccessful() {

      }

      @Override public void OnValidationFailed(ErrorTransactionResponse errorTransactionResponse) {

      }
    };
  }

  @Test public void testCardNumberForSuccess() {
    cardNumber = "4111111111111111";
    month = "11";
    year = "2050";
    CardData card = new CardData.Builder(cardNumber, month, year).cvvCode("111")
        .zipCode("abc")
        .cardHolderName("kiran")
        .build();
    Assert.assertEquals(true, card.validateCardData(callBack));
  }

  @Test public void testCardNumberForSuccess2() {
    cardNumber = "4111111111111111";
    month = "11";
    year = "50";
    CardData card = new CardData.Builder(cardNumber, month, year).build();
    Assert.assertEquals(true, card.validateCardData(callBack));
  }

  @Test public void testCardNumberForSuccess3() {
    cardNumber = "4111111111111111";
    month = "1";
    year = "50";
    CardData card = new CardData.Builder(cardNumber, month, year).build();
    Assert.assertEquals(true, card.validateCardData(callBack));
  }

  @Test public void testCardNumberForNull() {
    cardNumber = null;
    month = "11";
    year = "2050";
    CardData card = new CardData.Builder(cardNumber, month, year).build();
    Assert.assertEquals(false, card.validateCardData(callBack));
  }

  @Test public void testMonthForNull() {
    cardNumber = "4111111111111111";
    month = null;
    year = "2050";
    CardData card = new CardData.Builder(cardNumber, month, year).build();
    Assert.assertEquals(false, card.validateCardData(callBack));
  }

  @Test public void testYearForNull() {
    cardNumber = "4111111111111111";
    month = "1";
    year = null;
    CardData card = new CardData.Builder(cardNumber, month, year).build();
    Assert.assertEquals(false, card.validateCardData(callBack));
  }

  @Test public void testCardNumberMinimumLength() {
    cardNumber = "4111";
    month = "11";
    year = "2050";
    CardData card = new CardData.Builder(cardNumber, month, year).build();
    Assert.assertEquals(false, card.validateCardData(callBack));
  }

  @Test public void testCardNumberMaximumLength() {
    cardNumber = "411111111111111111111111111111111111111";
    month = "11";
    year = "2050";
    CardData card = new CardData.Builder(cardNumber, month, year).build();
    Assert.assertEquals(false, card.validateCardData(callBack));
  }

  @Test public void testCardNumberForError() {
    cardNumber = "41111AAAAA";
    month = "11";
    year = "2050";
    CardData card = new CardData.Builder(cardNumber, month, year).build();
    Assert.assertEquals(false, card.validateCardData(callBack));
  }

  @Test public void testExpirationMonth() {
    cardNumber = "4111111111111111";
    month = "aa";
    year = "2050";
    CardData card = new CardData.Builder(cardNumber, month, year).build();
    Assert.assertEquals(false, card.validateCardData(callBack));
  }

  @Test public void testCVVForError() {
    cardNumber = "4111111111111111";
    month = "11";
    year = "2050";
    cvvCode = "111a";
    CardData card = new CardData.Builder(cardNumber, month, year).cvvCode(cvvCode).build();
    Assert.assertEquals(false, card.validateCardData(callBack));
  }

  @Test public void testZip() {
    cardNumber = "4111111111111111";
    month = "11";
    year = "2050";
    cvvCode = "111";
    zipCode = "b";
    CardData card = new CardData.Builder(cardNumber, month, year).cvvCode(cvvCode)
        .zipCode(zipCode)
        .build();
    Assert.assertEquals(true, card.validateCardData(callBack));
  }

  @Test public void testZipForError() {
    cardNumber = "4111111111111111";
    month = "11";
    year = "2050";
    cvvCode = "111";
    zipCode = "   ";
    CardData card = new CardData.Builder(cardNumber, month, year).cvvCode(cvvCode)
        .zipCode(zipCode)
        .build();
    Assert.assertEquals(false, card.validateCardData(callBack));
  }

  @Test public void testCardHolderName() {
    cardNumber = "4111111111111111";
    month = "11";
    year = "2050";
    cvvCode = "111";
    zipCode = "bangalore";
    cardHolderName = "kiran bollepalli";
    CardData card = new CardData.Builder(cardNumber, month, year).cvvCode(cvvCode)
        .zipCode(zipCode)
        .cardHolderName(cardHolderName)
        .build();
    Assert.assertEquals(true, card.validateCardData(callBack));
  }

  @Test public void testCardHolderNameForError() {
    cardNumber = "4111111111111111";
    month = "11";
    year = "2050";
    cvvCode = "111";
    zipCode = "bangalore";
    cardHolderName =
        "kiran bollepalli afafakfhaskjfadjsfjasdfhasdfadsjfdasjfhasdfhasdhfjhadsfhadfhfjadfn"
            + " sdfadffasdfadsfasdfasdfa adsfasdfadsfdasfa";
    CardData card = new CardData.Builder(cardNumber, month, year).cvvCode(cvvCode)
        .zipCode(zipCode)
        .cardHolderName(cardHolderName)
        .build();
    Assert.assertEquals(false, card.validateCardData(callBack));
  }
}