package com.gagan.creditcard.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * 
 * @author Gaganpreet Singh
 * 
 * This is a utility class to check the number against Luhn10 algorithm
 */
public class Luhn10Algorithm {

	private static final Logger LOGGER = LoggerFactory.getLogger(Luhn10Algorithm.class);

	/**
	 * 
	 * @param targetNumber
	 *            - Must be a number without any spaces. Caller needs to remove all
	 *            whitespace in the given string before calling this method
	 * @return true if given string/number validate against Luhn10 algo else false
	 */
	public static boolean validateLuhn(String number) {

		boolean result = false;

		if (!StringUtils.isEmpty(number)) {
			boolean oddIndex = false;
			int digitSum = 0;
			try {
				for (int index = 0; index < number.length(); index++) {

					int digit = Integer.parseInt(number.charAt(index) + "");

					if (oddIndex) {
						digit *= 2;

						if (digit > 9) {
							digit = digit % 10 + digit / 10;
						}
					}
					digitSum += digit;
					oddIndex = !oddIndex;
				}

				result = (digitSum % 10 == 0);
			} catch (NumberFormatException e) {
				LOGGER.error("Invalid number found while parsing" + e.getMessage());
			}
		}
		return result;
	}
}
