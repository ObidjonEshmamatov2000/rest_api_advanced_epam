package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */

@Getter
@Setter
@AllArgsConstructor
public class BaseException extends RuntimeException{
    private String message;
    private Object param;
}