package com.gagan.creditcard.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.gagan.creditcard.dao.CreditCardRepository;
import com.gagan.creditcard.exception.DuplicateCardException;
import com.gagan.creditcard.model.Card;
import com.gagan.creditcard.service.impl.CreditCardServiceImpl;

@ExtendWith(SpringExtension.class)
public class CreditCardServiceTest {

	@TestConfiguration
	static class CreditCardServiceTestConfiguration {
		@Bean
		public CreditCardService creditCardService() {
			return new CreditCardServiceImpl();
		}
	}

	Card card = null;

	@BeforeEach
	public void setUp() {
		card = new Card("Gagan", "4769 9765 4321 0010", new BigDecimal(1000));
	}

	@Autowired
	private CreditCardService creditCardService;

	@MockBean
	CreditCardRepository creditCardRepo;

	@Test
	public void testAddCreditCardWithValidData() {
		Mockito.when(creditCardRepo.save(card)).thenReturn(card);
		Card returnedCard = creditCardService.add(card);
		Assertions.assertEquals(card, returnedCard);
	}

	@Test
	public void testAddCreditCardInvalidData() {
		card.setCardNumber("222-233 3333");
		Mockito.when(creditCardRepo.save(card)).thenReturn(card);
		Card returnedCard = creditCardService.add(card);
		Assertions.assertNull(returnedCard);
	}

	@Test
	public void testAddCreditCardWithNullCardObject() {
		card = null;
		Mockito.when(creditCardRepo.save(card)).thenReturn(card);
		Card returnedCard = creditCardService.add(card);
		Assertions.assertNull(returnedCard);
	}

	@Test
	public void testAddCreditCardWithNullCardNumber() {
		card.setCardNumber(null);
		Mockito.when(creditCardRepo.save(card)).thenReturn(card);
		Card returnedCard = creditCardService.add(card);
		Assertions.assertNull(returnedCard);
	}

	@Test
	public void testAddCreditCardWithDuplicateCardNumber() {
		creditCardService.add(card);
		List<Card> cardList = new ArrayList<>();
		cardList.add(card);
		Mockito.when(creditCardRepo.save(card)).thenReturn(card);
		Mockito.when(creditCardRepo.getCardByNumber(card.getCardNumber())).thenReturn(cardList);
		Assertions.assertThrows(DuplicateCardException.class, () -> {
			creditCardService.add(card);
		});
	}
}
