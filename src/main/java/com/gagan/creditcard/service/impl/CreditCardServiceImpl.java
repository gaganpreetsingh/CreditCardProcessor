package com.gagan.creditcard.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.gagan.creditcard.constant.Constants;
import com.gagan.creditcard.dao.CreditCardRepository;
import com.gagan.creditcard.exception.DuplicateCardException;
import com.gagan.creditcard.model.Card;
import com.gagan.creditcard.service.CreditCardService;
import com.gagan.creditcard.util.Luhn10Algorithm;

@Service
public class CreditCardServiceImpl implements CreditCardService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreditCardServiceImpl.class);

	@Autowired
	CreditCardRepository creditCardRepo;

	@Override
	public Card add(Card card) throws DuplicateCardException {
		LOGGER.info("add() -- Started");
		Card returnedCard = null;

		if (Objects.isNull(card) || StringUtils.isEmpty(card.getCardNumber())) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Either card object or card number is null" + card);
			}
		} else {
			String cardNumber = card.getCardNumber().replaceAll(Constants.WHITESPACE, Constants.EMPTY_STRING);

			if (Luhn10Algorithm.validateLuhn(cardNumber)) {
				card.setCardNumber(cardNumber);
				if (!CollectionUtils.isEmpty(creditCardRepo.getCardByNumber(card.getCardNumber()))) {
					LOGGER.info("Card already existed in DB");
					throw new DuplicateCardException(Constants.CARD_ALREADY_EXISTED);
				}
				returnedCard = creditCardRepo.save(card);
				LOGGER.debug("Card data saved into database");
			} else {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Luhn-10 algo failed for card number: " + card.getCardNumber());
				}
			}
		}
		LOGGER.info("add() -- Ended");
		return returnedCard;
	}

	@Override
	public List<Card> getAllCards() {
		LOGGER.info("getAllCards() -- Started");
		List<Card> list = creditCardRepo.findAll();
		if (CollectionUtils.isEmpty(list)) {
			list = new ArrayList<>(0);
			LOGGER.info("No record found!!! ");
		}
		LOGGER.info("getAllCards() -- Ended");
		return list;
	}
}
