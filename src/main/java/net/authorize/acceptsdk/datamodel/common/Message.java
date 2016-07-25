package net.authorize.acceptsdk.datamodel.common;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * POJO Class of message section from server response.
 *
 * Created by Kiran Bollepalli on 12,July,2016.
 * kbollepa@visa.com
 */
public class Message implements Parcelable {

  String mMessageCode;
  String mMessageText;

  public Message() {
  }

  public Message(Parcel in) {
    readFromParcel(in);
  }

  public Message(String mMessageCode, String mMessageText) {
    this.mMessageCode = mMessageCode;
    this.mMessageText = mMessageText;
  }



  public String getMessageCode() {
    return mMessageCode;
  }

  public void setMessageCode(String mMessageCode) {
    this.mMessageCode = mMessageCode;
  }

  public String getMessageText() {
    return mMessageText;
  }

  public void setMessageText(String mMessageText) {
    this.mMessageText = mMessageText;
  }

  @Override public String toString() {
    return "Message{" +
        "Code='" + mMessageCode + '\'' +
        ", MessageText='" + mMessageText + '\'' +
        '}';
  }

  // ---------- Code for Parcelable interface ----------

  public void readFromParcel(Parcel in) {
    mMessageCode = in.readString();
    mMessageText = in.readString();
  }
  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(mMessageCode);
    dest.writeString(mMessageText);
  }

  @Override public int describeContents() {
    return 0;
  }

  public static final Creator<Message> CREATOR = new Creator<Message>() {
    @Override public Message createFromParcel(Parcel in) {
      return new Message(in);
    }

    @Override public Message[] newArray(int size) {
      return new Message[size];
    }
  };
}
