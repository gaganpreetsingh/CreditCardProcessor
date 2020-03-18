package com.gagan.creditcard.api;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gagan.creditcard.api.validator.CreditCardValidator;
import com.gagan.creditcard.api.response.ApiResponse;
import com.gagan.creditcard.api.response.ResponseStatus;
import com.gagan.creditcard.constant.Constants;
import com.gagan.creditcard.exception.DuplicateCardException;
import com.gagan.creditcard.model.Card;
import com.gagan.creditcard.service.CreditCardService;

@RestController
@RequestMapping("/api/v1/cards/*")
public class CardController {

	@Autowired
	CreditCardService creditCardService;

	@PostMapping
	public ApiResponse saveCard(@Valid @RequestBody Card card) {
		ApiResponse apiResponse = new ApiResponse();

		if (!StringUtils.isEmpty(card.getCardNumber())) {
			card.setCardNumber(card.getCardNumber().replaceAll(Constants.WHITESPACE, Constants.EMPTY_STRING));
		}
		apiResponse = CreditCardValidator.validateCard(card);

		if (!apiResponse.hasError()) {
			try {
				Card returnedCard = creditCardService.add(card);
				if (Objects.isNull(creditCardService)) {
					apiResponse.setStatus(ResponseStatus.ERROR.getStatusName());
					apiResponse.setData("Error occured while saving Card details");
				} else {
					apiResponse.setStatus(ResponseStatus.SUCCESS.getStatusName());
					apiResponse.setData(returnedCard);
				}
			} catch (DuplicateCardException dupEx) {
				apiResponse.setStatus(ResponseStatus.ERROR.getStatusName());
				apiResponse.setData(Constants.CARD_ALREADY_EXISTED);
			}
		}
		return apiResponse;
	}

	@GetMapping
	public ApiResponse getAllCards() {
		ApiResponse apiResponse = new ApiResponse();
		List<Card> list = creditCardService.getAllCards();
		apiResponse.setStatus(ResponseStatus.SUCCESS.getStatusName());
		apiResponse.setData(list);
		Collections.sort(list);
		return apiResponse;
	}
}
