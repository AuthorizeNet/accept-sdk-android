package net.authorize.acceptsdk.datamodel.merchant;

import java.io.Serializable;

/**
 * A common class used to represent Merchant Authentication.
 * Created by Kiran Bollepalli on 08,July,2016.
 * kbollepa@visa.com
 */
public class AbstractMerchantAuthentication implements Serializable {
  private static final long serialVersionUID = 2L;

  protected String mApiLoginID;
  protected MerchantAuthenticationType merchantAuthenticationType;

  public MerchantAuthenticationType getMerchantAuthenticationType() {
    return this.merchantAuthenticationType;
  }

  public String getApiLoginID() {
    return this.mApiLoginID;
  }
}
