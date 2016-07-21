package net.authorize.acceptsdk.datamodel.merchant;

import java.io.Serializable;
import net.authorize.acceptsdk.ValidationCallback;

/**
 * A common class used to represent Merchant Authentication.
 *
 * Created by Kiran Bollepalli on 08,July,2016.
 * kbollepa@visa.com
 */
public abstract class AbstractMerchantAuthentication implements Serializable {
  private static final long serialVersionUID = 2L;

  protected String mApiLoginID;
  protected MerchantAuthenticationType merchantAuthenticationType;

  public MerchantAuthenticationType getMerchantAuthenticationType() {
    return this.merchantAuthenticationType;
  }

  public String getApiLoginID() {
    return this.mApiLoginID;
  }

  /**
   *  Validates Merchant Authentication.
   *
   * @param callback  {@link ValidationCallback}
   * @return boolean true, if it is success. false if validation fails.
   */
   public  abstract boolean validateMerchantAuthentication(ValidationCallback callback);
}
