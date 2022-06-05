package com.epam.esm.utils;

import com.epam.esm.exception.ApplicationNotValidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.epam.esm.utils.ParamsStringProvider.*;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */

public class PaginationProvider {
    private static final int DEFAULT_LIMIT = 25;
    private static final int DEFAULT_OFFSET = 1;

    private final ApplicationValidator validator;

    @Autowired
    public PaginationProvider(ApplicationValidator applicationValidator) {
        this.validator = applicationValidator;
    }

    public Map<String, Integer> getPaginationParam(Map<String, Object> params) {
        Map<String, Integer> paginationParams = new HashMap<>();
        Integer limit = (Integer) params.get(PAGE_SIZE);
        Integer offset = (Integer) params.get(PAGE_NUMBER);

        if (limit == null) {
            limit = DEFAULT_LIMIT;
        } else if (!validator.isNumberValid(limit)) {
            throw new ApplicationNotValidDataException(PAGE_SIZE_NOT_VALID_ERROR, limit);
        }
        if (offset == null) {
            offset = DEFAULT_OFFSET;
        } else if (!validator.isNumberValid(offset)) {
            throw new ApplicationNotValidDataException(PAGE_NUMBER_NOT_VALID_ERROR, offset);
        }

        paginationParams.put(LIMIT, limit);
        paginationParams.put(OFFSET, limit * (offset - 1));
        return paginationParams;
    }
}
