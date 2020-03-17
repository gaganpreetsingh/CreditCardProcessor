package com.gagan.creditcard.util;

import org.junit.platform.commons.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Luhn10Algorithm {

	private static final Logger LOGGER = LoggerFactory.getLogger(Luhn10Algorithm.class);

	public static boolean validateLuhn(String number) {

		boolean result = false;

		if (StringUtils.isNotBlank(number)) {
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
