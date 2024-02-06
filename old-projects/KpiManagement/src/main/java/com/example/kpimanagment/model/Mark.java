package com.example.kpimanagment.model;

import com.example.kpimanagment.validator.PlanAndActualFormatConstraint;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@PlanAndActualFormatConstraint
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Entity
@Table(name = "marks")
public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Необходимо заполнить поле 'Факт'")
    @Column(name = "actual")
    private String actual;

    @Column(name = "note", columnDefinition = "LONGTEXT")
    private String note;

    @NotNull
    @OneToOne
    private Target target;

    public Mark(Target target) {
        this.target = target;
    }

    @PreRemove
    private void destroy() {
        this.target.setMark(null);
    }
}
