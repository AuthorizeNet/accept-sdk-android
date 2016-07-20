package net.authorize.acceptsdk;

import java.util.Calendar;

/**
 * Class contains utility methods to validate card details.
 *
 * Created by kbollepa on 7/7/2016.
 */
public class ValidationManager {

/* ----------------- Validations related to Card data -------------------------*/

  /**
   * Method validates given card number is valid w.r.t Luhn formula
   *
   * @return true if card is valid and false for invalid card.
   */
  public static boolean isValidCardNumber(String cardNumber) {
    if (cardNumber == null) {
      return false;
    }
    cardNumber = cardNumber.trim();
    if (cardNumber.length() < 6) {
      return false;
    }
    if (!cardNumber.matches("\\d+")) {
      return false;
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
      return false;
    }
  }

  /**
   * Method validates expiration month of card
   *
   * @param month a string in two-digit format
   */
  public static boolean isValidExpirationMonth(String month) {

    if (month == null || month.length() != 2 || (!month.matches("\\d+"))) {
      return false;
    }

    int checkMonth = Integer.parseInt(month);
    if (checkMonth < 1 || checkMonth > 12) {
      return false;
    }

    return true;
  }

  /**
   * Method validates expiration year
   *
   * @param year a string in four digit format
   */
  public static boolean isValidExpirationYear(String year) {
    if (year == null || year.length() != 4 || (!year.matches("\\d+"))) {
      return false;
    }

    return true;
  }

  public static boolean isValidExpirationDate(String month, String year) {
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    int currentMonth =
        Calendar.getInstance().get(Calendar.MONTH) + 1; // since JANUARY = 0 for Calendar class
    int checkYear = Integer.parseInt(year);
    int checkMonth = Integer.parseInt(month);

    if (checkYear < currentYear) {
      return false;
    } else if ((checkYear == currentYear) && (checkMonth < currentMonth)) {
      return false;
    }
    return true;
  }

  /**
   * Method validates cvv code of card.
   * CVV code should be numeric string and length should be 3 or 4.
   *
   * @param cvvCode a string
   */
  public static boolean isValidCVV(String cvvCode) {
    if (cvvCode == null || (cvvCode.length() < 3 || cvvCode.length() > 4)) {
      return false;
    }

    if (!cvvCode.matches("\\d+")) {
      return false;
    }
    return true;
  }

  /**
   * Method validates zip code of card.
   * zip code length should be between 1 & 20.
   *
   * @param zipCode a string
   */
  public static boolean isValidZipCode(String zipCode) {
    if (zipCode == null || (zipCode.length() < 1 || zipCode.length() > 20)) {
      return false;
    }
    return true;
  }

  /**
   * Method validates card holder name.
   * Card holder name length should be between 1 & 64.
   *
   * @param fullName a string
   */

  public static boolean isValidCardHolderName(String fullName) {
    if (fullName == null || (fullName.length() < 1 || fullName.length() > 64)) {
      return false;
    }
    return true;
  }

  /* ----------------- Validations related to Finger Print -------------------------*/

  public static boolean isValidAmount(String amount) {
    if (amount == null || (amount.isEmpty())) {
      return false;
    }
    final String regExp = "[0-9]+([,.][0-9]{1,2})?";
    if (!amount.matches(regExp)) {
      return false;
    }
    return true;

  }
  /* ----------------- Common Validations-------------------------*/
  public static boolean isValidString(String string){
    if (string == null || (string.isEmpty())) {
      return false;
    }
    return true;
  }
}


