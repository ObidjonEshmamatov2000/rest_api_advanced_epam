package com.epam.esm.utils;

import com.epam.esm.dto.params.PaginationParams;
import com.epam.esm.exception.ApplicationNotValidDataException;
import org.springframework.beans.factory.annotation.Autowired;

import static com.epam.esm.utils.ParamsStringProvider.PAGE_NUMBER_NOT_VALID_ERROR;
import static com.epam.esm.utils.ParamsStringProvider.PAGE_SIZE_NOT_VALID_ERROR;

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

    public PaginationParams getPaginationParams(PaginationParams paginationParams) {
        Integer limit = paginationParams.getPageSize();
        Integer offset = paginationParams.getPageNumber();

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

        offset = limit * (offset - 1);

        paginationParams.setPageSize(limit);
        paginationParams.setPageNumber(offset);
        return paginationParams;
    }
}
