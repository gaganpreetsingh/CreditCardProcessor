package com.gagan.creditcard.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;

import com.gagan.creditcard.dao.CreditCardRepositoryCustom;
import com.gagan.creditcard.model.Card;

public class CreditCardRepositoryCustomImpl implements CreditCardRepositoryCustom {

	@Autowired
	EntityManager entityManager;

	public List<Card> getCardByNumber(String cardNumber) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Card> criteriaQuery = cb.createQuery(Card.class);
		Root<Card> root = criteriaQuery.from(Card.class);
		Predicate predicate = cb.equal(root.get("cardNumber"), cardNumber);
		criteriaQuery.select(root).where(predicate);

		return entityManager.createQuery(criteriaQuery).getResultList();
	}

}
