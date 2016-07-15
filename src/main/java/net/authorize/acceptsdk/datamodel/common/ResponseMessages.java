package net.authorize.acceptsdk.datamodel.common;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;
import net.authorize.acceptsdk.parser.JSONConstants;

/**
 * POJO Class of messages section from server response.
 * Created by Kiran Bollepalli on 12,July,2016.
 * kbollepa@visa.com
 */
public class ResponseMessages implements Parcelable {

  /**
   * Result code is either Ok or Error.
   * Also see{@link JSONConstants.ResultCode}
   */
  private String mResultCode;

  private List<Message> mMessageList;

  public ResponseMessages(String mResultCode) {
    this.mResultCode = mResultCode;
    this.mMessageList = new ArrayList<>();
  }

  public ResponseMessages(Parcel in) {
    readFromParcel(in);
  }

  /**
   * Get Result code of Transaction.
   *
   * @return String, returns "Ok" if successful or "Error" in case of failure.
   */
  public String getResultCode() {
    return mResultCode;
  }

  public void setResultCode(String mResultCode) {
    this.mResultCode = mResultCode;
  }

  public List<Message> getMessageList() {
    return mMessageList;
  }

  public void setMessageList(List<Message> mMessageList) {
    this.mMessageList = mMessageList;
  }

  public boolean addMessage(Message message) {
    if (mMessageList == null) return false;
    mMessageList.add(message);
    return true;
  }

  // ---------- Code for Parcelable interface ----------

  public void readFromParcel(Parcel in) {
    mResultCode = in.readString();
    mMessageList = in.createTypedArrayList(Message.CREATOR);
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(mResultCode);
    dest.writeTypedList(mMessageList);
  }

  @Override public int describeContents() {
    return 0;
  }

  public static final Creator<ResponseMessages> CREATOR = new Creator<ResponseMessages>() {
    @Override public ResponseMessages createFromParcel(Parcel in) {
      return new ResponseMessages(in);
    }

    @Override public ResponseMessages[] newArray(int size) {
      return new ResponseMessages[size];
    }
  };
}
