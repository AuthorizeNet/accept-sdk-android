package net.authorize.acceptsdk.datamodel.merchant;

import junit.framework.Assert;
import net.authorize.acceptsdk.ValidationCallback;
import net.authorize.acceptsdk.datamodel.transaction.response.ErrorTransactionResponse;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Kiran Bollepalli on 14,July,2016.
 * kbollepa@visa.com
 */
public class ClientKeyBasedMerchantAuthenticationTest {

  private String mClientKey;
  private String mApiLoginId;
  ValidationCallback callBack;

  @Before public void setUp() {
    mApiLoginId = "6AB64hcB";
    mClientKey = "6gSuV295YD86Mq4d86zEsx8C839uMVVjfXm9N4wr6DRuhTHpDU97NFyKtfZncUq8";
    callBack = new ValidationCallback() {
      @Override public void OnValidationSuccessful() {

      }

      @Override public void OnValidationFailed(ErrorTransactionResponse errorTransactionResponse) {

      }
    };
  }

  @Test public void testCreateMerchantAuthentication() {
    ClientKeyBasedMerchantAuthentication merchantAuthentication =
        ClientKeyBasedMerchantAuthentication.createMerchantAuthentication(mApiLoginId, mClientKey);
    Assert.assertEquals(true, merchantAuthentication.validateMerchantAuthentication(callBack));
  }

  @Test public void testApiLoginIDForNull() {
    mApiLoginId = null;
    mClientKey = "6gSuV295YD86Mq4d86zEsx8C839uMVVjfXm9N4wr6DRuhTHpDU97NFyKtfZncUq8";
    ClientKeyBasedMerchantAuthentication merchantAuthentication =
        ClientKeyBasedMerchantAuthentication.createMerchantAuthentication(mApiLoginId, mClientKey);
    Assert.assertEquals(false, merchantAuthentication.validateMerchantAuthentication(callBack));
  }

  @Test public void testApiLoginIDForError() {
    mApiLoginId = "  ";
    mClientKey = "6gSuV295YD86Mq4d86zEsx8C839uMVVjfXm9N4wr6DRuhTHpDU97NFyKtfZncUq8";
    ClientKeyBasedMerchantAuthentication merchantAuthentication =
        ClientKeyBasedMerchantAuthentication.createMerchantAuthentication(mApiLoginId, mClientKey);
    Assert.assertEquals(false, merchantAuthentication.validateMerchantAuthentication(callBack));

  }

  @Test public void testClientKeyForError() {
    mApiLoginId = "6AB64hcB";
    mClientKey = "";
    ClientKeyBasedMerchantAuthentication merchantAuthentication =
        ClientKeyBasedMerchantAuthentication.createMerchantAuthentication(mApiLoginId, mClientKey);
    Assert.assertEquals(false, merchantAuthentication.validateMerchantAuthentication(callBack));
  }

  @Test public void testClientKeyForNull() {
    mApiLoginId = "6AB64hcB";
    mClientKey = null;
    ClientKeyBasedMerchantAuthentication merchantAuthentication =
        ClientKeyBasedMerchantAuthentication.createMerchantAuthentication(mApiLoginId, mClientKey);
    Assert.assertEquals(false, merchantAuthentication.validateMerchantAuthentication(callBack));

  }
}