package com.example.kpimanagment.model;

import com.example.kpimanagment.validator.PlanFormatConstraint;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@PlanFormatConstraint
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"mark"})
@Entity
@Table(name = "targets")
public class Target {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 12, message = "Слишком большое знаечние в поле 'План'")
    @NotBlank(message = "Необходимо заполнить поле 'План'")
    @Column(name = "plan")
    private String plan;

    @Max(1)
    @DecimalMin(value = "0.01")
    @Column(name = "weight")
    private double weight;

    @NotNull
    @ManyToOne
    private Period period;

    @NotNull
    @ManyToOne
    private Employee employee;

    @ManyToOne
    private Employee curator;

    @NotNull
    @ManyToOne
    private Indicator indicator;

    @ToString.Exclude
    @OneToOne(mappedBy = "target", cascade = CascadeType.REMOVE)
    private Mark mark;

    public Target(Employee employee) {
        this.employee = employee;
    }

    public Target(Period period, Employee employee) {
        this.period = period;
        this.employee = employee;
    }

    public Target(Indicator indicator) {
        this.indicator = indicator;
    }

}
