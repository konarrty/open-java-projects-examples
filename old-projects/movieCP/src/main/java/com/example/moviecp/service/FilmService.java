package com.example.moviecp.service;

import com.example.moviecp.dto.FilmDto;
import com.example.moviecp.model.Film;
import com.example.moviecp.model.MovieReview;
import com.example.moviecp.model.Ticket;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FilmService {

    List<FilmDto> getAllFilms(String genre);

    List<Ticket> getTicketsForFilm(Long id);

    Film getFilmById(Long id);

    Film createFilm(Film film, MultipartFile image) throws IOException;

    Film addFilmWithoutImage(Film film);

    Film putFilm(Film film, Long filmId);

    void deleteFilm(Long id);

    Double calculateRating(Long filmId);

    Iterable<MovieReview> getFilmReviews(Long id);

    List<FilmDto> getPopularFilms(String genre);


}