package com.epam.esm.utils;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */
public final class ParamsStringProvider {
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String PRICE = "price";
    public static final String DURATION = "duration";
    public static final String CREATE_DATE = "create_date";
    public static final String LAST_UPDATE_DATE = "last_update_date";
    public static final String EMAIL = "email";
    public static final String USER_ID = "userId";

    public static final String PAGE_SIZE = "pageSize";
    public static final String PAGE_NUMBER = "pageNumber";
    public static final String CERTIFICATE_ID = "giftCertificateId";
    public static final String TAG_NAMES = "tagNames";
    public static final String SORT_PARAMS = "sortParams";
    public static final String LIMIT = "limit";
    public static final String OFFSET = "offset";
    public static final String ASC = " asc";
    public static final String DESC = " desc";

    public static final String SUCCESS_MESSAGE = "success";
    public static final String ID_NOT_VALID = "id is not valid";
    public static final String NAME_NOT_VALID = "name is not valid";
    public static final String GIFT_CERTIFICATE_ALREADY_EXIST = "gift certificate is already exist";
    public static final String GIFT_CERTIFICATE_NOT_FOUND = "gift certificate not found";
    public static final String CERTIFICATE_NAME_OR_DESC_NOT_ACCEPTABLE = "gift certificate name or description not acceptable";

    public static final String USER_NOT_FOUND = "user not found with given id";

    public static final String NO_CERTIFICATE_SELECTED_TO_ORDER = "no gift certificate selected, please select at least one";
    public static final String ORDER_NOT_FOUND = "order not found";

    public static final String TAG_ALREADY_EXIST = "tag already exist with this name";
    public static final String TAG_NOT_FOUND = "tag not found";

    public static final String PAGE_NUMBER_NOT_VALID_ERROR = "page number param is not valid";
    public static final String PAGE_SIZE_NOT_VALID_ERROR = "page size param is not valid";

    public static final String JSON_PARSE_ERROR = "json parse error, params has not been entered correctly";
    public static final String PARAM_TYPE_MISMATCH_ERROR = "json parse error, params does not match";
    public static final String SERVLET_NOT_COMPLETE_ERROR = "some params is not provided";

}
