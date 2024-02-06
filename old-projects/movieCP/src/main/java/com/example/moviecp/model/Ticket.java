package com.example.moviecp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
@ToString
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int place;

    private int line;

    private int coast;

    private boolean isFree = true;

    @OneToOne(cascade = CascadeType.MERGE)
    private Schedule schedule;

    public Ticket(int place, int line, int coast, Schedule schedule) {
        this.place = place;
        this.line = line;
        this.coast = coast;
        this.schedule = schedule;
    }
}
