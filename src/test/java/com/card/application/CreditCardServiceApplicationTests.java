package com.card.application;

import com.card.application.exception.CreditCardException;
import com.card.application.exception.ExceptionDetails;
import com.card.application.vo.CardDetailsVo;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreditCardServiceApplicationTests {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    public static final String GET_REST_ENDPOINT = "/api/v1/credit-card/retrieve-all";
    public static final String POST_REST_ENDPOINT = "/api/v1/credit-card/add";
    public static HttpHeaders httpHeaders;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeClass
    public static void prepareSecurityHeaders() {
        httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Basic YWRtaW46YWRtaW5u");
    }


    @Test
    @DisplayName("Test to add new card into DB")
    public void testCreateNewCard() {

        CardDetailsVo cardDetailsVo = prepareValidTestData();
        cardDetailsVo.setCardNumber("4417123456789113");
        HttpEntity<CardDetailsVo> entity = new HttpEntity<CardDetailsVo>(cardDetailsVo, httpHeaders);
        ResponseEntity<CardDetailsVo> responseEntity = restTemplate.exchange(POST_REST_ENDPOINT, HttpMethod.POST, entity,CardDetailsVo.class);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());
        log.info("testCreateNewCard completed successfully with response -" + responseEntity.getStatusCode());
    }
    @Test
    @DisplayName("Test to retrieve all card from DB")
    public void testGetAllCards() {

        // add data to retrieve
        CardDetailsVo cardDetailsVo = prepareValidTestData();
        HttpEntity<CardDetailsVo> getEntity = new HttpEntity<CardDetailsVo>(cardDetailsVo, httpHeaders);
        ResponseEntity<CardDetailsVo> responseEntityFromSave = restTemplate.exchange(POST_REST_ENDPOINT, HttpMethod.POST, getEntity,CardDetailsVo.class);

        //Retrieve stored card from db
        HttpEntity<CardDetailsVo> entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<List<CardDetailsVo>> responseEntity = restTemplate.exchange(GET_REST_ENDPOINT, HttpMethod.GET, entity, new ParameterizedTypeReference<List<CardDetailsVo>>() {});
        List<CardDetailsVo> creditCards = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Subhash Sharma", creditCards.get(0).getCustomerName());
        log.info("testGetAllCards completed successfully with response  -" + creditCards.get(0).getCustomerName());
    }

    @Test
    @DisplayName(" Validate card number allready present into DB")
    public void testDuplicateCardValidationBeforeSaving() {
        CreateNewCard();
        CardDetailsVo cardDetailsVo = prepareValidTestData();
        cardDetailsVo.setCardNumber("49927398716");
        HttpEntity<CardDetailsVo> entity = new HttpEntity<CardDetailsVo>(cardDetailsVo, httpHeaders);
        ResponseEntity<CardDetailsVo> responseEntity = restTemplate.exchange(POST_REST_ENDPOINT, HttpMethod.POST, entity,CardDetailsVo.class);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        log.info("testCreateNewCard completed successfully with response -" + responseEntity.getStatusCode());
    }



    @Test
    @DisplayName("Test to check invalid card - using Luhn logic")
    public void testLuhnCardValidationWhileSaving() {

        CardDetailsVo cardDetailsVo = prepareInvalidTestData();
        HttpEntity<CardDetailsVo> entity = new HttpEntity<CardDetailsVo>(cardDetailsVo, httpHeaders);
        ResponseEntity<CardDetailsVo> responseEntity = restTemplate.exchange(POST_REST_ENDPOINT, HttpMethod.POST, entity,CardDetailsVo.class);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        log.info("testCreateNewCard completed successfully with response -" + responseEntity.getStatusCode());
    }




    @Test
    @DisplayName(" test to check exception being thrown for invalid card number")
    public void testCreateNewCardWithCreditCardException() throws Exception {

        CardDetailsVo cardDetailsVo = prepareValidTestData();
        cardDetailsVo.setCardNumber("49927398716");
        HttpEntity<CardDetailsVo> entity = new HttpEntity<CardDetailsVo>(prepareInvalidTestData(), httpHeaders);
        ResponseEntity<ExceptionDetails> response = restTemplate.exchange(POST_REST_ENDPOINT, HttpMethod.POST, entity, ExceptionDetails.class);
        log.info("testCreateNewCardWithCreditCardException completed successfully details -" + response);
    }


    static CardDetailsVo prepareValidTestData() {
        CardDetailsVo cardDetailsVo = new CardDetailsVo();
        cardDetailsVo.setCustomerName("Subhash Sharma");
        cardDetailsVo.setBalanceAmount(100.0);
        cardDetailsVo.setCardNumber("1234567812345670");
        return cardDetailsVo;
    }

    static CardDetailsVo prepareInvalidTestData() {
        CardDetailsVo cardDetailsVo = new CardDetailsVo();
        cardDetailsVo.setCustomerName("Subhash Sharma");
        cardDetailsVo.setBalanceAmount(100.0);
        cardDetailsVo.setCardNumber("1234567812345678");
        return cardDetailsVo;
    }

    public void CreateNewCard() {

        CardDetailsVo cardDetailsVo = prepareValidTestData();
        cardDetailsVo.setCardNumber("49927398716");
        HttpEntity<CardDetailsVo> entity = new HttpEntity<CardDetailsVo>(cardDetailsVo, httpHeaders);
        ResponseEntity<CardDetailsVo> responseEntity = restTemplate.exchange(POST_REST_ENDPOINT, HttpMethod.POST, entity,CardDetailsVo.class);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());
        log.info("testCreateNewCard completed successfully with response -" + responseEntity.getStatusCode());
    }

}
