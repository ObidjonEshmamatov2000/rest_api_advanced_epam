package com.epam.esm.exceptionHandler;

import com.epam.esm.exception.ApplicationDuplicateDataException;
import com.epam.esm.exception.ApplicationNotFoundException;
import com.epam.esm.exception.ApplicationNotValidDataException;
import com.epam.esm.exception.BaseException;
import com.epam.esm.util.BaseResponse;
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
    public BaseResponse baseExceptionHandler(BaseException e) {
        return new BaseResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(ApplicationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseResponse notFoundError(ApplicationNotFoundException e) {
        return new BaseResponse(HttpStatus.NO_CONTENT.value(), e.getMessage(), e.getParam());
    }

    @ExceptionHandler(ApplicationNotValidDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse notValidDataError(ApplicationNotValidDataException e) {
        return new BaseResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), e.getParam());
    }

    @ExceptionHandler(ApplicationDuplicateDataException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public BaseResponse duplicateDataError(ApplicationDuplicateDataException e) {
        return new BaseResponse(HttpStatus.CONFLICT.value(), e.getMessage(), e.getParam());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse serverError(Exception e) {
        return new BaseResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getLocalizedMessage());
    }
}
