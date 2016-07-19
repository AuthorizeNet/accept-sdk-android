package net.authorize.acceptsdk.datamodel.merchant;

import net.authorize.acceptsdk.exception.AcceptSDKException;

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
   * @throws AcceptSDKException, If apiLogin ID or client key is null or empty.
   */
  public static ClientKeyBasedMerchantAuthentication createMerchantAuthentication(String loginId,
      String clientKey) throws AcceptSDKException {
    if (loginId == null || loginId.isEmpty()) {
      throw new AcceptSDKException(AcceptSDKException.APIKEY_ERROR);
    }
    if (clientKey == null || clientKey.isEmpty()) {
      throw new AcceptSDKException(AcceptSDKException.CLIENTKEY_ERROR);
    }
    ClientKeyBasedMerchantAuthentication authenticator = new ClientKeyBasedMerchantAuthentication();
    authenticator.mApiLoginID = loginId;
    authenticator.mClientKey = clientKey;
    authenticator.merchantAuthenticationType = MerchantAuthenticationType.CLIENT_KEY;

    return authenticator;
  }

  public String getClientKey() {
    return mClientKey;
  }
}
