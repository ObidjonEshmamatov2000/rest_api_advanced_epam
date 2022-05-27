package com.epam.esm.utils;

import org.apache.commons.lang3.math.NumberUtils;

public class ApplicationValidatorImpl implements ApplicationValidator{
    @Override
    public boolean isNameValid(String name) {
        return name!= null && name.trim().length() > 2 && !NumberUtils.isCreatable(name);
    }

    @Override
    public boolean isDescriptionValid(String description) {
        return description != null && description.trim().length() > 2;
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
        return email != null && !NumberUtils.isCreatable(email);
    }

    @Override
    public boolean isParamValid(String param) {
        return param != null;
    }
}
