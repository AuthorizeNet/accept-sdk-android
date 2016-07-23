package net.authorize.acceptsdk.datamodel.merchant;

/**
 * Defines the type of merchant authentication mechanisms that are supported.
 */
public enum MerchantAuthenticationType {
  /**
   * For ClientKey based Authentication.
   */
  CLIENT_KEY,
  /**
   * For Finger Print based Authentication.
   */
  FINGERPRINT
}
