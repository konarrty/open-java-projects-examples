package com.example.tourmanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String firstName;

    private String lastName;

    private String middleName;

    private String phoneNumber;

    private String passportNumber;

    private String socialHref;

    private UserDTO user;

    public ClientDTO(UserDTO user) {
        this.user = user;
    }
}
