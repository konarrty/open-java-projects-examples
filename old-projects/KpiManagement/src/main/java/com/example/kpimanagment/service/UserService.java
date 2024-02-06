package com.example.kpimanagment.service;

import com.example.kpimanagment.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface UserService extends UserDetailsService {
    List<User> getAllUsers();

    User findById(Long id);

    User patchUser(User newUser, Long id);

    void deleteUserById(Long id);

    User findByUsername(String username);

    User findByPrincipal(Principal principal);

    User putUser(User newUser, Long userId);

    User getUserById(Long id);

    User getUserByIdWithLocking(Long id);

    User createUser(User userNotes);

    User registrationUser(String login, String password, String invitationCode);
}
