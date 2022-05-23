package com.epam.esm.exceptionHandler;

import com.epam.esm.exception.ApplicationDuplicateDataException;
import com.epam.esm.exception.ApplicationNotFoundException;
import com.epam.esm.exception.ApplicationNotValidDataException;
import com.epam.esm.exception.BaseException;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@EnableWebMvc
@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApplicationError baseExceptionHandler(BaseException e) {
        return new ApplicationError(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(ApplicationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApplicationError notFoundError(ApplicationNotFoundException e) {
        return new ApplicationError(HttpStatus.NO_CONTENT.value(), e.getMessage());
    }

    @ExceptionHandler(ApplicationNotValidDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApplicationError notValidDataError(ApplicationNotValidDataException e) {
        return new ApplicationError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(ApplicationDuplicateDataException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApplicationError duplicateDataError(ApplicationDuplicateDataException e) {
        return new ApplicationError(HttpStatus.CONFLICT.value(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApplicationError serverError(Exception e) {
        return new ApplicationError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getLocalizedMessage());
    }
}
