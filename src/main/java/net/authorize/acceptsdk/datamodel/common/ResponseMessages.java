package net.authorize.acceptsdk.datamodel.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kiran Bollepalli on 12,July,2016.
 * kbollepa@visa.com
 */
public class ResponseMessages {

  private String mResultCode;
  private List<Message> mMessageList;

  public ResponseMessages(){
    //this.mMessageList = new ArrayList<Message>();
  }
  public ResponseMessages(String mResultCode) {
    this.mResultCode = mResultCode;
    this.mMessageList = new ArrayList<Message>();
  }

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

  public void addMessage(Message message) {
    if (mMessageList == null) throw new NullPointerException("Message List is not created");
    mMessageList.add(message);
  }
}
