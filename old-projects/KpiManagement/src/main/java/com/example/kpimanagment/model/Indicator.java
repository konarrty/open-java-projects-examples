package com.example.kpimanagment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "targets")
@Entity
@Table(name = "indicators")
public class Indicator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 150, message = "В поле 'Название показателя' может быть не более 150 символов")
    @NotBlank(message = "Необходимо заполнить поле 'Название показателя'")
    @Column(name = "name", unique = true)
    private String name;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "indicator")
    private List<Target> targets;

    public Indicator(Long id) {
        this.id = id;
    }

    public Indicator(String name) {
        this.name = name;
    }

    public Indicator(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
