package com.mercadona.eanservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mercadona.eanservice.dto.ErrorDTO;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EanInvalidoException.class)
    public ResponseEntity<ErrorDTO> handleProductoNotFound(EanInvalidoException ex) {
        return new ResponseEntity<>(new ErrorDTO(HttpStatus.NOT_FOUND.value(), ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handleIllegalArgument(IllegalArgumentException ex) {
        return new ResponseEntity<>(new ErrorDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGeneralException(Exception ex) {
        return new ResponseEntity<>(new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error interno del servidor"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
