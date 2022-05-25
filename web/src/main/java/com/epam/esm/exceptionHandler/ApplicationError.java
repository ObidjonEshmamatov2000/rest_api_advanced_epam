package com.epam.esm.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApplicationError{
    private int status;
    private String message;
    private Object param;

    public ApplicationError(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
