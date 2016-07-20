package net.authorize.acceptsdk;

import android.content.Context;
import android.test.mock.MockContext;
import java.util.concurrent.CountDownLatch;
import junit.framework.Assert;
import net.authorize.acceptsdk.datamodel.merchant.ClientKeyBasedMerchantAuthentication;
import net.authorize.acceptsdk.datamodel.transaction.CardData;
import net.authorize.acceptsdk.datamodel.transaction.EncryptTransactionObject;
import net.authorize.acceptsdk.datamodel.transaction.TransactionType;
import net.authorize.acceptsdk.datamodel.transaction.callbacks.EncryptTransactionCallback;
import net.authorize.acceptsdk.datamodel.transaction.response.EncryptTransactionResponse;
import net.authorize.acceptsdk.datamodel.transaction.response.ErrorTransactionResponse;
import net.authorize.acceptsdk.util.LogUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Kiran Bollepalli on 14,July,2016.
 * kbollepa@visa.com
 */
public class AcceptSDKApiClientTest {

  private final String CARD_NUMBER = "4111111111111111";
  private final String CARD_EXPIRATION_MONTH = "11";
  private final String CARD_EXPIRATION_YEAR = "2017";
  private final String CARD_ZIP = "98001";
  private final String CARD_CVV = "256";
  private final String CLIENT_KEY =
      "6gSuV295YD86Mq4d86zEsx8C839uMVVjfXm9N4wr6DRuhTHpDU97NFyKtfZncUq8";
  private final String API_LOGIN_ID = "6AB64hcB";

  AcceptSDKApiClient apiClient;
  Context context;
  String apiLoginID;

  @Before public void setUp() throws Exception {
    context = new MockContext();
    apiLoginID = API_LOGIN_ID;
    apiClient =
        new AcceptSDKApiClient.Builder(context, AcceptSDKApiClient.Environment.SANDBOX).build();
  }

  @After public void tearDown() throws Exception {
    context = null;
    apiClient = null;
  }

  @Test public void testGetContext() throws Exception {
    Assert.assertNotNull(AcceptSDKApiClient.getContext().get());
  }
  // create  a signal to let us know when our task is done.
  final CountDownLatch signal = new CountDownLatch(1);
  @Test public void testPerformEncryption() throws Exception {
    boolean value =
        apiClient.performEncryption(prepareTransactionObject(), new EncryptTransactionCallback() {

          @Override public void onErrorReceived(ErrorTransactionResponse error) {
            LogUtil.log(LogUtil.LOG_LEVEL.INFO, "error : " + error.getResultCode());
            signal.countDown();
          }

          @Override public void onEncryptionFinished(EncryptTransactionResponse response) {
            LogUtil.log(LogUtil.LOG_LEVEL.INFO, "response : " + response.getResultCode());
            signal.countDown();
          }
        });
    Assert.assertEquals(value, true);
  }

  private EncryptTransactionObject prepareTransactionObject() {
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
    //try {
    //  cardData =
    //      new CardData.Builder(CARD_NUMBER, CARD_EXPIRATION_MONTH, CARD_EXPIRATION_YEAR).build();
    //} catch (AcceptInvalidCardException e) {
    //  // Handle exception if the card is invalid
    //  e.printStackTrace();
    //}
    return cardData;
  }
}