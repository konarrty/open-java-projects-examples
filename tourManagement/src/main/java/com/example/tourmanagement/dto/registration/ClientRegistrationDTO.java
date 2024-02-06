package com.example.tourmanagement.dto.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRegistrationDTO {

    private String firstName;

    private String lastName;

    private String middleName;

    private String phoneNumber;

    private String socialHref;

    private String username;

    private String password;

    private String email;

}
