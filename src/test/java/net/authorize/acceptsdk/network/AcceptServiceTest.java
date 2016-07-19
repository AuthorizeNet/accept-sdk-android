package net.authorize.acceptsdk.network;

import android.content.Intent;
import android.test.ServiceTestCase;
import net.authorize.acceptsdk.AcceptSDKApiClient;
import net.authorize.acceptsdk.datamodel.merchant.ClientKeyBasedMerchantAuthentication;
import net.authorize.acceptsdk.datamodel.transaction.CardData;
import net.authorize.acceptsdk.datamodel.transaction.EncryptTransactionObject;
import net.authorize.acceptsdk.datamodel.transaction.TransactionType;
import net.authorize.acceptsdk.exception.AcceptInvalidCardException;
import net.authorize.acceptsdk.exception.AcceptSDKException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by Kiran Bollepalli on 14,July,2016.
 * kbollepa@visa.com
 */
public class AcceptServiceTest extends ServiceTestCase<AcceptService> {
  private final String CARD_NUMBER = "4111111111111111";
  private final String CARD_EXPIRATION_MONTH = "11";
  private final String CARD_EXPIRATION_YEAR = "2017";
  private final String CARD_ZIP = "98001";
  private final String CARD_CVV = "256";
  private final String CLIENT_KEY =
      "6gSuV295YD86Mq4d86zEsx8C839uMVVjfXm9N4wr6DRuhTHpDU97NFyKtfZncUq8";
  private final String API_LOGIN_ID = "6AB64hcB";

  public AcceptServiceTest() {
    super(AcceptService.class);
  }
  public AcceptServiceTest(Class<AcceptService> serviceClass) {
    super(serviceClass);
  }

  @

  @Test public void testStartActionEncrypt() throws Exception {
    AcceptService.startActionEncrypt(AcceptSDKApiClient.getContext().get(), prepareTransactionObject(),
        null);
  }



  private EncryptTransactionObject prepareTransactionObject() throws AcceptSDKException {
    ClientKeyBasedMerchantAuthentication merchantAuthentication =
        ClientKeyBasedMerchantAuthentication.
            createMerchantAuthentication(API_LOGIN_ID, CLIENT_KEY);

    // create a transaction object by calling the predefined api for creation
    return EncryptTransactionObject.
        createTransactionObject(
            TransactionType.SDK_TRANSACTION_ENCRYPTION) // type of transaction object
        .cardData(prepareTestCardData()) // card data to be encrypted
        .merchantAuthentication(merchantAuthentication).build();
  }

  private CardData prepareTestCardData() {
    CardData cardData = null;
    try {
      cardData =
          new CardData.Builder(CARD_NUMBER, CARD_EXPIRATION_MONTH, CARD_EXPIRATION_YEAR).build();
    } catch (AcceptInvalidCardException e) {
      // Handle exception if the card is invalid
      e.printStackTrace();
    }
    return cardData;
  }
}