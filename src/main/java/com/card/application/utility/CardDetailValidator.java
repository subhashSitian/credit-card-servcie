package com.card.application.utility;

import com.card.application.exception.CreditCardException;
import com.card.application.model.CardDetaisEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CardDetailValidator {

    private static final Logger logger = LoggerFactory.getLogger(CardDetailValidator.class);
    private static final String REGEX_CARD_NUMBER = "^[0-9]{10,19}$";
    private static final String REGEX_NAME = "^([a-zA-z\\s]{2,20})$";

    /** Credit card input validations */
    public void cardInputValidation(CardDetaisEntity creditCard) throws CreditCardException {

        logger.debug("Credit Card input validation ");
        if (!validationByLuhn(creditCard.getCardNumber())) {
            throw new CreditCardException(CreditCardException.INCORRECT_CARD_NUMBER);
        }

        logger.debug("Credit Card input validated successfully ");
    }

    /** Luhn check for Credit card number validity */
    private static boolean validationByLuhn(String cardNumber) {
        int sumOfCardNumber = 0;
        int[] cardNumberArray = new int[cardNumber.length()];
        for (int i = 0; i < cardNumber.length(); i++) {
            cardNumberArray[i] = Integer.parseInt(cardNumber.substring(i, i + 1));
        }
        for (int i = cardNumberArray.length - 2; i >= 0; i = i - 2) {
            int evenDigits = cardNumberArray[i];
            evenDigits = evenDigits * 2;
            if (evenDigits > 9) {
                evenDigits = evenDigits % 10 + 1;
            }
            cardNumberArray[i] = evenDigits;
        }
        for (int i = 0; i < cardNumberArray.length; i++) {
            sumOfCardNumber += cardNumberArray[i];
        }

        if (sumOfCardNumber % 10 == 0) {
            logger.info(" Valid credit card number -" + cardNumber);
            return true;
        } else {
            logger.error(" Invalid credit card number -" + cardNumber);
            return false;
        }
    }
}

