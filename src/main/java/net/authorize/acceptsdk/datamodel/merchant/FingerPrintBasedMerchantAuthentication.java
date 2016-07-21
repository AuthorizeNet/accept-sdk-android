package net.authorize.acceptsdk.datamodel.merchant;

import net.authorize.acceptsdk.ValidationCallback;
import net.authorize.acceptsdk.ValidationManager;
import net.authorize.acceptsdk.datamodel.error.SDKErrorCode;
import net.authorize.acceptsdk.datamodel.transaction.response.ErrorTransactionResponse;

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
   */
  public static FingerPrintBasedMerchantAuthentication createMerchantAuthentication(String loginId,
      FingerPrintData fingerPrintData) {
    FingerPrintBasedMerchantAuthentication authenticator =
        new FingerPrintBasedMerchantAuthentication();

    if (loginId != null) loginId = loginId.trim();

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

  /**
   *  Validates Fingerprint based Merchant Authentication.
   *
   * @param callback  {@link ValidationCallback}
   * @return boolean true, if it is success. false if validation fails.
   */

  @Override public boolean validateMerchantAuthentication(ValidationCallback callback) {
    if (!ValidationManager.isValidString(mApiLoginID)) {
      callback.OnValidationFailed(
          ErrorTransactionResponse.createErrorResponse(SDKErrorCode.E_WC_10));
      return false;
    }

    if (mFingerPrintData == null) {
      callback.OnValidationFailed(
          ErrorTransactionResponse.createErrorResponse(SDKErrorCode.E_WC_04));
      return false;
    }

    if (!mFingerPrintData.validateFingerPrint(callback)) {
      callback.OnValidationFailed(
          ErrorTransactionResponse.createErrorResponse(SDKErrorCode.E_WC_13));
      return false;
    }
    return true;
  }
}
