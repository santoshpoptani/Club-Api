package com.example.clubsapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class AuthorizatoinException extends RuntimeException{
    public AuthorizatoinException(String message) {
        super(message);
    }
}
