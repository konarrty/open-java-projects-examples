package com.example.moviecp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckStatDto {

    private String filmName;

    private long ticketAmount;
}
