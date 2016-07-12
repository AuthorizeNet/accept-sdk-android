package net.authorize.acceptsdk.datamodel.transaction;

import java.io.Serializable;
import java.util.UUID;
import net.authorize.acceptsdk.datamodel.merchant.AbstractMerchantAuthentication;

/**
 * Created by Kiran Bollepalli on 07,July,2016.
 * kbollepa@visa.com
 */
public class TransactionObject implements Serializable {

  private static final long serialVersionUID = 2L;

  AbstractMerchantAuthentication mMerchantAuthentication;
  CardData mCardData;
  TransactionType mTransactionType;
  String mGuid;

  public TransactionObject() {
    //FIXME : Need to revisit this code
    mGuid = UUID.randomUUID().toString();
  }

  public AbstractMerchantAuthentication getMerchantAuthentication() {
    return mMerchantAuthentication;
  }

  public void setGuid(String guid) {
    mGuid = guid;
  }

  public String getGuid() {
    return mGuid;
  }

  public void setMerchantAuthentication(AbstractMerchantAuthentication mMerchantAuthentication) {

    this.mMerchantAuthentication = mMerchantAuthentication;
  }

  public CardData getCardData() {
    return mCardData;
  }

  public void setCardData(CardData mCardData) {
    this.mCardData = mCardData;
  }

  public TransactionType getTransactionType() {
    return mTransactionType;
  }

  public void setTransactionType(TransactionType mTransactionType) {
    this.mTransactionType = mTransactionType;
  }

  /**
   * A factory method for creating proper transaction object.
   *
   * @param type transaction type
   * @return one of transaction objects
   */
  public static TransactionObject.Builder createTransactionObject(TransactionType type) {

    switch (type) {
      case SDK_TRANSACTION_ENCRYPTION:
        return new EncryptTransactionObject.Builder();
      default:
        return new EncryptTransactionObject.Builder();
    }
  }

  public static abstract class Builder {
    CardData cardData;
    AbstractMerchantAuthentication merchantAuthentication;
    TransactionType transactionType;

    public TransactionObject.Builder cardData(CardData cardData) {
      this.cardData = cardData;
      return this;
    }

    public TransactionObject.Builder merchantAuthentication(
        AbstractMerchantAuthentication merchantAuthentication) {
      this.merchantAuthentication = merchantAuthentication;
      return this;
    }

    public abstract EncryptTransactionObject build();
  }
}
