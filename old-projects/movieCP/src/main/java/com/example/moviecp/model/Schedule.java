package com.example.moviecp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@Table(name = "schedules")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDateAndTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDateAndTime;

    @ManyToOne
    private Film film;

    @JsonIgnore
    @OneToOne(mappedBy = "schedule")
    private Ticket ticket;

    public Schedule(LocalDateTime startDateAndTime, LocalDateTime endDateAndTime, Film film) {
        this.startDateAndTime = startDateAndTime;
        this.endDateAndTime = endDateAndTime;
        this.film = film;
    }
}
