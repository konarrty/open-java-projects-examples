package com.example.moviecp.controller;

import com.example.moviecp.dto.FilmDto;
import com.example.moviecp.model.Film;
import com.example.moviecp.model.MovieReview;
import com.example.moviecp.model.Ticket;
import com.example.moviecp.service.FilmService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/films")
@CrossOrigin("*")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    private final ObjectMapper objectMapper;

    @GetMapping("/")
    public ResponseEntity<List<FilmDto>> getAllFilms(@RequestParam(required = false, defaultValue = "ALL") String genre) {

        return ResponseEntity.ok(filmService.getAllFilms(genre));
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Film> createFilm(@RequestParam("film") String jsonFilm, @RequestParam("image") MultipartFile image) throws IOException {
        Film film = objectMapper.readValue(jsonFilm, Film.class);

        return ResponseEntity.ok(filmService.createFilm(film, image));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "")
    public ResponseEntity<Film> addFilmWithoutImage(@RequestBody Film film) {

        return ResponseEntity.ok(filmService.addFilmWithoutImage(film));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Film> putFilm(@RequestBody Film film, @PathVariable Long id) {

        return ResponseEntity.ok(filmService.putFilm(film, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable Long id) {

        filmService.deleteFilm(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{id}/tickets")
    public ResponseEntity<List<Ticket>> getTicketsForFilm(@PathVariable Long id) {

        return ResponseEntity.ok(filmService.getTicketsForFilm(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable Long id) {

        return ResponseEntity.ok(filmService.getFilmById(id));
    }

    @PreAuthorize("hasAnyRole('VIEWER')")
    @GetMapping("/{id}/rating")
    public ResponseEntity<?> getFilmRating(@PathVariable Long id) {

        Double rating = filmService.calculateRating(id);

        if (rating != null)
            return ResponseEntity.ok(rating);
        else
            return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/reviews")
    public Iterable<MovieReview> getFilmReviews(@PathVariable Long id) {

        return filmService.getFilmReviews(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/pop")
    public List<FilmDto> getPopularFilms(@RequestParam(required = false, defaultValue = "") String genre) {

        return filmService.getPopularFilms(genre);
    }


}
