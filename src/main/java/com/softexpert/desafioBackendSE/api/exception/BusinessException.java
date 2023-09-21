package com.softexpert.desafioBackendSE.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 2992237950757242618L;

    public BusinessException(String message) {
        super(message);
    }
}
