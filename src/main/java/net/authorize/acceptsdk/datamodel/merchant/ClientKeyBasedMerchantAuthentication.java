package net.authorize.acceptsdk.datamodel.merchant;

import net.authorize.acceptsdk.exception.AcceptInvalidCardException;
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
   * @throws AcceptSDKException,  If apiLogin ID or client key is null.
   */
  public static ClientKeyBasedMerchantAuthentication createMerchantAuthentication(String loginId,
      String clientKey) throws AcceptSDKException {
    if (loginId == null || clientKey == null) {
      throw new AcceptSDKException( AcceptSDKException.NULL_APIKEY_CLIENT_KEY);
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
