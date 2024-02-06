package com.example.tourmanagement.dto.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperatorRegistrationDTO {

    private String name;

    private String description;

    private String username;

    private String password;

    private String email;
}
