package com.example.kpimanagment.controller;

import com.example.kpimanagment.dto.UserRegistrationDto;
import com.example.kpimanagment.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @GetMapping("/")
    public String showRegistrationCodePage() {

        return "registration";
    }

    @GetMapping("/invitationCode")
    public String showInvitationCodePage() {

        return "invitationCode";
    }


    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/")
    public void addUser(@Valid @ModelAttribute UserRegistrationDto user) {

        userService.registrationUser(user.getUsername(), user.getPassword(), user.getInvitationCode());

    }
}
