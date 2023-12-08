package com.example.clubsapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ResousrceNotFoundException extends RuntimeException{
    public ResousrceNotFoundException(String message) {
        super(message);
    }
}
