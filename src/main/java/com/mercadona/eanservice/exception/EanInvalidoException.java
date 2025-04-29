package com.mercadona.eanservice.exception;

public class EanInvalidoException extends RuntimeException {
    public EanInvalidoException(String message) {
        super(message);
    }
}