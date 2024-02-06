package com.example.moviecp.service.impl;

import com.example.moviecp.dto.FilmDto;
import com.example.moviecp.model.Film;
import com.example.moviecp.model.MovieReview;
import com.example.moviecp.model.Ticket;
import com.example.moviecp.repository.FilmRepository;
import com.example.moviecp.service.FilmService;
import com.example.moviecp.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;

    private final TicketService ticketService;


    @Override
    public List<FilmDto> getAllFilms(String genre) {

        List<FilmDto> filmsDto;
        List<Film> films = filmRepository.findAll();

        filmsDto = films.stream().map(f -> new FilmDto(f, calculateRating(f.getId())))
                .collect(Collectors.toList());

        if (!genre.equals("ALL"))
            filmsDto = filmsDto.stream().filter(film ->
                            film.getGenre().equals(genre))
                    .collect(Collectors.toList());

        return filmsDto;
    }

    @Override
    public Iterable<MovieReview> getFilmReviews(Long id) {

        return filmRepository.findFilmById(id).getMovieReviews();
    }

    @Override
    public List<Ticket> getTicketsForFilm(Long id) {

        return ticketService.getAllTickets().stream()
                .filter(t -> t.getSchedule().getFilm().getId().equals(id)
                        && t.isFree())
                .collect(Collectors.toList());
    }

    @Override
    public Film getFilmById(Long id) {

        return filmRepository.findFilmById(id);
    }

    @Override
    public Film createFilm(Film film, MultipartFile image) {
        File file = new File("src/main/resources/static/images/films/" + image.getOriginalFilename());
        try {
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(file));
            stream.write(image.getBytes());
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        film.setFilmName(file.getName());

        return filmRepository.save(film);
    }

    @Override
    public Film addFilmWithoutImage(Film film) {

        return filmRepository.save(film);
    }

    @Override
    public Film putFilm(Film newFilm, Long filmId) {
        Film film = filmRepository.findFilmById(filmId);
        newFilm.setId(film.getId());

        return filmRepository.save(newFilm);
    }

    @Override
    public void deleteFilm(Long id) {

        filmRepository.deleteById(id);
    }

    @Override
    public Double calculateRating(Long filmId) {
        Film film = filmRepository.findById(filmId).orElseThrow();

        OptionalDouble rating = film.getMovieReviews().stream()
                .mapToDouble(MovieReview::getRating)
                .average();

        if (rating.isPresent())
            return rating.getAsDouble();
        else
            return null;
    }

    @Override
    public List<FilmDto> getPopularFilms(String genre) {

        return getAllFilms(genre).stream()
                .sorted(Comparator
                        .comparing(h -> (-1) *
                                calculateRating(h.getId())))
                .limit(5)
                .collect(Collectors.toList());
    }

}
