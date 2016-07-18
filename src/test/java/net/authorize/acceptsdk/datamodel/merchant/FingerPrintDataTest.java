package net.authorize.acceptsdk.datamodel.merchant;

import net.authorize.acceptsdk.exception.AcceptSDKException;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Kiran Bollepalli on 18,July,2016.
 * kbollepa@visa.com
 */
public class FingerPrintDataTest {

  @Before public void setUp() throws Exception {

  }

  @Test public void testFingerPrintData() throws AcceptSDKException {
    FingerPrintData fingerPrintData =
        new FingerPrintData.Builder("37072f4703346059fbde79b4c8babdcd", 1468821505).setSequence(
            "abc").setAmount(12344.52).build();
  }
}