package com.example.moviecp.controller;

import com.example.moviecp.dto.MovieReviewDto;
import com.example.moviecp.model.MovieReview;
import com.example.moviecp.service.MovieReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/reviews")
@CrossOrigin("*")
public class MovieReviewController {

    @Autowired
    private MovieReviewService movieReviewService;

    @GetMapping("/")
    public ResponseEntity<List<MovieReview>> getAllMovieReviews() {

        return ResponseEntity.ok(movieReviewService.getAllMovieReviews());
    }

    @GetMapping("/my")
    public ResponseEntity<List<MovieReview>> getAllMovieReviews(Principal principal) {

        return ResponseEntity.ok(movieReviewService.getMyMovieReviews(principal));
    }


    @PostMapping("/")
    public ResponseEntity<MovieReview> createMovieReview(@RequestBody MovieReviewDto movieReview, Principal principal) {
        System.err.println(movieReview);
        System.err.println(principal);
        return ResponseEntity.ok(movieReviewService.createMovieReview(dtoToMovieReview(movieReview), principal));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieReview> putMovieReview(@RequestBody MovieReview movieReview, @PathVariable Long id) {

        return ResponseEntity.ok(movieReviewService.putMovieReview(movieReview, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovieReview(@PathVariable Long id) {

        movieReviewService.deleteMovieReview(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieReview> getMovieReviewById(@PathVariable Long id) {

        return ResponseEntity.ok(movieReviewService.getMovieReviewById(id));
    }

    public MovieReview dtoToMovieReview(MovieReviewDto movieReviewDto) {

        return new MovieReview(movieReviewDto.getReview(), movieReviewDto.getRating(), movieReviewDto.getFilm());
    }

}
