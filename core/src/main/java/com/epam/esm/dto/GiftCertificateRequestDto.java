package com.epam.esm.dto;

import com.epam.esm.entity.TagEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GiftCertificateRequestDto {
    @NotBlank(message = "name can't be null or blank")
    private String name;
    @NotBlank(message = "description can't be null or blank")
    private String description;
    @PositiveOrZero(message = "prise should be 0 or more")
    private BigDecimal price;
    @Positive(message = "duration should be more than 0")
    private Integer duration;
    private List<TagEntity> tagEntities;
}
