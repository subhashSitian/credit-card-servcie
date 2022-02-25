package com.card.application.services;

import com.card.application.controller.CardServiceController;
import com.card.application.exception.CreditCardException;
import com.card.application.model.CardDetaisEntity;
import com.card.application.repository.CardDetailsRepository;
import com.card.application.utility.CardDetailValidator;
import com.card.application.utility.CreditCardUtil;
import com.card.application.vo.CardDetailsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class CardManagementServiceImp implements CardManagementService {

    private final Logger logger = LoggerFactory.getLogger(CardServiceController.class);

    @Autowired
    CardDetailsRepository cardDetailsRepository;

    @Autowired
    CreditCardUtil cardUtil;

    @Autowired
    CardDetailValidator cardValidator;

    /**
     * This servcie Check :
     * 1-- check card number using Luhn
     * 2-- if card number in request is present into DB then return error else
     * 3-- store the card details into table
     */
    @Override
    public CardDetailsVo addNewCard(final CardDetailsVo cardDetailsVo) throws CreditCardException {
        CardDetaisEntity cardDetaisEntity = cardUtil.convertToCreditCardEntity(cardDetailsVo);
        cardValidator.cardInputValidation(cardDetaisEntity);
        final Optional<CardDetaisEntity> creditCardRecord = cardDetailsRepository.findByCardNumber(cardDetailsVo.getCardNumber());
        if (creditCardRecord.isPresent()) {
            throw new CreditCardException(CreditCardException.DUPLICATE_CARD_NUMBER);
        }
        cardDetaisEntity = cardDetailsRepository.save(cardDetaisEntity);
        logger.info("Card details has been saved Sucesfully ={}",cardDetaisEntity);
        return cardUtil.convertToCardDetailsVo(cardDetaisEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CardDetailsVo> retrieveAllCards() throws CreditCardException {
        try{
            List<CardDetaisEntity> cardDetailsEntity = cardDetailsRepository.findAll();
            return cardDetailsEntity.stream().map(creditcard -> cardUtil.convertToCardDetailsVo(creditcard)).collect(Collectors.toList());
        }catch(Exception exc){
            throw new CreditCardException(CreditCardException.ERROR_RETRIEVING_CARD_DETAILS);
        }

    }
}
