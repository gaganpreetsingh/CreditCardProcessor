package com.gagan.creditcard.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gagan.constant.Constants;
import com.gagan.creditcard.dao.CreditCardRepository;
import com.gagan.creditcard.model.Card;
import com.gagan.creditcard.service.CreditCardService;
import com.gagan.creditcard.util.Luhn10Algorithm;

@Service
public class CreditCardServiceImpl implements CreditCardService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreditCardServiceImpl.class);

	@Autowired
	CreditCardRepository creditCardRepo;

	@Override
	public Card add(Card card) {
		LOGGER.info("add() -- Started");
		Card returnedCard = null;

		if (card != null && card.getCardNumber() != null) {
			String cardNumber = card.getCardNumber().replaceAll(Constants.WHITESPACE, Constants.EMPTY_STRING);

			if (Luhn10Algorithm.validateLuhn(cardNumber)) {
				card.setCardNumber(cardNumber);
				returnedCard = creditCardRepo.save(card);
			} else {
				if(LOGGER.isDebugEnabled()) {
					LOGGER.debug("Luhn-10 algo failed for card number: "+ card.getCardNumber());
				}
			}
		} else {
			if(LOGGER.isDebugEnabled()) {
				LOGGER.debug("Either card object or card number is null"+ card);
			}
		}
		LOGGER.info("add() -- Ended");
		return returnedCard;
	}

}
