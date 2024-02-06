package com.example.moviecp.service;

import com.example.moviecp.model.MovieReview;

import java.security.Principal;
import java.util.List;

public interface MovieReviewService {
    List<MovieReview> getAllMovieReviews();

    MovieReview createMovieReview(MovieReview movieReview, Principal principal);

    MovieReview getMovieReviewById(Long id);

    MovieReview putMovieReview(MovieReview newMovieReview, Long movieReviewId);

    void deleteMovieReview(Long id);

    List<MovieReview>  getMyMovieReviews(Principal principal);
}
