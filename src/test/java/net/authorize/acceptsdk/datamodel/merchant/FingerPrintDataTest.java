package net.authorize.acceptsdk.datamodel.merchant;

import junit.framework.Assert;
import net.authorize.acceptsdk.ValidationCallback;
import net.authorize.acceptsdk.datamodel.transaction.response.ErrorTransactionResponse;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Kiran Bollepalli on 18,July,2016.
 * kbollepa@visa.com
 */
public class FingerPrintDataTest {

  ValidationCallback callBack;
  private FingerPrintData fingerPrintData;

  @Before public void setUp() throws Exception {
    fingerPrintData =
        new FingerPrintData.Builder("37072f4703346059fbde79b4c8babdcd", 1468821505)
            .setSequence("abc")
            .setCurrencyCode("USD")
            .setAmount(12344.52).build();

    callBack = new ValidationCallback() {
      @Override public void OnValidationSuccessful() {

      }

      @Override public void OnValidationFailed(ErrorTransactionResponse errorTransactionResponse) {

      }
    };
  }

  @Test public void testFingerPrintData() {
    Assert.assertEquals(true,fingerPrintData.validateFingerPrint(callBack));
  }

  @Test public void testHashValueForError() {
    FingerPrintData data =
        new FingerPrintData.Builder(null, 111)
            .setSequence(null)
            .setCurrencyCode(null)
            .setAmount(0)
            .build();
    Assert.assertEquals(false,data.validateFingerPrint(callBack));
  }

  @Test public void testTimeStampForError() {
    FingerPrintData data =
        new FingerPrintData.Builder("37072f4703346059fbde79b4c8babdcd", -111)
            .setSequence(null)
            .setCurrencyCode(null)
            .setAmount(0)
            .build();
    Assert.assertEquals(false,data.validateFingerPrint(callBack));
  }


  @Test public void testAmountForError() {
    FingerPrintData data =
        new FingerPrintData.Builder("37072f4703346059fbde79b4c8babdcd", 111)
            .setSequence(null)
            .setCurrencyCode(null)
            .setAmount(-111.123)
            .build();
    Assert.assertEquals(false,data.validateFingerPrint(callBack));
  }
}