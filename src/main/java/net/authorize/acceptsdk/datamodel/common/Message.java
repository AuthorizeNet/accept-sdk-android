package net.authorize.acceptsdk.datamodel.common;

/**
 * Created by Kiran Bollepalli on 12,July,2016.
 * kbollepa@visa.com
 */
public class Message {

  String mMessageCode;
  String mMessageText;

  public Message() {

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
}
