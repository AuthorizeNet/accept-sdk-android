package net.authorize.acceptsdk.parser;

import net.authorize.acceptsdk.datamodel.merchant.ClientKeyBasedMerchantAuthentication;
import net.authorize.acceptsdk.datamodel.merchant.FingerPrintBasedMerchantAuthentication;
import net.authorize.acceptsdk.datamodel.merchant.FingerPrintData;
import net.authorize.acceptsdk.datamodel.transaction.CardData;
import net.authorize.acceptsdk.datamodel.transaction.EncryptTransactionObject;
import net.authorize.acceptsdk.datamodel.transaction.TransactionType;
import org.junit.Before;

/**
 * Created by Kiran Bollepalli on 08,July,2016.
 * kbollepa@visa.com
 */
public class AcceptSDKParserTest {

  EncryptTransactionObject transactionObject;

  private final String ACCOUNT_NUMBER = "4111111111111111";
  private final String EXPIRATION_MONTH = "11";
  private final String EXPIRATION_YEAR = "2017";
  private final String CLIENT_KEY =
      "6gSuV295YD86Mq4d86zEsx8C839uMVVjfXm9N4wr6DRuhTHpDU97NFyKtfZncUq8";
  private final String API_LOGIN_ID = "6AB64hcB";

  @Before public void setUp() throws Exception {
    transactionObject = prepareTransactionObject();
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

    cardData = new CardData.Builder(ACCOUNT_NUMBER, EXPIRATION_MONTH, EXPIRATION_YEAR).build();

    return cardData;
  }

  private EncryptTransactionObject prepareTransactionObjectForFingerPrintTest() {
    FingerPrintData fData =
        new FingerPrintData.Builder("37072f4703346059fbde79b4c8babdcd", 1468821505).build();

    FingerPrintBasedMerchantAuthentication merchantAuthentication =
        FingerPrintBasedMerchantAuthentication.
            createMerchantAuthentication(API_LOGIN_ID, fData);

    // create a transaction object by calling the predefined api for creation
    return EncryptTransactionObject.
        createTransactionObject(
            TransactionType.SDK_TRANSACTION_ENCRYPTION) // type of transaction object
        .cardData(prepareTestCardData()) // card data to be encrypted
        .merchantAuthentication(merchantAuthentication).build();
  }

  //COMMENT: JSONStringer is not mocked due to that unit testing failed.
  //@Test public void testGetJsonFromEncryptTransaction() throws Exception {
  //  String result = AcceptSDKParser.getOrderedJsonFromEncryptTransaction(transactionObject);
  //}
  //
  //@Test public void testGetJsonFromEncryptTransactionForFingerPrint() throws Exception {
  //  String result = AcceptSDKParser.getOrderedJsonFromEncryptTransaction(prepareTransactionObjectForFingerPrintTest());
  //}
}