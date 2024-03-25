package com.example.aston.model;

import jakarta.persistence.Table;
import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@ToString
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "countries")
public class Country {

    @Id
    private Long id;

    private String name;

    private Double latitude;

    private Double longitude;
}
