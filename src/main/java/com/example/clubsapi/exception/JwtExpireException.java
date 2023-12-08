package com.example.clubsapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
public class JwtExpireException extends RuntimeException{
    public JwtExpireException(String message) {
        super(message);
    }
}
