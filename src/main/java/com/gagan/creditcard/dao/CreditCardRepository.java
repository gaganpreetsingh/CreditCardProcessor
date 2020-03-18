package com.gagan.creditcard.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gagan.creditcard.model.Card;

public interface CreditCardRepository extends JpaRepository<Card, Integer> {

}
