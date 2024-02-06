package com.example.moviecp.dto;

import com.example.moviecp.model.Balance;
import lombok.Data;

@Data
public class UserDto {

    private Long id;

    private String username;

    private String email;

    private Balance balance;
}
