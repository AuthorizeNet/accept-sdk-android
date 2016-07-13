package net.authorize.acceptsdk.datamodel.transaction.response;

import java.util.List;
import net.authorize.acceptsdk.datamodel.common.Message;
import net.authorize.acceptsdk.datamodel.common.ResponseMessages;

/**
 * Created by Kiran Bollepalli on 12,July,2016.
 * kbollepa@visa.com
 */
public class TransactionResponse {

  ResponseMessages responseMessages;

  public TransactionResponse() {
  }

  public TransactionResponse(ResponseMessages responseMessages) {
    this.responseMessages = responseMessages;
  }

  public ResponseMessages getResponseMessages() {
    return responseMessages;
  }

  public void setResponseMessages(ResponseMessages responseMessages) {
    this.responseMessages = responseMessages;
  }

  public String getResultCode() {
    return responseMessages.getResultCode();
  }

  public Message getFirstMessage() {
    Message message = null;
    if (responseMessages == null) return message;

    List<Message> messageList = responseMessages.getMessageList();
    if (messageList != null && messageList.size() > 0) message = messageList.get(0);

    return message;
  }
}
