package net.authorize.acceptsdk.datamodel.transaction.response;

/**
 * <b>public enum ResponseReasonCode</b>
 * <br><br>
 *
 */
public enum ResponseReasonCode {
 //TODO : Need to modify this code.
	SDK_RESPONSE_REASON_CODE_SUCCESSFUL_TRANSACTION(100, "Successful transaction", "SUCCESS"), 
	SDK_RESPONSE_REASON_CODE_ONE_OR_MORE_FIELDS_MISSING(101, "The request is missing one or more required fields", "DECLINED"),
	SDK_RESPONSE_REASON_CODE_ONE_OR_MORE_FIELDS_INVALID(102, "One or more fields in the request contains invalid data.", "DECLINED"),
	SDK_RESPONSE_REASON_CODE_PARTIAL_TRANSACTION(110, "Partial transaction", "SUCCESS"),
	SDK_RESPONSE_REASON_CODE_GENERAL_SYSTEM_FAILURE(150, "General system failure.", "DECLINED"),
	SDK_RESPONSE_REASON_CODE_APPROVED_BUT_DECLINED_BECAUSE_AVS(200, "The authorization request was approved by the issuing bank " +
			"but declined by CyberSource because it did not pass the Address Verification System (AVS) check.", "DECLINED"),
	SDK_RESPONSE_REASON_CODE_DECLINED_QUESTIONS(201, "The issuing bank has questions about the request.", "DECLINED"),
	SDK_RESPONSE_REASON_CODE_DECLINED_EXPIRED_CARD(202, "Expired card.", "DECLINED"),
	SDK_RESPONSE_REASON_CODE_DECLINED_GENERAL_DECLINE(203, "General decline of the card.", "DECLINED"),
	SDK_RESPONSE_REASON_CODE_DECLINED_INSUFICCIENT_FUNDS(204, "Insufficient funds in the account.", "DECLINED"),
	SDK_RESPONSE_REASON_CODE_DECLINED_STOLEN_OR_LOST_CARD(205, "Stolen or lost card.", "DECLINED"),
	SDK_RESPONSE_REASON_CODE_DECLINED_ISSUING_BANK_UNAVAILABLE(207, "Issuing bank unavailable.", "DECLINED"),
	SDK_RESPONSE_REASON_CODE_DECLINED_INACTIVE_CARD(208, "Inactive card or card not authorized for card-not-present transactions.", "DECLINED"),
	SDK_RESPONSE_REASON_CODE_DECLINED_CVN_DID_NOT_MATCH(209, "CVN did not match.", "DECLINED"),
	SDK_RESPONSE_REASON_CODE_DECLINED_CARD_CREDIT_LIMIT_REACHED(210, "The card has reached the credit limit.", "DECLINED"),
	SDK_RESPONSE_REASON_CODE_DECLINED_INVALID_CVN(211, "Invalid CVN.", "DECLINED"),
	SDK_RESPONSE_REASON_CODE_UNKNOWN(0,"Unknown or not mapped code", "DECLINED");

	private int reasonCode;
	private String reasonCodeDescription;
	private String shortDisplayableName;

	ResponseReasonCode(int code, String description, String shortDisplayableName) {
		this.reasonCode = code;
		this.reasonCodeDescription = description;
		this.shortDisplayableName = shortDisplayableName;
	}

	/**
	 * <b>public static ResponseReasonCode getResponseReasonCodeByValue(int reasonCode)</b>
	 * <br><br>
	 * Get ResponseReasonCode object by value.
	 * @param reasonCode
	 * @return
	 */
	public static ResponseReasonCode getResponseReasonCodeByValue(int reasonCode) {
		switch (reasonCode) {
		case 100:
			return ResponseReasonCode.SDK_RESPONSE_REASON_CODE_SUCCESSFUL_TRANSACTION;
		case 101:
			return ResponseReasonCode.SDK_RESPONSE_REASON_CODE_ONE_OR_MORE_FIELDS_INVALID;
		case 110:
			return ResponseReasonCode.SDK_RESPONSE_REASON_CODE_PARTIAL_TRANSACTION;
		case 150:
			return ResponseReasonCode.SDK_RESPONSE_REASON_CODE_GENERAL_SYSTEM_FAILURE;
		case 200:
			return ResponseReasonCode.SDK_RESPONSE_REASON_CODE_APPROVED_BUT_DECLINED_BECAUSE_AVS;
		case 201:
			return ResponseReasonCode.SDK_RESPONSE_REASON_CODE_DECLINED_QUESTIONS;
		case 202:
			return ResponseReasonCode.SDK_RESPONSE_REASON_CODE_DECLINED_EXPIRED_CARD;
		case 203:
			return ResponseReasonCode.SDK_RESPONSE_REASON_CODE_DECLINED_GENERAL_DECLINE;
		case 204:
			return ResponseReasonCode.SDK_RESPONSE_REASON_CODE_DECLINED_INSUFICCIENT_FUNDS;
		case 205:
			return ResponseReasonCode.SDK_RESPONSE_REASON_CODE_DECLINED_STOLEN_OR_LOST_CARD;
		case 207:
			return ResponseReasonCode.SDK_RESPONSE_REASON_CODE_DECLINED_ISSUING_BANK_UNAVAILABLE;
		case 208:
			return ResponseReasonCode.SDK_RESPONSE_REASON_CODE_DECLINED_INACTIVE_CARD;
		case 209:
			return ResponseReasonCode.SDK_RESPONSE_REASON_CODE_DECLINED_CVN_DID_NOT_MATCH;
		case 210:
			return ResponseReasonCode.SDK_RESPONSE_REASON_CODE_DECLINED_CARD_CREDIT_LIMIT_REACHED;
		case 211:
			return ResponseReasonCode.SDK_RESPONSE_REASON_CODE_DECLINED_INVALID_CVN;
		default:
			ResponseReasonCode unknownCode = ResponseReasonCode.SDK_RESPONSE_REASON_CODE_UNKNOWN;
			unknownCode.setReasonCode(reasonCode);
			return unknownCode;
		}
	}

	/**
	 * <b>public static ResponseReasonCode getResponseReasonCodeByValueMapping(String reasonCode)</b>
	 * <br><br>
	 * Map value of reason code and return SDKResponseCode object.
	 * 
	 * @param reasonCode
	 * @return properly mapped reason code; if mapping was unsuccessful returns ResponseReasonCode.SDK_RESPONSE_REASON_CODE_UNKNOWN with reason code 0
	 */
	public static ResponseReasonCode getResponseReasonCodeByValueMapping(String reasonCode) {
		int code = 0;
		if (reasonCode != null) {
			try {
				code = Integer.parseInt(reasonCode);
			} catch (NumberFormatException e) {
				// do nothing
			}
		}

		return getResponseReasonCodeByValue(code);

	}

	/**
	 * <b>public int getReasonCode()</b>
	 * <br><br>
	 * Get value of this reason code.
	 * @return reason code
	 */
	public int getReasonCode() {
		return reasonCode;
	}

	/**
	 * <b>public String getDescription()</b>
	 * <br><br>
	 * Get description of this reason code.
	 * @return reason code description
	 */
	public String getDescription() {
		return reasonCodeDescription;
	}

	/**
	 * <b>public String getShortDisplayableName()</b>
	 * <br><br>
	 * Get short name for this reason code so it could be used somewhere to inform user about status.
	 * @return displayable name
	 */
	public String getShortDisplayableName() {
		return shortDisplayableName;
	}

	private void setReasonCode(int reasonCode) {
		this.reasonCode = reasonCode;
	}
}
