package net.authorize.acceptsdk.datamodel.transaction.response;

import net.authorize.acceptsdk.datamodel.common.ResponseMessages;

/**
 * Created by Kiran Bollepalli on 12,July,2016.
 * kbollepa@visa.com
 */
public class TransactionResponse {

  ResponseMessages responseMessages;

  TransactionResponse() {
  }

  TransactionResponse(ResponseMessages responseMessages) {
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
}
