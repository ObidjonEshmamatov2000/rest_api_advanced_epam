package com.epam.esm.utils;

public interface ApplicationValidator {
    boolean isNameValid(String name);
    boolean isDescriptionValid(String description);
    boolean isNumberValid(long number);
    boolean isDateValid(String date);
    boolean isEmailValid(String email);
    boolean isParamValid(String param);
}
