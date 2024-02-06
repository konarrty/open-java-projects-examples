package com.example.constructionmanagementspring.service.impl;

import com.example.constructionmanagementspring.exception.NoSuchEntityException;
import com.example.constructionmanagementspring.model.User;
import com.example.constructionmanagementspring.repository.RoleRepository;
import com.example.constructionmanagementspring.repository.UserRepository;
import com.example.constructionmanagementspring.service.MediaService;
import com.example.constructionmanagementspring.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final MediaService mediaService;


    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, MediaService mediaService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.mediaService = mediaService;
    }

    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();

    }

    @Override
    public User createUser(User user) {
        user.setRoles(List.of(roleRepository.findByName("ROLE_PROVIDER")));
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);

    }

    @Override
    public User editPhotoUser(MultipartFile image, Long userId) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new NoSuchEntityException("Пользователь не найден!"));

        user.setPhotoName(mediaService.editUserPhoto(image));

        return userRepository.save(user);
    }


    @Override
    public User putUser(User newUser, Long userId) {

        User user = userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new NoSuchEntityException("Пользователь не найден!"));

        newUser.setId(user.getId());

        return userRepository.save(newUser);
    }

    @Override
    public User findByUsername(String username) {

        return userRepository.findByUsername(username).orElseThrow(() ->
                new NoSuchEntityException("Нет такой учетной записи!"));
    }

    @Override
    public User findByPrincipal(Principal principal) {
        if (principal == null)
            return null;
        else
            return userRepository.findByUsername(principal.getName())
                    .orElseThrow();
    }

    @Override
    public User findById(Long id) {

        return userRepository.findById(id).orElseThrow();
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new NoSuchEntityException("Нет такой учетной записи!"));
        ;

        if (user == null)
            throw new UsernameNotFoundException("Нет такого пользователя!");

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }


}
