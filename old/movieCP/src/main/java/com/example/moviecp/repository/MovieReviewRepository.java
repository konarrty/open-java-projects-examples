package com.example.moviecp.repository;

import com.example.moviecp.model.MovieReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieReviewRepository extends JpaRepository<MovieReview, Long> {
    MovieReview findMovieReviewById(Long id);

}
