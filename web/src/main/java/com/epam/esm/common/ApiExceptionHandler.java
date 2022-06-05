package com.epam.esm.common;

import com.epam.esm.exception.ApplicationDuplicateDataException;
import com.epam.esm.exception.ApplicationNotFoundException;
import com.epam.esm.exception.ApplicationNotValidDataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.epam.esm.utils.ParamsStringProvider.*;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */

@EnableWebMvc
@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

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
        log.error("ERROR: -> ", e);
        return new BaseResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),  e.getMessage(), e);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        return ResponseEntity
                .status(400)
                .body(new BaseResponse(400, JSON_PARSE_ERROR));
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        return ResponseEntity
                .status(400)
                .body(new BaseResponse(status.value(), PARAM_TYPE_MISMATCH_ERROR));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        return ResponseEntity
                .status(400)
                .body(new BaseResponse(status.value(), SERVLET_NOT_COMPLETE_ERROR));
    }
}
