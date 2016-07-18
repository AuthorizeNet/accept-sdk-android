package net.authorize.acceptsdk;

import java.util.Calendar;
import net.authorize.acceptsdk.exception.AcceptInvalidCardException;
import net.authorize.acceptsdk.exception.AcceptSDKException;

/**
 * Class contains utility methods to validate card details.
 *
 * Created by kbollepa on 7/7/2016.
 */
public class ValidationManager {
  /**
   * Method validates given card number is valid w.r.t Luhn formula
   *
   * @return true if card is valid and false for invalid card.
   * @throws AcceptInvalidCardException, If card number is not valid
   */
  public static boolean isValidCardNumber(String cardNumber) throws AcceptInvalidCardException {
    if (cardNumber == null) {
      throw new AcceptInvalidCardException();
    }
    cardNumber = cardNumber.trim();
    if (cardNumber.length() < 6) {
      throw new AcceptInvalidCardException();
    }
    if (!cardNumber.matches("\\d+")) {
      throw new AcceptInvalidCardException();
    }

    // reversing the string
    String reversedNumber = new StringBuilder(cardNumber).reverse().toString();

    String mutilatedString = "";

    // Even digits are doubled, odd digits are not modified
    for (int i = 0; i < reversedNumber.length(); i++) {
      int c = Integer.parseInt(String.valueOf(reversedNumber.charAt(i)));
      if (i % 2 != 0) {
        c *= 2;
      }
      mutilatedString += c;
    }

    int sumOfDigits = 0;

    // summing up all digits
    for (int i = 0; i < mutilatedString.length(); i++) {
      int c = Integer.parseInt(String.valueOf(mutilatedString.charAt(i)));
      sumOfDigits += c;
    }

    if (sumOfDigits > 0 && sumOfDigits % 10 == 0) {
      return true;
    } else {
      throw new AcceptInvalidCardException();
    }
  }

  /**
   * Method validates expiration date of card
   *
   * @param month a string in two-digit format
   * @param year a string in four digit format
   * @throws AcceptInvalidCardException, If expiration date is not valid
   */
  public static boolean isValidExpirationDate(String month, String year)
      throws AcceptInvalidCardException {
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    int currentMonth =
        Calendar.getInstance().get(Calendar.MONTH) + 1; // since JANUARY = 0 for Calendar class

    if (!month.matches("\\d+")) {
      throw new AcceptInvalidCardException(
          AcceptInvalidCardException.INVALID_CARD_EXPIRATION_MONTH);
    }
    if (!year.matches("\\d+")) {
      throw new AcceptInvalidCardException(AcceptInvalidCardException.INVALID_CARD_EXPIRATION_YEAR);
    }
    if (month.length() != 2 || year.length() != 4) {
      throw new AcceptInvalidCardException(AcceptInvalidCardException.INVALID_CARD_DATE_FORMAT);
    }

    int checkYear = Integer.parseInt(year);
    int checkMonth = Integer.parseInt(month);
    if (checkMonth < 1 || checkMonth > 12) {
      throw new AcceptInvalidCardException(
          AcceptInvalidCardException.INVALID_CARD_EXPIRATION_MONTH);
    }
    if (checkYear < currentYear) {
      throw new AcceptInvalidCardException(AcceptInvalidCardException.INVALID_CARD_EXPIRATION_YEAR);
    } else if ((checkYear == currentYear) && (checkMonth < currentMonth)) {
      throw new AcceptInvalidCardException(AcceptInvalidCardException.INVALID_CARD_EXPIRATION_DATE);
    }

    return true;
  }

  /**
   * Method validates cvv code of card.
   * <p>CVV code should be numeric string and
   * length should be 3 or 4. Other wise throws {@link AcceptInvalidCardException} </>
   *
   * @param cvvCode a string
   * @throws AcceptInvalidCardException
   */
  public static boolean isValidCVV(String cvvCode) throws AcceptInvalidCardException {
    if (cvvCode == null || (cvvCode.length() < 3 || cvvCode.length() > 4)) {
      throw new AcceptInvalidCardException(AcceptInvalidCardException.INVALID_CVV);
    }

    if (!cvvCode.matches("\\d+")) {
      throw new AcceptInvalidCardException(AcceptInvalidCardException.INVALID_CVV);
    }
    return true;
  }

  /**
   * Method validates zip code of card.
   * <p> zip code length should be between 1 & 20. Other wise throws {@link
   * AcceptInvalidCardException} </>
   *
   * @param zipCode a string
   * @throws AcceptInvalidCardException
   */
  public static boolean isValidZipCode(String zipCode) throws AcceptInvalidCardException {
    if (zipCode == null || (zipCode.length() < 1 || zipCode.length() > 20)) {
      throw new AcceptInvalidCardException(AcceptInvalidCardException.INVALID_ZIP);
    }
    return true;
  }

  /**
   * Method validates card holder name.
   * <p> ard holder name length should be between 1 & 64. Other wise throws {@link
   * AcceptInvalidCardException} </>
   *
   * @param fullName a string
   * @throws AcceptInvalidCardException
   */

  public static boolean isValidCardHolderName(String fullName) throws AcceptInvalidCardException {
    if (fullName == null || (fullName.length() < 1 || fullName.length() > 64)) {
      throw new AcceptInvalidCardException(AcceptInvalidCardException.INVALID_CARD_HOLDER_NAME);
    }
    return true;
  }

  public static boolean isValidFingerPrintHashValue(String hashValue) throws AcceptSDKException {
    if (hashValue == null || (hashValue.isEmpty())) {
      throw new AcceptSDKException(AcceptSDKException.INVALID_FINGER_PRINT_HASH_VALUE);
    }
    return true;
  }



  public static boolean isValidAmount(String amount) throws AcceptSDKException {
    if (amount == null || (amount.isEmpty())) {
      throw new AcceptSDKException(AcceptSDKException.INVALID_AMOUNT);
    }
    final String regExp = "[0-9]+([,.][0-9]{1,2})?";
    if (!amount.matches(regExp)) {
      throw new AcceptSDKException(AcceptSDKException.INVALID_AMOUNT);
    }
    return true;
  }
}


