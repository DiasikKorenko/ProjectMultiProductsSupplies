package com.exampl.exception;

public class SomeCustomException extends RuntimeException {

    public SomeCustomException(String cardNumber) {
        super("Карта с  таким номером  уже существует");
    }
}
