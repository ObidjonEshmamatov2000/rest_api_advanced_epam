package com.epam.esm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import static com.epam.esm.utils.RegexProvider.NAME_REGEX;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TagRequestDto {
    @NotBlank(message = "tag name cannot be empty or null")
    @NotEmpty(message = "A tag's name shouldn't be empty.")
    @Pattern(regexp = NAME_REGEX, message = "A tag's name should contain only alphabetic symbols, space or dash.")
    @Length(min = 2, max = 100, message = "The length of the tag's name should be between 2 and 100 symbols.")
    private String name;
}
