package com.epam.esm.utils;

public class ApplicationValidatorImpl implements ApplicationValidator{
    @Override
    public boolean isNameValid(String name) {
        return name != null;
    }

    @Override
    public boolean isDescriptionValid(String description) {
        return description != null;
    }

    @Override
    public boolean isNumberValid(long number) {
        return number > 0;
    }

    @Override
    public boolean isDateValid(String date) {
        return date != null;
    }

    @Override
    public boolean isEmailValid(String email) {
        return email != null;
    }

    @Override
    public boolean isParamValid(String param) {
        return param != null;
    }
}
