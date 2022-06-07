package com.epam.esm.dto.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 07/06/2022 - 10:08 AM
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationParams {
    private int pageSize;
    private int pageNumber;
}
