package com.epam.esm.dto.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 07/06/2022 - 10:09 AM
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TagParams {
    private String name;
    private Integer certificateId;
    private PaginationParams paginationParams;
}
