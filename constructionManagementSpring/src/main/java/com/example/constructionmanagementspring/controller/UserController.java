package com.example.constructionmanagementspring.controller;

import com.example.constructionmanagementspring.config.annotation.BodyToEntity;
import com.example.constructionmanagementspring.dto.UserDto;
import com.example.constructionmanagementspring.model.User;
import com.example.constructionmanagementspring.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("api/users/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public List<User> getAllUsers() {

        return userService.getAllUsers();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public User createUser(@Valid @RequestBody User user) {

        return userService.createUser(user);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/me")
    public ResponseEntity<User> createUser(Principal principal) {
        User user = userService.findByPrincipal(principal);

        if (user != null)
            return ResponseEntity.ok(user);
        else
            return ResponseEntity
                    .notFound()
                    .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{userId}")
    public User putUser(@Valid @BodyToEntity(UserDto.class) User user, @PathVariable Long userId) {

        return userService.putUser(user, userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/{userId}/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public User editPhotoUser(@RequestParam("image") MultipartFile image, @PathVariable Long userId) throws IOException {

        return userService.editPhotoUser(image, userId);
    }

}
