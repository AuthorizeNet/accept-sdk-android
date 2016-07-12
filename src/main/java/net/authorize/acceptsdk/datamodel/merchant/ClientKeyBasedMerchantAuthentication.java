package net.authorize.acceptsdk.datamodel.merchant;

/**
 * Client key authenticator.
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

    ClientKeyBasedMerchantAuthentication authenticator = new ClientKeyBasedMerchantAuthentication();
    authenticator.mApiLoginID = loginId;
    authenticator.mClientKey = clientKey;
    authenticator.merchantAuthenticationType = MerchantAuthenticationType.CLIENT_KEY;

    return authenticator;
  }

  public String getClientKey() {
    return mClientKey;
  }

  public void setClientKey(String clientKey) {
    mClientKey = clientKey;
  }
}
