package net.authorize.acceptsdk.datamodel.merchant;

import net.authorize.acceptsdk.exception.AcceptSDKException;

/**
 * Fingerprint based Merchant Authentication.
 */
public class FingerPrintBasedMerchantAuthentication extends AbstractMerchantAuthentication {

  private FingerPrintData mFingerPrintData;

  private FingerPrintBasedMerchantAuthentication() {
  }

  /**
   * Creates a client key authenticator.
   *
   * @return FingerPrintBasedMerchantAuthentication container
   * @throws AcceptSDKException, If apiLogin ID or fingerprint is null.
   */
  public static FingerPrintBasedMerchantAuthentication createMerchantAuthentication(String loginId,
      FingerPrintData fingerPrintData) throws AcceptSDKException {
    if (loginId == null || fingerPrintData == null) {
      throw new AcceptSDKException(AcceptSDKException.NULL_APIKEY_FINGER_PRINT);
    }
    FingerPrintBasedMerchantAuthentication authenticator =
        new FingerPrintBasedMerchantAuthentication();
    authenticator.mApiLoginID = loginId;
    authenticator.mFingerPrintData = fingerPrintData;
    authenticator.merchantAuthenticationType = MerchantAuthenticationType.FINGERPRINT;

    return authenticator;
  }

  public FingerPrintData getFingerPrintData() {
    return mFingerPrintData;
  }

  public void setFingerPrintData(FingerPrintData fingerPrintData) {
    mFingerPrintData = fingerPrintData;
  }
}
