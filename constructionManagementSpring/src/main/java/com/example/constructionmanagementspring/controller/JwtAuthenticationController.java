package com.example.constructionmanagementspring.controller;

import com.example.constructionmanagementspring.dto.jwtDto.JwtRequest;
import com.example.constructionmanagementspring.dto.jwtDto.JwtResponse;
import com.example.constructionmanagementspring.model.User;
import com.example.constructionmanagementspring.service.UserService;
import com.example.constructionmanagementspring.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("api/auth/")
@RequiredArgsConstructor
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final UserService userService;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final User user = userService
                .findByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(user);

        return ResponseEntity.ok(new JwtResponse(token, user.getRoles()));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
