package com.example.aston.model;

import lombok.*;

@ToString
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Country {

    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
}
