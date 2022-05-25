package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "gift_certificate")
public class GiftCertificateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    @NotEmpty(message = "A gift certificate's name shouldn't be empty.")
    @Pattern(regexp = "\\w[\\w\\s-]+", message = "A gift certificate's name should contain only alphabetic symbols, space or dash.")
    @Length(min = 2, max = 100, message = "The length of the gift certificate's name should be between 2 and 100 symbols.")
    private String name;

    @NotEmpty(message = "Description shouldn't be empty.")
    @Pattern(regexp = "[^><].+", message = "Symbols '<' and '>' are forbidden.")
    @Length(min = 3, max = 300, message = "The length of the gift certificate description should be between 3 and 300 symbols.")
    private String description;

    @DecimalMin(value = "0.00", message = "A price shouldn't be negative.")
    @DecimalMax(value = "10000.00", message = "A price shouldn't be more than 10000.00.")
    private BigDecimal price;

    @Min(value = 1, message = "A duration shouldn't be less than 1 day.")
    @Max(value = 180, message = "A duration shouldn't be more than 180 days.")
    private int duration;

    @CreationTimestamp
    @Column(updatable = false, name = "create_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "last_update_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime lastUpdateDate;


    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
    })
    @JoinTable(name = "gift_certificate_tag",
            joinColumns = @JoinColumn(name = "gift_certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<TagEntity> tags;
}
