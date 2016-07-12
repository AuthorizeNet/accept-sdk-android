package net.authorize.acceptsdk.common.error;

/**
 * Describes all error types, which can occurs during payment transactions.
 */
public enum AcceptGatewayError implements AcceptError {

  SDK_GATEWAY_ERROR_MISSING_FIELDS(2101, "The request is missing one or more required fields."),
  SDK_GATEWAY_ERROR_FIELDS_CONTAINS_INVALID_DATA(2102,
      "One or more fields in the request contains invalid data."),
  SDK_GATEWAY_ERROR_ONLY_PARTIAL_APPROVED(2110, "Only a partial amount was approved."),
  SDK_GATEWAY_ERROR_GENERAL_SYSTEM_FAILURE(2150, "General system failure."),
  SDK_GATEWAY_ERROR_SERVER_TIMEOUT(2151,
      "The request was received but there was a server timeout. This error does not include timeouts between the client and the server."),
  SDK_GATEWAY_ERROR_SERVICE_DID_NOT_FINISH(2152,
      "The request was received, but a service did not finish running in time."),
  SDK_GATEWAY_ERROR_DID_NOT_PASS_AVS(2200,
      "The authorization request was approved by the issuing bank but declined by gateway because it did not pass the Address Verification System (AVS) check."),
  SDK_GATEWAY_ERROR_ISSUING_BANK_HAS_QUESTIONS(2201,
      "The issuing bank has questions about the request. You do not receive an authorization code programmatically, but you might receive one verbally by calling the processor."),
  SDK_GATEWAY_ERROR_EXPIRED_CARD(2202,
      "Expired card. You might also receive this if the expiration date you provided does not match the date the issuing bank has on file."),
  SDK_GATEWAY_ERROR_GENERAL_DECLINE(2203,
      "General decline of the card. No other information was provided by the issuing bank."),
  SDK_GATEWAY_ERROR_INSUFFICIENT_FUNDS(2204, "Insufficient funds in the account."),
  SDK_GATEWAY_ERROR_STOLEN_OR_LOST_CARD(2205, "Stolen or lost card."),
  SDK_GATEWAY_ERROR_ISSUING_BANK_UNAVAILABLE(2207, "Issuing bank unavailable."),
  SDK_GATEWAY_ERROR_INACTIVE_CARD(2208,
      "Inactive card or card not authorized for card-not-present transactions."),
  SDK_GATEWAY_ERROR_CID_DID_NOT_MATCH(2209, "Card Identification Digits (CID) did not match."),
  SDK_GATEWAY_ERROR_CREDIT_LIMIT(2210, "The card has reached the credit limit."),
  SDK_GATEWAY_ERROR_INVALID_CVN(2211, "Invalid CVN."),
  SDK_GATEWAY_ERROR_NEGATIVE_FILE(2221,
      "The customer matched an entry on the processor's negative file."),
  SDK_GATEWAY_ERROR_DID_NOT_PASS_CVN_CHECK(2230, "The card has reached the credit limit."),
  SDK_GATEWAY_ERROR_INVALID_ACCOUNT_NUMBER(2231, "Invalid account number."),
  SDK_GATEWAY_ERROR_CARD_TYPE_NOT_EXPECTED(2232,
      "The card type is not accepted by the payment processor."),
  SDK_GATEWAY_ERROR_GENERAL_DECLINE_BY_PROCESSOR(2233, "General decline by the processor."),
  SDK_GATEWAY_ERROR_PROBLEM_WITH_INFORMATION(2234,
      "There is a problem with the information in your gateway account."),
  SDK_GATEWAY_ERROR_CAPTURE_AMOUNT_EXCEEDS(2235,
      "The requested capture amount exceeds the originally authorized amount."),
  SDK_GATEWAY_ERROR_PROCESSOR_FAILURE(2236, "Processor failure."),
  SDK_GATEWAY_ERROR_ALREADY_REVERSED(2237, "The authorization has already been reversed."),
  SDK_GATEWAY_ERROR_ALREADY_CAPTURED(2238, "The authorization has already been captured."),
  SDK_GATEWAY_ERROR_TRANSACTION_AMOUNT_MUST_MATCH_PREVIOUS_AMOUNT(2239,
      "The requested transaction amount must match the previous transaction amount."),
  SDK_GATEWAY_ERROR_CARD_TYPE_INVALID(2240,
      "The card type sent is invalid or does not correlate with the credit card number."),
  SDK_GATEWAY_ERROR_REQUEST_ID_INVALID(2241, "The request ID is invalid."),
  SDK_GATEWAY_ERROR_NO_CORRESPONDING(2242,
      "You requested a capture, but there is no corresponding, unused authorization record. Occurs if there was not a previously successful authorization request or if the previously successful authorization has already been used by another capture request."),
  SDK_GATEWAY_ERROR_TRANSACTION_SETTLED_OR_REVERSED(2243,
      "The transaction has already been settled or reversed."),
  SDK_GATEWAY_ERROR_TIMEOUT_AT_PAYMENT_PROCESSOR(2250,
      "The request was received, but there was a timeout at the payment processor."),
  SDK_GATEWAY_ERROR_STAND_ALONE_CREDITS_NOT_ALLOWED(2254, "Stand-alone credits are not allowed."),
  SDK_GATEWAY_ERROR_INVALID_TOKEN(2255, "Access Token authentication failed."),
  SDK_GATEWAY_ERROR_GENERAL(2999, "Unknown error.");

  private int errorCode;
  private String errorMessage;
  private String errorExtraMessage;

  AcceptGatewayError(int errorCode, String errorMessage) {
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  @Override public int getErrorCode() {
    return errorCode;
  }

  @Override public String getErrorMessage() {
    return errorMessage;
  }

  @Override public String getErrorExtraMessage() {
    return errorExtraMessage;
  }

  @Override public void setErrorExtraMessage(String errorExtraMessage) {
    this.errorExtraMessage = errorExtraMessage;
  }

  @Override public AcceptErrorType getErrorType() {
    return AcceptErrorType.SDK_ERROR_TYPE_GATEWAY;
  }

  public static AcceptGatewayError getGatewayErrorByErrorCode(int errorCode) {
    switch (errorCode) {
      case 2101:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_MISSING_FIELDS;
      case 2102:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_FIELDS_CONTAINS_INVALID_DATA;
      case 2110:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_ONLY_PARTIAL_APPROVED;
      case 2150:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_GENERAL_SYSTEM_FAILURE;
      case 2151:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_SERVER_TIMEOUT;
      case 2152:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_SERVICE_DID_NOT_FINISH;
      case 2200:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_DID_NOT_PASS_AVS;
      case 2201:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_ISSUING_BANK_HAS_QUESTIONS;
      case 2202:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_EXPIRED_CARD;
      case 2203:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_GENERAL_DECLINE;
      case 2204:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_INSUFFICIENT_FUNDS;
      case 2205:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_STOLEN_OR_LOST_CARD;
      case 2207:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_ISSUING_BANK_UNAVAILABLE;
      case 2208:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_INACTIVE_CARD;
      case 2209:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_CID_DID_NOT_MATCH;
      case 2210:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_CREDIT_LIMIT;
      case 2211:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_INVALID_CVN;
      case 2221:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_NEGATIVE_FILE;
      case 2230:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_DID_NOT_PASS_CVN_CHECK;
      case 2231:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_INVALID_ACCOUNT_NUMBER;
      case 2232:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_CARD_TYPE_NOT_EXPECTED;
      case 2233:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_GENERAL_DECLINE_BY_PROCESSOR;
      case 2234:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_PROBLEM_WITH_INFORMATION;
      case 2235:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_CAPTURE_AMOUNT_EXCEEDS;
      case 2236:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_PROCESSOR_FAILURE;
      case 2237:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_ALREADY_REVERSED;
      case 2238:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_ALREADY_CAPTURED;
      case 2239:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_TRANSACTION_AMOUNT_MUST_MATCH_PREVIOUS_AMOUNT;
      case 2240:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_CARD_TYPE_INVALID;
      case 2241:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_REQUEST_ID_INVALID;
      case 2242:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_NO_CORRESPONDING;
      case 2243:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_TRANSACTION_SETTLED_OR_REVERSED;
      case 2250:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_TIMEOUT_AT_PAYMENT_PROCESSOR;
      case 2255:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_INVALID_TOKEN;
      case 2999:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_GENERAL;
      default:
        return AcceptGatewayError.SDK_GATEWAY_ERROR_GENERAL;
    }
  }
}
