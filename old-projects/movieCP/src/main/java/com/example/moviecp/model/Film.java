package com.example.moviecp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
@JsonIgnoreProperties(value = "rating")
@ToString
@Entity
@Table(name = "films")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String ageRistrict;

    private String description;

    private String genre;

    private String filmName;

    @JsonIgnore
    @OneToMany(mappedBy = "film",cascade = CascadeType.ALL)
    private List<Schedule> schedule;

    @JsonIgnore
    @OneToMany(mappedBy = "film",cascade = CascadeType.ALL)
    private List<MovieReview> movieReviews;

    public Film(String name, String ageRistrict, String description, String genre) {
        this.name = name;
        this.ageRistrict = ageRistrict;
        this.description = description;
        this.genre = genre;
    }
}
