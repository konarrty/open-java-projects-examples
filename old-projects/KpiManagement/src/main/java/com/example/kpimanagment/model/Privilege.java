package com.example.kpimanagment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "privileges")
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ToString.Exclude
    @ManyToOne
    private Employee employee;

    @NotNull
    @ToString.Exclude
    @ManyToOne
    private User user;

    @PreRemove
    public void destroy() {
        this.user.getPrivileges().remove(this);

    }

    public Privilege(@NotNull Employee employee) {
        this.employee = employee;
    }
}