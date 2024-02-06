package com.example.moviecp.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id","viewer","film"})
@ToString
@Entity
@Table(name = "movie_reviews")
public class MovieReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String review;

    private int rating;

    @ManyToOne
    private Film film;

    @ManyToOne
    private User viewer;

    public MovieReview(String review, int rating, Film film) {
        this.review = review;
        this.rating = rating;
        this.film = film;
    }
}
