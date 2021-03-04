package net.authorize.acceptsdk.datamodel.transaction;

import junit.framework.Assert;
import net.authorize.acceptsdk.ValidationCallback;
import net.authorize.acceptsdk.datamodel.merchant.ClientKeyBasedMerchantAuthentication;
import net.authorize.acceptsdk.datamodel.transaction.response.ErrorTransactionResponse;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Created by Kiran Bollepalli on 14,July,2016.
 * kbollepa@visa.com
 */
public class EncryptTransactionObjectTest {
  private final String CLIENT_KEY =
      "6gSuV295YD86Mq4d86zEsx8C839uMVVjfXm9N4wr6DRuhTHpDU97NFyKtfZncUq8";
  private final String API_LOGIN_ID = "6AB64hcB";
  ClientKeyBasedMerchantAuthentication clientKeyBasedMerchantAuthentication;
  private final String CARD_NUMBER = "4111111111111111";
  private final String EXPIRATION_MONTH = "11";
  private final String EXPIRATION_YEAR = "2050";
  private final String CVV = "256";
  CardData cardData;
  ValidationCallback callBack;

  @Before public void setUp()    {
    clientKeyBasedMerchantAuthentication = ClientKeyBasedMerchantAuthentication.
        createMerchantAuthentication(API_LOGIN_ID, CLIENT_KEY);
    cardData = new CardData.Builder(CARD_NUMBER, EXPIRATION_MONTH, EXPIRATION_YEAR).build();
    callBack = new ValidationCallback() {
      @Override public void OnValidationSuccessful() {

      }

      @Override public void OnValidationFailed(ErrorTransactionResponse errorTransactionResponse) {

      }
    };
  }

  @Test public void testEncryptTransactionObjectInstantiation()    {
    TransactionObject transactionObject = TransactionObject.
        createTransactionObject(TransactionType.SDK_TRANSACTION_ENCRYPTION)
        .cardData(cardData)
        .merchantAuthentication(clientKeyBasedMerchantAuthentication).build();
    Assert.assertEquals(true,transactionObject.validateTransactionObject(callBack));
  }

  @Test public void testCardDataForError()    {
    TransactionObject transactionObject = TransactionObject.
        createTransactionObject(TransactionType.SDK_TRANSACTION_ENCRYPTION)
        .cardData(null)
        .merchantAuthentication(clientKeyBasedMerchantAuthentication).build();
    Assert.assertEquals(false,transactionObject.validateTransactionObject(callBack));
  }

  @Test public void testAuthenticationForError()    {
    TransactionObject transactionObject = TransactionObject.
        createTransactionObject(TransactionType.SDK_TRANSACTION_ENCRYPTION)
        .cardData(cardData)
        .merchantAuthentication(null).build();
    Assert.assertEquals(false,transactionObject.validateTransactionObject(callBack));
  }
}