package com.card.application.controller;

import com.card.application.exception.CreditCardException;
import com.card.application.repository.CardDetailsRepository;
import com.card.application.services.CardManagementService;
import com.card.application.vo.CardDetailsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(value = "/api/v1/credit-card")
public class CardServiceController {

    private final Logger logger = LoggerFactory.getLogger(CardServiceController.class);

    @Autowired
    CardManagementService cardManagementService;

    /**
     * This service will accecpt card details and store them into H2 DB.
     * @param requestVo
     * @return
     */
    @RequestMapping(
            value = "/add",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CardDetailsVo> addNewCard(@Valid @RequestBody final CardDetailsVo requestVo) throws CreditCardException {
        logger.info("Inside Controller to Add New card and Card details to persisit is ={}",requestVo);
        CardDetailsVo cardDataPostSave = cardManagementService.addNewCard(requestVo);
        return new ResponseEntity<>(cardDataPostSave,HttpStatus.CREATED);

    }


    /**
     * This service retrieve all card details being stored into H2 DB
     * @return
     */
    @RequestMapping(
            value = "/retrieve-all",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<CardDetailsVo>> retrieveAllCards() throws CreditCardException {
        return new ResponseEntity<>(cardManagementService.retrieveAllCards(),HttpStatus.OK);
    }
}
