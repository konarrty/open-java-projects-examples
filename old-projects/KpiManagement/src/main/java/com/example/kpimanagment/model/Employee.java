package com.example.kpimanagment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id", "targets", "targetsCurator", "privileges"})
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Необходимо заполнить поле 'ФИО'")
    @Column(name = "full_name", unique = true)
    private String fullName;

    @NotBlank(message = "Необходимо заполнить поле 'Должность'")
    @Column(name = "position")
    private String position;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE)
    private Set<Target> targets;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "curator", cascade = CascadeType.REMOVE)
    private Set<Target> targetsCurator;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE)
    private List<Privilege> privileges;

    public Employee(String fullName) {
        this.fullName = fullName;
    }

    public Employee(Long id) {
        this.id = id;
    }

    public Employee(String fullName, String position, @NotNull User user) {
        this.fullName = fullName;
        this.position = position;
        this.user = user;
    }

    @PreRemove
    public void preRemove() {
//        user.setEmployee(null);

    }
}
