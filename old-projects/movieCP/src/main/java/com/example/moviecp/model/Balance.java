package com.example.moviecp.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@ToString
@Entity
@Table(name = "balances")
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double total;

    @OneToOne(mappedBy = "balance")
    private User viewer;

    public Balance() {
        this.total = 0;
    }

    public Balance(double total) {
        this.total = total;
    }
}
