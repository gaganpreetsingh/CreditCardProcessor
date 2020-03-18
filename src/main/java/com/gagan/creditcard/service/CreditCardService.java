package com.gagan.creditcard.service;

import java.util.List;

import com.gagan.creditcard.exception.DuplicateCardException;
import com.gagan.creditcard.model.Card;

public interface CreditCardService {

	Card add(Card card) throws DuplicateCardException;
	
	List<Card> getAllCards();
}
