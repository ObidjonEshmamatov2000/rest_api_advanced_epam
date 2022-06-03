package com.epam.esm.utils;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */

public class ApplicationValidatorImpl implements ApplicationValidator{
    private static final String EMAIL_REGEX = "[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+";
    private static final String NAME_REGEX = "[\\w_-]{2,18}";
    private static final String DESC_REGEX = "\\w[\\w\\s_.-]{3,50}";

    private Pattern pattern;
    private Matcher matcher;


    @Override
    public boolean isNameValid(String name) {
        if (name == null) return false;
        pattern = Pattern.compile(NAME_REGEX);
        matcher = pattern.matcher(name);
        return matcher.matches() && !NumberUtils.isCreatable(name);
    }

    @Override
    public boolean isDescriptionValid(String description) {
        if (description == null) return false;
        pattern = Pattern.compile(DESC_REGEX);
        matcher = pattern.matcher(description);
        return matcher.matches() && description.trim().length() > 2;
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
        if (email == null) return false;
        pattern = Pattern.compile(EMAIL_REGEX);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
