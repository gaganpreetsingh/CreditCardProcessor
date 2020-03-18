package com.gagan.creditcard.api.validator;

import com.gagan.creditcard.api.response.ApiResponse;
import com.gagan.creditcard.api.response.ResponseStatus;
import com.gagan.creditcard.constant.Constants;
import com.gagan.creditcard.model.Card;
import com.gagan.creditcard.util.Luhn10Algorithm;

public class CreditCardValidator {

	public static ApiResponse validateCard(Card card) {
		ApiResponse responseEntity = new ApiResponse();

		if (!Luhn10Algorithm.validateLuhn(card.getCardNumber())) {
			responseEntity.setStatus(ResponseStatus.ERROR.getStatusName());
			responseEntity.setData(Constants.Message.INVALID_CARD_NUMBER);
		}
		return responseEntity;
	}

}
