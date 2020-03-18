package com.gagan.creditcard.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.util.StringUtils;

import com.gagan.creditcard.constant.Constants;

@Entity
public class Card implements Comparable<Card> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cardId;

	@NotEmpty(message = "Card Holder Name is required")
	private String cardHolderName;

	@NotEmpty(message = "Card Holder Number is required.")
	@Size(max = 19, message = "Max length allowed is 19")
	@Column(unique = true)
	private String cardNumber;

	@Column(name = "card_limit")
	@NotNull(message = "Limit is required")
	private BigDecimal limit;

	private BigDecimal balance;

	public Card() {
		this.balance = new BigDecimal(0); // Set default to 0 as per requirement
	}

	public Card(String cardHolderName, String cardNumber, BigDecimal limit) {
		this();
		this.cardHolderName = cardHolderName;
		this.cardNumber = cardNumber;
		this.limit = limit;
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
		if (!StringUtils.isEmpty(cardNumber)) {
			this.cardNumber = cardNumber.replaceAll(Constants.WHITESPACE, Constants.EMPTY_STRING);
		} else {
			this.cardNumber = cardNumber;
		}
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

	@Override
	public int compareTo(Card card) {
		int x = card.getCardId();
		int y = this.getCardId();
		return (x < y) ? -1 : ((x == y) ? 0 : 1);
	}

}
