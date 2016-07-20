package net.authorize.acceptsdk.datamodel.merchant;

import net.authorize.acceptsdk.ValidationCallback;
import net.authorize.acceptsdk.ValidationManager;
import net.authorize.acceptsdk.datamodel.error.SDKErrorCode;
import net.authorize.acceptsdk.datamodel.transaction.response.ErrorTransactionResponse;

/**
 * ClientKey based Merchant Authentication
 */
public class ClientKeyBasedMerchantAuthentication extends AbstractMerchantAuthentication {

  private String mClientKey;

  private ClientKeyBasedMerchantAuthentication() {
  }

  /**
   * Creates a client key authenticator.
   *
   * @return ClientKeyBasedMerchantAuthentication container
   */
  public static ClientKeyBasedMerchantAuthentication createMerchantAuthentication(String loginId,
      String clientKey) {
    //if (loginId == null || loginId.isEmpty()) {
    //  throw new AcceptSDKException(AcceptSDKException.APIKEY_ERROR);
    //}
    //if (clientKey == null || clientKey.isEmpty()) {
    //  throw new AcceptSDKException(AcceptSDKException.CLIENTKEY_ERROR);
    //}
    ClientKeyBasedMerchantAuthentication authenticator = new ClientKeyBasedMerchantAuthentication();
    authenticator.mApiLoginID = loginId;
    authenticator.mClientKey = clientKey;
    authenticator.merchantAuthenticationType = MerchantAuthenticationType.CLIENT_KEY;

    return authenticator;
  }

  public String getClientKey() {
    return mClientKey;
  }

  @Override public boolean validateMerchantAuthentication(ValidationCallback callback) {
    if (!ValidationManager.isValidString(mApiLoginID)) {
      callback.OnValidationFailed(
          ErrorTransactionResponse.createErrorResponse(SDKErrorCode.E_WC_10));
      return false;
    }
    if (!ValidationManager.isValidString(mClientKey)) {
      callback.OnValidationFailed(
          ErrorTransactionResponse.createErrorResponse(SDKErrorCode.E_WC_18));
      return false;
    }
    //callback.OnValidationSuccessful();
    return true;
  }
}
