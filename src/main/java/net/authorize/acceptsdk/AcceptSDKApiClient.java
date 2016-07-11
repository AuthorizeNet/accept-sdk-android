package net.authorize.acceptsdk;

import android.content.Context;

import net.authorize.acceptsdk.datamodel.transaction.callbacks.EncryptTransactionCallback;
import net.authorize.acceptsdk.datamodel.transaction.EncryptTransactionObject;
import net.authorize.acceptsdk.internal.AcceptSDKCore;
import net.authorize.acceptsdk.network.ConnectionData;

import java.lang.ref.WeakReference;

/**
 * Created by kbollepa on 7/7/2016.
 */
public class AcceptSDKApiClient {

    private static WeakReference<Context> sContext;

    public enum Environment {SANDBOX, PRODUCTION}

    ;

    private final Environment mEnvironment;


    public AcceptSDKApiClient(Builder builder) {
        sContext = builder.context;
        mEnvironment = builder.environment;
        configureConnectionTimeout(builder.connectionTimeout);
        setActiveEndPointUrl();
    }

    public Environment getEnvironment() {
        return mEnvironment;
    }

    public static WeakReference<Context> getContext() {
        return sContext;
    }

    private void configureConnectionTimeout(int timeoutMillis) {
        ConnectionData.setConnectionTimeout(timeoutMillis);
    }

    private void setActiveEndPointUrl() {
        String url = (this.mEnvironment == Environment.PRODUCTION) ? ConnectionData.ENDPOINT_PRODUCTION :
                ConnectionData.ENDPOINT_SANDBOX;
        ConnectionData.setActiveEndPointUrl(url);
    }

    public void performEncryption(EncryptTransactionObject transactionObject, EncryptTransactionCallback callback) {
        if (transactionObject == null)
            throw new NullPointerException("Transaction Object must not be null");
        AcceptSDKCore.getInstance().performEncryption(transactionObject, callback);
    }

    public static class Builder {
        private final WeakReference<Context> context;
        private final Environment environment;
        private int connectionTimeout;

        public Builder(Context context, Environment environment) {
            if (context == null)
                throw new NullPointerException("Context must not be null");
            this.context = new WeakReference<Context>(context);
            this.environment = (environment == null) ? Environment.SANDBOX : environment;
        }

        public AcceptSDKApiClient.Builder setConnectionTimeout(int timeout) {
            this.connectionTimeout = timeout;
            return this;
        }

        public AcceptSDKApiClient build() {
            return new AcceptSDKApiClient(this);
        }

    }

}
