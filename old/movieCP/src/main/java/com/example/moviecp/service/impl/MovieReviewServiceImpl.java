package com.example.moviecp.service.impl;

import com.example.moviecp.model.Film;
import com.example.moviecp.model.MovieReview;
import com.example.moviecp.model.User;
import com.example.moviecp.repository.MovieReviewRepository;
import com.example.moviecp.service.FilmService;
import com.example.moviecp.service.MovieReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieReviewServiceImpl implements MovieReviewService {

    private final MovieReviewRepository movieReviewRepository;

    private final FilmService filmService;

    private final UserServiceImpl userService;

    @Override
    public List<MovieReview> getAllMovieReviews() {

        return movieReviewRepository.findAll();
    }

    @Override
    public MovieReview getMovieReviewById(Long id) {

        return movieReviewRepository.findMovieReviewById(id);
    }

    @Override
    public List<MovieReview> getMyMovieReviews(Principal principal) {
        User user = userService.findByUsername(principal.getName());

        return getAllMovieReviews().stream()
                .filter(movieReview -> movieReview.getViewer().getId().equals(user.getId()))
                .collect(Collectors.toList());
    }


    @Override
    public MovieReview createMovieReview(MovieReview movieReview, Principal principal) {

        Film film = filmService.getFilmById(movieReview.getFilm().getId());
        User viewer = userService.findByUsername(principal.getName());
        movieReview.setViewer(viewer);
        movieReview.setFilm(film);

        return movieReviewRepository.save(movieReview);
    }

    @Override
    public MovieReview putMovieReview(MovieReview newMovieReview, Long movieReviewId) {
        MovieReview movieReview = movieReviewRepository.findMovieReviewById(movieReviewId);
        newMovieReview.setId(movieReview.getId());

        return movieReviewRepository.save(newMovieReview);
    }

    @Override
    public void deleteMovieReview(Long id) {

        movieReviewRepository.deleteById(id);
    }

}
