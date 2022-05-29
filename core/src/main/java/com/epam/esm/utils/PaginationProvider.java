package com.epam.esm.utils;

import com.epam.esm.exception.ApplicationNotValidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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
        Integer limit = (Integer) params.get("limit");
        Integer offset = (Integer) params.get("offset");

        if (limit == null) {
            limit = DEFAULT_LIMIT;
        } else if (!validator.isNumberValid(limit)) {
            throw new ApplicationNotValidDataException("the limit param is not valid", limit);
        }
        if (offset == null) {
            offset = DEFAULT_OFFSET;
        } else if (!validator.isNumberValid(offset)) {
            throw new ApplicationNotValidDataException("the offset param is not valid", offset);
        }

        paginationParams.put("limit", limit);
        paginationParams.put("offset", limit * (offset - 1));
        return paginationParams;
    }
}
