package com.epam.esm.utils;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */
public interface ApplicationValidator {
    boolean isNameValid(String name);

    boolean isDescriptionValid(String description);

    boolean isNumberValid(long number);

    boolean isDateValid(String date);

    boolean isEmailValid(String email);
}
