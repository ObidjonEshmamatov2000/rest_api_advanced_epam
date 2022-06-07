package com.epam.esm.dto.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 07/06/2022 - 10:14 AM
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GiftCertificateParams {
    private String name;
    private String description;
    private String tagNames;
    private String  sortParams;
    private PaginationParams paginationParams;
}
