package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */

@Getter
@Setter
@AllArgsConstructor
@ResponseStatus(HttpStatus.CONFLICT)
public class ApplicationDuplicateDataException extends RuntimeException{
    private String message;
    private Object param;
}