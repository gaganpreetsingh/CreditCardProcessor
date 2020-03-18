package com.gagan.creditcard.dao;

import java.util.List;

import com.gagan.creditcard.model.Card;

public interface CreditCardRepositoryCustom {

	public List<Card> getCardByNumber(String cardNumber);
}
