package com.example.tourmanagement.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourRequest {

    private Long id;

    @FutureOrPresent
    private LocalDateTime startDateTime;

    @FutureOrPresent
    private LocalDateTime endDateTime;

    @Min(value = 1)
    private Integer capacity;

    private Long detailsId;


}
