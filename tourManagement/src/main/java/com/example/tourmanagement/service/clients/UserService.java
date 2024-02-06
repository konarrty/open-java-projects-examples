package com.example.tourmanagement.service.clients;

import com.example.tourmanagement.dto.UserDTO;
import com.example.tourmanagement.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    Iterable<UserDTO> getAllUsers();

    UserDTO createUser(UserDTO user);

    User getUserById(Long id);

    void deleteUserById(Long id);

    User findByUsername(String username);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    UserDTO patchUsers(UserDTO userDTO, Long id);
}
