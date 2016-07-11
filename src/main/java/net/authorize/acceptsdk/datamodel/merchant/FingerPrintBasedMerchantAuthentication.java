package net.authorize.acceptsdk.datamodel.merchant;

/**
 * Client key authenticator.
 */
public class FingerPrintBasedMerchantAuthentication extends AbstractMerchantAuthentication {

    private FingerPrintData mFingerPrintData;

    private FingerPrintBasedMerchantAuthentication() {
    }

    /**
     * Creates a client key authenticator.
     *
     * @param loginId
     * @param fingerPrintData
     * @return FingerPrintBasedMerchantAuthentication container
     */
    public static FingerPrintBasedMerchantAuthentication createMerchantAuthentication(
            String loginId, FingerPrintData fingerPrintData) {

        FingerPrintBasedMerchantAuthentication authenticator = new FingerPrintBasedMerchantAuthentication();
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
