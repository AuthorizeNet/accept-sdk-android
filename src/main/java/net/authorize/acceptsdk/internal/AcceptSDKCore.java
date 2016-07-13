package net.authorize.acceptsdk.internal;

import android.os.Bundle;
import android.os.Handler;
import net.authorize.acceptsdk.AcceptSDKApiClient;
import net.authorize.acceptsdk.datamodel.error.AcceptError;
import net.authorize.acceptsdk.datamodel.transaction.EncryptTransactionObject;
import net.authorize.acceptsdk.datamodel.transaction.callbacks.EncryptTransactionCallback;
import net.authorize.acceptsdk.datamodel.transaction.response.EncryptTransactionResponse;
import net.authorize.acceptsdk.network.AcceptService;
import net.authorize.acceptsdk.network.TransactionResultReceiver;

/**
 * Created by Kiran Bollepalli on 07,July,2016.
 * kbollepa@visa.com
 */
public class AcceptSDKCore implements TransactionResultReceiver.Receiver {

  private static AcceptSDKCore sInstance = new AcceptSDKCore();

  /** Flag to indicate that a transaction is in progress. */
  private static boolean sTransactionInProgress = false;
  private TransactionResultReceiver mResultReceiver = null;
  EncryptTransactionCallback mEncryptTransactionCallback;

  private AcceptSDKCore() {

  }

  public static AcceptSDKCore getInstance() {
    return sInstance;
  }

  public boolean performEncryption(EncryptTransactionObject transactionObject,
      EncryptTransactionCallback callback) {
    if (sTransactionInProgress) return sTransactionInProgress;
    if (transactionObject == null) return false;
    registerResultReceiver();
    sTransactionInProgress = true;
    mEncryptTransactionCallback = callback;

    AcceptService.startActionEncrypt(AcceptSDKApiClient.getContext().get(), transactionObject,
        mResultReceiver);
    return sTransactionInProgress;
  }

  private void registerResultReceiver() {
    if (mResultReceiver != null) return;
    mResultReceiver = new TransactionResultReceiver(new Handler());
    mResultReceiver.setReceiver(this);
  }

  @Override public void onReceiveResult(int resultCode, Bundle resultData) {

    sTransactionInProgress = false;
    switch (resultCode) {
      case AcceptService.SERVICE_RESULT_CODE_SDK_RESPONSE:
        EncryptTransactionResponse response = (EncryptTransactionResponse) resultData.getParcelable(
            AcceptService.SERVICE_RESULT_RESPONSE_KEY);
        mEncryptTransactionCallback.onEncryptionFinished(response);
        break;
      case AcceptService.SERVICE_RESULT_CODE_SDK_ERROR:
        AcceptError error =
            (AcceptError) resultData.getSerializable(AcceptService.SERVICE_RESULT_ERROR_KEY);
        mEncryptTransactionCallback.onErrorReceived(error);
        break;
    }
  }
}
