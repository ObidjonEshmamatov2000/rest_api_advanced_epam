package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "app_user")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class AppUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @NotEmpty(message = "The first name of the user shouldn't be empty.")
    @Pattern(regexp = "\\w[\\w\\s-]+", message = "The first name of the user should contain only alphabetic symbols, space or dash.")
    @Length(min = 2, max = 100, message = "The length of the user's first name should be between 2 and 100 symbols.")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "The last name of the user shouldn't be empty.")
    @Pattern(regexp = "\\w[\\w\\s-]+", message = "The last name of the user should contain only alphabetic symbols, space or dash.")
    @Length(min = 2, max = 100, message = "The length of the user's last name should be between 2 and 100 symbols.")
    private String lastName;

    @Column(name = "email", unique = true)
    @NotEmpty(message = "The email shouldn't be empty.")
    @Email(message = "Entered string has to be an email.")
    @Length(min = 10, max = 50, message = "The length of the email should be between 10 and 50 symbols.")
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonIgnore
    List<OrderEntity> orders;
}
