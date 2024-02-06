package com.example.constructionmanagementspring.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class PassportDto {
    private Long id;

    private String firstName;

    private String secondName;

    private String lastName;

    private String age;

    private String address;

    private String city;

    private String country;
}
