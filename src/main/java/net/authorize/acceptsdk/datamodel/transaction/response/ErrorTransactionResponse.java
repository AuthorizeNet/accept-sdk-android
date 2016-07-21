package net.authorize.acceptsdk.datamodel.transaction.response;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import net.authorize.acceptsdk.datamodel.common.Message;
import net.authorize.acceptsdk.datamodel.common.ResponseMessages;
import net.authorize.acceptsdk.datamodel.error.SDKErrorCode;
import net.authorize.acceptsdk.parser.JSONConstants;
import net.authorize.acceptsdk.util.LogUtil;
import net.authorize.acceptsdk.util.SDKUtils;

/**
 * Created by Kiran Bollepalli on 15,July,2016.
 * kbollepa@visa.com
 */
public class ErrorTransactionResponse extends TransactionResponse {

  public ErrorTransactionResponse(ResponseMessages responseMessages) {
    super(responseMessages);
  }

  /**
   * Create Error Response from {@link Message} object.
   *
   * @param errorMessage Message Object.
   * @return {@link ErrorTransactionResponse}
   */
  public static ErrorTransactionResponse createErrorResponse(Message errorMessage) {
    ResponseMessages responseMessages = new ResponseMessages(JSONConstants.ResultCode.ERROR);
    responseMessages.addMessage(errorMessage);
    return new ErrorTransactionResponse(responseMessages);
  }

  /**
   * Create Error Response from Error Stream
   *
   * @param errorCode error Message code.
   * @param errorStream error stream.
   * @return {@link ErrorTransactionResponse}
   * @throws IOException
   */
  public static ErrorTransactionResponse createErrorResponse(String errorCode,
      InputStream errorStream) throws IOException {

    String errorString = SDKUtils.convertStreamToString(errorStream);
    LogUtil.log(LogUtil.LOG_LEVEL.INFO, errorString);
    Message message = new Message(errorCode, errorString);
    return ErrorTransactionResponse.createErrorResponse(message);
  }

  /**
   * Create Error Response
   *
   * @param {@link SDKErrorCode} error code.
   * @param errorMessage error message.
   * @return {@link ErrorTransactionResponse}
   */
  public static ErrorTransactionResponse createErrorResponse(SDKErrorCode errorCode,
      String errorMessage) {
    Message message = new Message(errorCode.getErrorCode(), errorMessage);
    return ErrorTransactionResponse.createErrorResponse(message);
  }

  /**
   * Create Error Response
   *
   * @param {@link SDKErrorCode} error code.
   * @return {@link ErrorTransactionResponse}
   */
  public static ErrorTransactionResponse createErrorResponse(SDKErrorCode errorCode) {
    Message message = new Message(errorCode.getErrorCode(), errorCode.getErrorMessage());
    return ErrorTransactionResponse.createErrorResponse(message);
  }

  public Message getFirstErrorMessage() {
    Message message = null;
    if (responseMessages == null) return message;

    List<Message> messageList = responseMessages.getMessageList();
    if (messageList != null && messageList.size() > 0) message = messageList.get(0);

    return message;
  }

  // ---------- Code for Parcelable interface ----------

  public ErrorTransactionResponse(Parcel in) {
    super(in);
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
  }

  public static final Parcelable.Creator<ErrorTransactionResponse> CREATOR =
      new Parcelable.Creator<ErrorTransactionResponse>() {

        @Override public ErrorTransactionResponse createFromParcel(Parcel in) {
          return new ErrorTransactionResponse(in);
        }

        @Override public ErrorTransactionResponse[] newArray(int size) {
          return new ErrorTransactionResponse[size];
        }
      };
}
