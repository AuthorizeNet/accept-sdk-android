package net.authorize.acceptsdk;

import net.authorize.acceptsdk.datamodel.transaction.response.ErrorTransactionResponse;

/**
 * Validation Callback for  Transaction Data.
 *
 * Created by Kiran Bollepalli on 20,July,2016.
 * kbollepa@visa.com
 */
public interface ValidationCallback {

  /**
   * Callback method for successful validation.
   */
  void OnValidationSuccessful();

  /**
   * Callback method for failed validation.
   *
   * @param errorTransactionResponse {@link ErrorTransactionResponse}
   */
  void OnValidationFailed(ErrorTransactionResponse errorTransactionResponse);
}
