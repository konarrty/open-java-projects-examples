package com.example.moviecp.controller;

import com.example.moviecp.model.Check;
import com.example.moviecp.model.Role;
import com.example.moviecp.model.User;
import com.example.moviecp.service.CheckService;
import com.example.moviecp.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/users/")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    private final CheckService checkService;


    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/me")
    public ResponseEntity<User> getMe(Principal principal) {

        return ResponseEntity.ok(userService.getMe(principal));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/sign-in")
    public List<String> signIn(Principal principal) {

        return userService.signIn(principal);
    }

    @PostMapping("/{userId}/credits")
    public ResponseEntity<User> addCreditsFromUser(@PathVariable Long userId, @RequestParam Double sum) {

        User user = userService.addCreditsFromUser(userId, sum);
        if (user != null)
            return ResponseEntity.ok(user);
        else
            return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> putFilm(@RequestBody User user, @PathVariable Long id) {

        return ResponseEntity.ok(userService.putUser(user, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        userService.deleteFilm(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/credits-off")
    public ResponseEntity<User> writeOfCreditsFromUser(@PathVariable Long userId, @RequestParam Double sum) {

        User user = userService.writeOffCreditsFromUser(userId, sum);
        if (user != null)
            return ResponseEntity.ok(user);
        else
            return ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{userId}/checks")
    public List<Check> getUsersChecks(@PathVariable Long userId) {

        return checkService.getUsersChecks(userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/my-checks")
    public List<Check> getUsersChecks(Principal principal) {

        return checkService.getUsersChecksByPrincipal(principal);
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody User user) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(user));
    }


}
