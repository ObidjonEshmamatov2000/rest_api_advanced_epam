package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@AllArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ApplicationNotValidDataException extends RuntimeException{
    private String message;
    private Object param;
}