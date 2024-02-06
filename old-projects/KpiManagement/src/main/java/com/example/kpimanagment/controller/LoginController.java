package com.example.kpimanagment.controller;

import com.example.kpimanagment.enums.ERole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class LoginController {

    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated())
            return "login";
        else
            return "redirect:home";
    }

    @GetMapping("/home")
    public String home(Authentication authentication) {
        ERole role = (ERole) authentication.getAuthorities().stream().findFirst().get();
        if (role.equals(ERole.ROLE_USER) || role.equals(ERole.ROLE_CURATOR))
            return "redirect:/targets/my";
        else
            return "redirect:/employees/showList";

    }
    @GetMapping("/license")
    public String showLicense() {

        return "license";
    }

}
