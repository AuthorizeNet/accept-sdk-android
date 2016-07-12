package net.authorize.acceptsdk.datamodel.transaction.response;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Kiran Bollepalli on 07,July,2016.
 * kbollepa@visa.com
 */
public class EncryptTransactionResponse extends TransactionResponse implements Parcelable {

  private String mDataDescriptor;
  private String mDataValue;

  // private ResponseReasonCode mReasonCode;

  public EncryptTransactionResponse() {
    super();
  }

  public String getDataDescriptor() {
    return mDataDescriptor;
  }

  public void setDataDescriptor(String mDataDescriptor) {
    this.mDataDescriptor = mDataDescriptor;
  }

  public String getDataValue() {
    return mDataValue;
  }

  public void setDataValue(String mDataValue) {
    this.mDataValue = mDataValue;
  }

  public static EncryptTransactionResponse createEncryptionResponse(InputStream inputStream)
      throws IOException {
    JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
    reader.beginObject();
    while (reader.hasNext()) {
      String name = reader.nextName();
      if (name.equals("opaqueData")) {

      }
    }

    return null;
  }

  @Override public int describeContents() {
    return 0;
  }

  //public ResponseReasonCode getReasonCode() {
  //  return mReasonCode;
  //}

  private EncryptTransactionResponse(Parcel in) {
    //        this.decision = (SDKResponseDecision) in.readSerializable();
    //        this.reasonCode = (SDKResponseReasonCode) in.readSerializable();
    //        this.requestId = in.readString();
    //        this.requestToken =  in.readString();
    //        this.type = (SDKGatewayResponseType) in.readSerializable();
    //        this.authorizationCode = in.readString();
    //        this.date = in.readString();
    //        this.time = in.readString();
    //        this.encryptedPaymentData = in.readString();
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    //        dest.writeSerializable(decision);
    //        dest.writeSerializable(reasonCode);
    //        dest.writeString(requestId);
    //        dest.writeString(requestToken);
    //        dest.writeSerializable(type);
    //        dest.writeString(authorizationCode);
    //        dest.writeString(date);
    //        dest.writeString(time);
    //        dest.writeString(encryptedPaymentData);
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
