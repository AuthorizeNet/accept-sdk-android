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
   * @param loginId API login id of merchant.
   * @param clientKey public client key of merchant
   * @return ClientKeyBasedMerchantAuthentication Object
   */
  public static ClientKeyBasedMerchantAuthentication createMerchantAuthentication(String loginId,
      String clientKey) {
    ClientKeyBasedMerchantAuthentication authenticator = new ClientKeyBasedMerchantAuthentication();

    if (loginId != null) loginId = loginId.trim();
    if (clientKey != null) clientKey = clientKey.trim();

    authenticator.mApiLoginID = loginId;
    authenticator.mClientKey = clientKey;
    authenticator.merchantAuthenticationType = MerchantAuthenticationType.CLIENT_KEY;

    return authenticator;
  }

  public String getClientKey() {
    return mClientKey;
  }

  /**
   * Validates Client key based Merchant Authentication.
   *
   * @param callback {@link ValidationCallback}
   * @return boolean true, if it is success. false if validation fails.
   */
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

    return true;
  }
}
