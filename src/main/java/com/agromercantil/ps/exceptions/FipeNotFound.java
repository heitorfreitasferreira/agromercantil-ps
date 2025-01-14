package com.agromercantil.ps.exceptions;

import org.springframework.http.HttpStatus;

public class FipeNotFound extends RuntimeException {
    final HttpStatus code = HttpStatus.NOT_FOUND;
    final String message = "Entity not found in Fipe";
    final String avaliable;

    public FipeNotFound(String message, String avaliable) {
        super(message);
        this.avaliable = avaliable;
    }


    @Override
    public String getMessage() {
        return message;
    }

    public String getAvaliable() {
        return avaliable;
    }
}
