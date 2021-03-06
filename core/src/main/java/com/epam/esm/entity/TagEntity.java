package com.epam.esm.entity;

import com.epam.esm.audit.AuditListener;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */
@EntityListeners(AuditListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tag")
public class TagEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(name = "create_date")
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createDate;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private Set<GiftCertificateEntity> certificates;

    public TagEntity(String name, LocalDateTime createDate, Set<GiftCertificateEntity> certificates) {
        this.name = name;
        this.createDate = createDate;
        this.certificates = certificates;
    }

    public TagEntity(long id, String name, LocalDateTime date) {
        this.id = id;
        this.name = name;
        this.createDate = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TagEntity tag = (TagEntity) o;
        return Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}
