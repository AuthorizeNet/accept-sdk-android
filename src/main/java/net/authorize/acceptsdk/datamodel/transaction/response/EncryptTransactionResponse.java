package net.authorize.acceptsdk.datamodel.transaction.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Response Object of Encrypt Transaction.
 *
 * Created by Kiran Bollepalli on 07,July,2016.
 * kbollepa@visa.com
 */
public class EncryptTransactionResponse extends TransactionResponse {

  private String mDataDescriptor;
  private String mDataValue;

  public EncryptTransactionResponse() {
    super();
  }


  /**
   * Returns Data descriptor .Used as "dataDescriptor" to perform payment transaction.
   *
   * @return String
   */
  public String getDataDescriptor() {
    return mDataDescriptor;
  }

  public void setDataDescriptor(String mDataDescriptor) {
    this.mDataDescriptor = mDataDescriptor;
  }

  /**
   * Returns Encrypted Payment Token.Used as "dataValue" to perform payment transaction.
   *
   * @return String Encrypted Payment Token
   */
  public String getDataValue() {
    return mDataValue;
  }

  public void setDataValue(String mDataValue) {
    this.mDataValue = mDataValue;
  }


  // ---------- Code for Parcelable interface ----------

  public EncryptTransactionResponse(Parcel in) {
    super(in);
    readFromParcel(in);
  }


  @Override public int describeContents() {
    return 0;
  }



  public void readFromParcel(Parcel in) {
    mDataDescriptor = in.readString();
    mDataValue = in.readString();
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(mDataDescriptor);
    dest.writeString(mDataValue);
  }

  public static final Parcelable.Creator<EncryptTransactionResponse> CREATOR =
      new Parcelable.Creator<EncryptTransactionResponse>() {

        @Override public EncryptTransactionResponse createFromParcel(Parcel in) {
          return new EncryptTransactionResponse(in);
        }

        @Override public EncryptTransactionResponse[] newArray(int size) {
          return new EncryptTransactionResponse[size];
        }
      };
}
