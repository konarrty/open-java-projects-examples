package com.example.tourmanagement.dto.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentRegistrationDTO {

    private double salary;

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private String email;

}
