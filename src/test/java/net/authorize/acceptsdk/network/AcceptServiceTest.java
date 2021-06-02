package net.authorize.acceptsdk.network;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import net.authorize.acceptsdk.datamodel.merchant.ClientKeyBasedMerchantAuthentication;
import net.authorize.acceptsdk.datamodel.transaction.CardData;
import net.authorize.acceptsdk.datamodel.transaction.EncryptTransactionObject;
import net.authorize.acceptsdk.datamodel.transaction.TransactionType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by Kiran Bollepalli on 14,July,2016.
 * kbollepa@visa.com
 */
@RunWith(MockitoJUnitRunner.class)
public class AcceptServiceTest {
  private final String CARD_NUMBER = "4111111111111111";
  private final String CARD_EXPIRATION_MONTH = "11";
  private final String CARD_EXPIRATION_YEAR = "2017";
  private final String CARD_ZIP = "98001";
  private final String CARD_CVV = "256";
  private final String CLIENT_KEY =
      "6gSuV295YD86Mq4d86zEsx8C839uMVVjfXm9N4wr6DRuhTHpDU97NFyKtfZncUq8";
  private final String API_LOGIN_ID = "6AB64hcB";
  TransactionResultReceiver mResultReceiver;
  @Mock
  Context context;

  @Before
  public void initiate() {
    registerResultReceiver();
  }

  private void registerResultReceiver() {
    if (mResultReceiver != null) return;
    mResultReceiver = new TransactionResultReceiver(new Handler());
  }

  @Test
  public void testStartActionEncrypt() throws Exception {
    AcceptService.startActionEncrypt(context, prepareTransactionObject(), mResultReceiver);
    //assertNotNull(getService());
  }

  @Test
  public void testOnHandleIntent() throws Exception {
    Intent intent = new Intent(context, AcceptService.class);
    intent.setAction(AcceptService.ACTION_ENCRYPT);
    context.startService(intent);
    //assertNotNull(getService());
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
    CardData cardData =
        new CardData.Builder(CARD_NUMBER, CARD_EXPIRATION_MONTH, CARD_EXPIRATION_YEAR).build();

    return cardData;
  }
}