package com.card.application.services;

import com.card.application.exception.CreditCardException;
import com.card.application.vo.CardDetailsVo;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Thia interface expose the method to add and retrive card.
 */
public interface CardManagementService {
    public CardDetailsVo addNewCard(final CardDetailsVo cardDetailsVo) throws CreditCardException;
    public List<CardDetailsVo> retrieveAllCards() throws CreditCardException;

}
