package net.authorize.acceptsdk;

import net.authorize.acceptsdk.datamodel.transaction.response.ErrorTransactionResponse;

/**
 * Created by Kiran Bollepalli on 20,July,2016.
 * kbollepa@visa.com
 */
public interface ValidationCallback {

  void OnValidationSuccessful();

  void OnValidationFailed(ErrorTransactionResponse errorTransactionResponse);
}
