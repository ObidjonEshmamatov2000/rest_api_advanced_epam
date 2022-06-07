package com.epam.esm.dto.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 07/06/2022 - 10:11 AM
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppUserParams {
    private String name;
    private String email;
    private PaginationParams paginationParams;
}
