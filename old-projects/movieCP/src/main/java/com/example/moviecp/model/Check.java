package com.example.moviecp.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
@ToString
@Entity
@Table(name = "checks")
public class Check {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Ticket ticket;

    @OneToOne
    private User viewer;

    public Check(Ticket ticket, User viewer) {
        this.ticket = ticket;
        this.viewer = viewer;
    }
}
