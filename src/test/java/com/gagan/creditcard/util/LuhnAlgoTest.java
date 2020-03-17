package com.gagan.creditcard.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.gagan.creditcard.util.Luhn10Algorithm;

public class LuhnAlgoTest {

	@Test
	public void testLuhnAlgorithmWithValidNumberSeries() {
		boolean result = Luhn10Algorithm.validateLuhn("476997654321001");
		Assertions.assertTrue(result);
	}

	@Test
	public void testLuhnAlgorithmWithInvalidNumberSeries() {
		boolean result = Luhn10Algorithm.validateLuhn("3232");
		Assertions.assertFalse(result);
	}

	@Test
	public void testLuhnAlgorithmWithNonDigitData() {
		boolean result = Luhn10Algorithm.validateLuhn("12##R");
		Assertions.assertFalse(result);
	}

	@Test
	public void testLuhnAlgorithmWithEmptyData() {
		boolean result = Luhn10Algorithm.validateLuhn("");
		Assertions.assertFalse(result);
	}

	@Test
	public void testLuhnAlgorithmWithNullData() {
		boolean result = Luhn10Algorithm.validateLuhn(null);
		Assertions.assertFalse(result);
	}
}
