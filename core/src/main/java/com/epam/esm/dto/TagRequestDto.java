package com.epam.esm.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TagRequestDto {
    @NotBlank(message = "tag name cannot be empty or null")
    @NotEmpty(message = "A tag's name shouldn't be empty.")
    @Pattern(regexp = "\\w[\\w\\s-]+", message = "A tag's name should contain only alphabetic symbols, space or dash.")
    @Length(min = 2, max = 100, message = "The length of the tag's name should be between 2 and 100 symbols.")
    private String name;
}
