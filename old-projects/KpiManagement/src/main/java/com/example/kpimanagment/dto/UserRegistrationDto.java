package com.example.kpimanagment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {

    @NotBlank(message = "\nНеобходимо заполнить поле 'Логин'")
    private String username;

    @NotBlank(message = "\nНеобходимо заполнить поле 'Пароль'")
    private String password;

    @NotBlank(message = "\nНеобходимо заполнить поле 'Пригласительный код'")
    private String invitationCode;
}
