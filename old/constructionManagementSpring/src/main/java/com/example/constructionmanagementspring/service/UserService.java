package com.example.constructionmanagementspring.service;

import com.example.constructionmanagementspring.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAllUsers();

    User findById(Long id);

    User findByUsername(String username);

    User findByPrincipal(Principal principal);

    User putUser(User newUser, Long userId);

    User createUser(User userNotes);

    User editPhotoUser(MultipartFile image, Long userId) throws IOException;
}
