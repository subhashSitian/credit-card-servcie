package com.card.application;

import com.card.application.exception.CreditCardException;
import com.card.application.model.CardDetaisEntity;
import com.card.application.repository.CardDetailsRepository;
import com.card.application.services.CardManagementServiceImp;
import com.card.application.utility.CardDetailValidator;
import com.card.application.utility.CreditCardUtil;
import com.card.application.vo.CardDetailsVo;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreditCardServiceTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @InjectMocks
    CardManagementServiceImp cardService;
    @Mock
    CardDetailsRepository cardDetailsRepository;
    @Mock
    CardDetailValidator cardDetailValidator;
    @Mock
    CreditCardUtil cardUtil;
    @Mock
    ModelMapper modelMapper;
    @Mock
    CardDetailValidator cardValidator;

    static List<CardDetaisEntity> cardList = new ArrayList<>();
    static CardDetaisEntity cardEntity;
    static CardDetailsVo cardDetailsVo;
    static CreditCardUtil creditCardUtil;

    @BeforeClass
    public static void prepareValidTestData() {

        cardEntity = new CardDetaisEntity();
        cardEntity.setCustomerName("Subhash Sharma");
        cardEntity.setBalanceAmount(0.0);
        cardEntity.setCardNumber("1234567812345670");
        cardList.add(cardEntity);

        cardDetailsVo = new CardDetailsVo();
        cardDetailsVo.setCustomerName("New Customer");
        cardDetailsVo.setBalanceAmount(100.0);
        cardDetailsVo.setCardNumber("123456781234534");

    }

    @AfterClass
    public static void clear() {
        cardEntity = null;
        cardList = null;
    }

    @Test
    @DisplayName("Test to check retrieve all card detail and validate customer name")
    public void testFindAllCards() throws CreditCardException {
        //given
        when(cardDetailsRepository.findAll()).thenReturn(cardList);
        when(cardUtil.convertToCardDetailsVo(any(CardDetaisEntity.class))).thenReturn(cardDetailsVo);
        //when
        String cardHolderName = cardService.retrieveAllCards() .iterator().next().getCustomerName();
        //then
        assertEquals("New Customer", cardHolderName);
        log.info("Successfully run Mocked findAll() Cards");
    }

    @Test
    @DisplayName("Test to save card data and return not null object")
    public void testSaveCardDeatils() throws CreditCardException {
        //given
        when(cardDetailsRepository.save(any(CardDetaisEntity.class))).thenReturn(cardEntity);
        when(cardUtil.convertToCreditCardEntity(any(CardDetailsVo.class))).thenReturn(cardEntity);
        when(cardUtil.convertToCardDetailsVo(any(CardDetaisEntity.class))).thenReturn(cardDetailsVo);

        //when
        CardDetailsVo cardDetailsVo = cardService.addNewCard(cardUtil.convertToCardDetailsVo(cardEntity));
        //then
        assertNotNull(cardDetailsVo);

    }


    @Test(expected = CreditCardException.class)
    @DisplayName("Test new card addition for duplicate record error")
    public void testSaveCardsWithDuplicateRecordError() throws CreditCardException {
        //given
        when(cardUtil.convertToCreditCardEntity(any(CardDetailsVo.class))).thenReturn(cardEntity);
        doNothing().when(cardDetailValidator).cardInputValidation(any(CardDetaisEntity.class));
        when(cardDetailsRepository.findByCardNumber(anyString())).thenReturn(java.util.Optional.of(cardEntity));
         //when
        cardService.addNewCard(cardDetailsVo);
        //then
        //Expecting error
    }
}
