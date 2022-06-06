package com.epam.esm.utils;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 06/06/2022 - 5:26 PM
 */
public final class RegexProvider {
    public static final String EMAIL_REGEX = "[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+";
    public static final String NAME_REGEX = "\\w[\\w\\s_-]{2,100}";
    public static final String DESCRIPTION_REGEX = "\\w[\\w\\s_.-]{3,300}";
}
