package com.example.tourmanagement.service.impl.clients;

import com.example.tourmanagement.dto.UserDTO;
import com.example.tourmanagement.mapper.clients.UserMapper;
import com.example.tourmanagement.model.entity.User;
import com.example.tourmanagement.repository.clients.UserRepository;
import com.example.tourmanagement.service.clients.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Iterable<UserDTO> getAllUsers() {

        Collection<User> users = userRepository.findAll();

        return userMapper.toDto(users);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        userRepository.save(user);

        return userMapper.toDto(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Нет такой учетной записи!"));
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }


    @Override
    public User findByUsername(String username) {

        return userRepository.findByUsername(username).orElse(null);
    }


    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);

        if (user == null)
            throw new UsernameNotFoundException("Нет такого пользователя!");

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                List.of(user.getRole()));
    }

    @Override
    public UserDTO patchUsers(UserDTO newUserDTO, Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException("Пользователь не найдено!"));

        userMapper.partialUpdate(user, newUserDTO);
        userRepository.save(user);

        return userMapper.toDto(user);
    }
}
