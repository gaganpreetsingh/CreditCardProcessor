package com.gagan.creditcard.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Card {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cardId;

	private String cardHolderName;
	private String cardNumber;

	@Column(name = "card_limit")
	private BigDecimal limit;
	private BigDecimal balance;

	public Card(String cardHolderName, String cardNumber, BigDecimal limit) {
		this.cardHolderName = cardHolderName;
		this.cardNumber = cardNumber;
		this.limit = limit;
		this.balance = new BigDecimal(0);
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public int getCardId() {
		return cardId;
	}

	public void setCardId(int cardId) {
		this.cardId = cardId;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public BigDecimal getLimit() {
		return limit;
	}

	public void setLimit(BigDecimal limit) {
		this.limit = limit;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Card [cardId=" + cardId + ", cardHolderName=" + cardHolderName + ", cardNumber=" + cardNumber
				+ ", limit=" + limit + ", balance=" + balance + "]";
	}

	@Override
	public boolean equals(Object object) {
		boolean isEqual = false;
		
		if (object instanceof Card) {
			Card card = (Card) object;
			isEqual = card.getCardNumber().equals(this.getCardNumber());
		}
		return isEqual;
	}
	
}
