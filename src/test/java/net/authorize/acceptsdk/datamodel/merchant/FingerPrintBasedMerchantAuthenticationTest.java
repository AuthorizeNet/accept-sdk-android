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
public class FingerPrintBasedMerchantAuthenticationTest {

  private FingerPrintData fingerPrintData;
  private String mApiLoginId;
  ValidationCallback callBack;

  @Before public void setUp() {
    mApiLoginId = "6AB64hcB";
    fingerPrintData =
        new FingerPrintData.Builder("37072f4703346059fbde79b4c8babdcd", 1468821505).setSequence(
            "abc").setAmount(12344.52).build();
    callBack = new ValidationCallback() {
      @Override public void OnValidationSuccessful() {

      }

      @Override public void OnValidationFailed(ErrorTransactionResponse errorTransactionResponse) {

      }
    };
  }

  @Test public void testCreateMerchantAuthentication() {
    FingerPrintBasedMerchantAuthentication merchantAuthentication =
        FingerPrintBasedMerchantAuthentication.createMerchantAuthentication(mApiLoginId,
            fingerPrintData);
    Assert.assertEquals(true, merchantAuthentication.validateMerchantAuthentication(callBack));
  }

  @Test public void testApiLoginIDForNull() {
    mApiLoginId = null;
    FingerPrintBasedMerchantAuthentication merchantAuthentication =
        FingerPrintBasedMerchantAuthentication.createMerchantAuthentication(mApiLoginId,
            fingerPrintData);
    Assert.assertEquals(false, merchantAuthentication.validateMerchantAuthentication(callBack));
  }

  @Test public void testFingerPrintForNull() {
    mApiLoginId = "6AB64hcB";
    fingerPrintData = null;
    FingerPrintBasedMerchantAuthentication merchantAuthentication =
        FingerPrintBasedMerchantAuthentication.createMerchantAuthentication(mApiLoginId,
            fingerPrintData);
    Assert.assertEquals(false, merchantAuthentication.validateMerchantAuthentication(callBack));
  }

}