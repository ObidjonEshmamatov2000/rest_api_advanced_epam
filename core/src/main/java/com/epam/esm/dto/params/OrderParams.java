package com.epam.esm.dto.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 07/06/2022 - 10:12 AM
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderParams {
    private Integer userId;
    private PaginationParams paginationParams;
}
