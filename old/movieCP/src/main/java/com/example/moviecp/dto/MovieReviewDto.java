package com.example.moviecp.dto;

import com.example.moviecp.model.Film;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.logging.log4j.message.StringFormattedMessage;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieReviewDto {

    private String review;

    private int rating;

    private Film film;
}
