package net.authorize.acceptsdk.network;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Result Receiver for {@link AcceptService}
 */
public class TransactionResultReceiver extends ResultReceiver {

  private Receiver mReceiver;

  public TransactionResultReceiver(Handler handler) {
    super(handler);
  }

  public interface Receiver {
    void onReceiveResult(int resultCode, Bundle resultData);
  }

  public void setReceiver(Receiver receiver) {
    mReceiver = receiver;
  }

  @Override protected void onReceiveResult(int resultCode, Bundle resultData) {

    if (mReceiver != null) {
      mReceiver.onReceiveResult(resultCode, resultData);
    }
  }
}