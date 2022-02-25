package com.card.application.exception;


/** Card input related exception details mapping */
public class CreditCardException extends Exception {


    public static final String INCORRECT_CARD_NUMBER = "Luhn check failed- Invalid credit card number";
    public static final String DUPLICATE_CARD_NUMBER = "Credit card number already exist";
    public static final String INVALID_DATA_IN_REQUEST = "Card deatils are invalid";
    public static final String ERROR_RETRIEVING_CARD_DETAILS= "Error while retrieving card deatils";


    public CreditCardException(final String message) {
        super(message);
    }



}
