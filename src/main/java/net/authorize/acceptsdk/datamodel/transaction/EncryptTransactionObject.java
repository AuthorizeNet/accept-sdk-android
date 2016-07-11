package net.authorize.acceptsdk.datamodel.transaction;

import net.authorize.acceptsdk.datamodel.merchant.AbstractMerchantAuthentication;

/**
 * Created by Kiran Bollepalli on 07,July,2016.
 * kbollepa@visa.com
 */
public class EncryptTransactionObject extends TransactionObject {


    private EncryptTransactionObject(Builder builder)
    {
        mTransactionType = builder.transactionType;
        this.mCardData = builder.cardData;
        this.mMerchantAuthentication = builder.merchantAuthentication;
    }

    public static class Builder extends TransactionObject.Builder{

        public Builder(){
              transactionType = TransactionType.SDK_TRANSACTION_ENCRYPTION;
        }

        @Override
        public EncryptTransactionObject build(){
            return new EncryptTransactionObject(this);
        }
    }

}
