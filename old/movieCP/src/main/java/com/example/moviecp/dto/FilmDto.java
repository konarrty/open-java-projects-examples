package com.example.moviecp.dto;

import com.example.moviecp.model.Film;
import lombok.Data;

@Data
public class FilmDto {

    private Long id;

    private String name;

    private String ageRistrict;

    private double rating;

    private String description;

    private String filmName;

    private String genre;

    public FilmDto(Film film, double rating) {
        this.id = film.getId();
        this.description = film.getDescription();
        this.filmName = film.getFilmName();
        this.genre = film.getGenre();
        this.name = film.getName();
        this.ageRistrict = film.getAgeRistrict();
        this.rating = rating;
    }
}
