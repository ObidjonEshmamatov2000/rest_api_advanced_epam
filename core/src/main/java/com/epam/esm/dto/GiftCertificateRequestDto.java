package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Set;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GiftCertificateRequestDto {
    @Pattern(regexp = "\\w[\\w\\s-]+", message = "A gift certificate's name should contain only alphabetic symbols, space or dash.")
    @Length(min = 2, max = 100, message = "The length of the gift certificate's name should be between 2 and 100 symbols.")
    private String name;

    @Pattern(regexp = "[^><].+", message = "Symbols '<' and '>' are forbidden.")
    @Length(min = 3, max = 300, message = "The length of the gift certificate description should be between 3 and 300 symbols.")
    private String description;

    @DecimalMin(value = "0.00", message = "A price shouldn't be negative.")
    @DecimalMax(value = "10000.00", message = "A price shouldn't be more than 10000.00.")
    @PositiveOrZero(message = "prise should be 0 or more")
    private BigDecimal price;

    @Min(value = 1, message = "A duration shouldn't be less than 1 day.")
    @Max(value = 180, message = "A duration shouldn't be more than 180 days.")
    @Positive(message = "duration should be more than 0")
    private Integer duration;

    private Set<TagRequestDto> tags;
}
