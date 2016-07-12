package net.authorize.acceptsdk.network;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Xml;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.SocketTimeoutException;
import javax.net.ssl.HttpsURLConnection;
import net.authorize.acceptsdk.common.error.AcceptError;
import net.authorize.acceptsdk.common.error.AcceptGatewayError;
import net.authorize.acceptsdk.common.error.AcceptInternalError;
import net.authorize.acceptsdk.datamodel.transaction.EncryptTransactionObject;
import net.authorize.acceptsdk.datamodel.transaction.response.EncryptTransactionResponse;
import net.authorize.acceptsdk.parser.AcceptSDKParser;
import net.authorize.acceptsdk.util.LogUtil;
import org.json.JSONException;

import static net.authorize.acceptsdk.parser.JSONConstants.ResultCode;
import static net.authorize.acceptsdk.util.LogUtil.LOG_LEVEL;

/**
 * Handling asynchronous task requests in
 * a service on a separate handler thread for Accept
 * <p/>
 *
 * Created by Kiran Bollepalli on 07,July,2016.
 * kbollepa@visa.com
 */
public class AcceptService extends IntentService {

  /** Reason code: 100 */
  private static String REASON_CODE_OK = "100";
  /** Reason code: 120 */
  private static String REASON_CODE_DISCOUNTED_OK = "120";
  /** Reason code: 101 */
  private static String REASON_CODE_MISSING_FIELD = "101";
  /** Reason code: 102 */
  private static String REASON_CODE_INVALID_FIELD = "102";
  /** Reason code: 110 */
  private static String REASON_CODE_PARTIAL = "110";
  private final static String POST = "POST";

  public static final String ACTION_ENCRYPT = "net.authorize.action.ENCRYPT";

  private static final String EXTRA_PARAM_TRANSACTION_OBJECT =
      "net.authorize.extra.TRANSACTION_OBJECT";
  private static final String EXTRA_PARAM_RESULT_RECEIVER = "net.authorize.extra.RESULT_RECEIVER";

  public static final String SERVICE_RESULT_RESPONSE_KEY = "SERVICE_RESULT_RESPONSE_KEY";
  public static final String SERVICE_RESULT_ERROR_KEY = "SERVICE_RESULT_ERROR_KEY";
  public static final int SERVICE_RESULT_CODE_SDK_RESPONSE = 100;
  public static final int SERVICE_RESULT_CODE_SDK_ERROR = 200;

  /**
   * Starts this service to perform action CONNECT with the given parameters. If
   * the service is already performing a task this action will be queued.
   *
   * @param transactionObject - Envelope that will be send to Gateway
   * @param resultReceiver - result receiver to notify the gateway when the service has a result
   * @see IntentService
   */
  public static void startActionEncrypt(Context context,
      final EncryptTransactionObject transactionObject, TransactionResultReceiver resultReceiver) {
    Intent intent = new Intent(context, AcceptService.class);
    intent.setAction(ACTION_ENCRYPT);
    Bundle bundle = new Bundle();
    bundle.putSerializable(EXTRA_PARAM_TRANSACTION_OBJECT, transactionObject);
    bundle.putParcelable(EXTRA_PARAM_RESULT_RECEIVER, resultReceiver);
    intent.putExtras(bundle);
    context.startService(intent);
  }

  public AcceptService() {
    super("InAppConnectionService");
  }

  @Override protected void onHandleIntent(Intent intent) {
    if (intent != null) {
      final String action = intent.getAction();

      switch (action) {
        case ACTION_ENCRYPT:
          final EncryptTransactionObject transactionObject =
              (EncryptTransactionObject) intent.getSerializableExtra(
                  EXTRA_PARAM_TRANSACTION_OBJECT);
          final ResultReceiver resultReceiver =
              intent.getParcelableExtra(EXTRA_PARAM_RESULT_RECEIVER);
          Object result = handleActionEncrypt(transactionObject);
          onPostHandleAction(result, resultReceiver);
          break;
      }
    }
  }

  /**
   * Handle action Foo in the provided background thread with the provided
   * parameters.
   */
  private Object handleActionEncrypt(EncryptTransactionObject transactionObject) {
    Object resultObject = null;
    String url = ConnectionData.getActiveEndPointUrl();
    try {

      HttpsURLConnection urlConnection = SDKUtils.getHttpsURLConnection(url, POST, true);
      urlConnection.setRequestProperty(ConnectionData.CONTENT_TYPE_LABEL,
          ConnectionData.CONTENT_TYPE_APPLICATION_JSON);
      urlConnection.setConnectTimeout(ConnectionData.getConnectionTimeout());

      OutputStream os = urlConnection.getOutputStream();
      BufferedWriter writer =
          new BufferedWriter(new OutputStreamWriter(os, Xml.Encoding.UTF_8.name()));
      writer.write(AcceptSDKParser.getJsonFromEncryptTransaction(transactionObject)); //Json data
      writer.flush();
      writer.close();
      os.close();

      int responseCode = urlConnection.getResponseCode();
      if (responseCode == HttpsURLConnection.HTTP_OK) {
        /* COMMENT: Check Result code.
         *   > If it is "Ok" that means transaction is successful.
         *   > If it is "Error" that means transaction is failed.
         */
        String responseString = SDKUtils.convertStreamToString(urlConnection.getInputStream());
        LogUtil.log(LOG_LEVEL.INFO, " response string :" + responseString);
        String resultCode = AcceptSDKParser.getResultCodeFromResponse(responseString);

        if (resultCode.equals(ResultCode.OK)) {
          EncryptTransactionResponse result =
              AcceptSDKParser.createEncryptionResponse(responseString);
          resultObject = result;
        } else { //Error case
          AcceptGatewayError error = SDKGatewayErrorMapping.getGatewayError(result.reasonCode);
          if (result.reasonCode.equals(REASON_CODE_MISSING_FIELD)) {
            error.setErrorExtraMessage(result.missingField);
          } else {
            error.setErrorExtraMessage(result.icsMessage.icsRmsg);
          }
          resultObject = error;
        }
      } else if (responseCode == HttpsURLConnection.HTTP_INTERNAL_ERROR) {
        //TODO: Need to implement this
        AcceptError error = AcceptSDKParser.createErrorResponse(urlConnection.getErrorStream());
         resultObject = error;
      } else {
        AcceptError error = AcceptInternalError.SDK_INTERNAL_ERROR_NETWORK_CONNECTION;
        error.setErrorExtraMessage(String.valueOf(responseCode));
        resultObject = error;
      }
    } catch (SocketTimeoutException e) {
      e.printStackTrace();
      AcceptError error = AcceptInternalError.SDK_INTERNAL_ERROR_NETWORK_CONNECTION_TIMEOUT;
      error.setErrorExtraMessage(e.getMessage());
      resultObject = error;
    } catch (IOException e) {
      e.printStackTrace();
       AcceptError error = AcceptInternalError.SDK_INTERNAL_ERROR_NETWORK_CONNECTION;
       error.setErrorExtraMessage(e.getMessage());
       resultObject = error;
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return resultObject;
  }

  protected void onPostHandleAction(Object result, ResultReceiver resultReceiver) {
    Bundle resultData = new Bundle();
    if (result instanceof EncryptTransactionResponse) {
      EncryptTransactionResponse response = (EncryptTransactionResponse) result;
      resultData.putParcelable(SERVICE_RESULT_RESPONSE_KEY, response);
      resultReceiver.send(SERVICE_RESULT_CODE_SDK_RESPONSE, resultData);
    }// else if (result instanceof SDKError) {
    //            SDKError response = (SDKError)result;
    //            resultData.putSerializable(SERVICE_RESULT_ERROR_KEY, response);
    //            resultReceiver.send(SERVICE_RESULT_CODE_SDK_ERROR, resultData);
    //        }
  }
}
