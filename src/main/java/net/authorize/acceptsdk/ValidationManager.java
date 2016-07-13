package net.authorize.acceptsdk;

import java.util.Calendar;
import net.authorize.acceptsdk.exception.AcceptInvalidCardException;

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
    if (cardNumber == null || cardNumber.length() < 6) {
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
}


