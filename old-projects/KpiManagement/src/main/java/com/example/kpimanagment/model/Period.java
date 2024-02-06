package com.example.kpimanagment.model;

import com.example.kpimanagment.enums.EPeriodType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"targets"})
@Entity
@Table(name = "periods", uniqueConstraints =
@UniqueConstraint(columnNames = {"type", "year"}))
public class Period {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(2000)
    @Max(2099)
    @Column(name = "year", columnDefinition = "YEAR")
    private int year;

    @NotNull
    @Enumerated
    @Column(name = "type")
    private EPeriodType type;

    @JsonIgnore
    @OneToMany(mappedBy = "period", cascade = CascadeType.REMOVE)
    private List<Target> targets;

    public Period(int year, @NotNull EPeriodType type) {
        this.year = year;
        this.type = type;
    }

    public Period(int year, int month) {
        this.year = year;
        this.type = EPeriodType.valueOf("MONTH_" + month);
    }

    @Override
    public String toString() {
        if (!type.isYear())
            return type.typeName + " " + year + " года";
        else
            return year + " год";
    }

}
