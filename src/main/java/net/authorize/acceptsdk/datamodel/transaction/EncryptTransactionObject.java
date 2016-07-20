package net.authorize.acceptsdk.datamodel.transaction;

import net.authorize.acceptsdk.ValidationCallback;
import net.authorize.acceptsdk.datamodel.error.SDKErrorCode;
import net.authorize.acceptsdk.datamodel.transaction.response.ErrorTransactionResponse;

/**
 * Created by Kiran Bollepalli on 07,July,2016.
 * kbollepa@visa.com
 */
public class EncryptTransactionObject extends TransactionObject {

  private EncryptTransactionObject(Builder builder) {
    mTransactionType = builder.transactionType;
    this.mCardData = builder.cardData;
    this.mMerchantAuthentication = builder.merchantAuthentication;
  }

  @Override public void validateTransactionObject(final ValidationCallback callback) {
    if (mMerchantAuthentication == null) {
      callback.OnValidationFailed(
          ErrorTransactionResponse.createErrorResponse(SDKErrorCode.E_WC_04));
    }
    if (mCardData == null) {
      callback.OnValidationFailed(
          ErrorTransactionResponse.createErrorResponse(SDKErrorCode.E_WC_04));
    }
    if (mMerchantAuthentication.validateMerchantAuthentication(callback)
        && mCardData.validateCardData(callback)) {
      callback.OnValidationSuccessful();
    }
  }

  public static class Builder extends TransactionObject.Builder {

    public Builder() {
      transactionType = TransactionType.SDK_TRANSACTION_ENCRYPTION;
    }

    @Override public EncryptTransactionObject build() {
      return new EncryptTransactionObject(this);
    }
  }
}
