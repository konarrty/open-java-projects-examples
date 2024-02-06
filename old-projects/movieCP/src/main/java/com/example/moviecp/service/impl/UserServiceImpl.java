package com.example.moviecp.service.impl;

import com.example.moviecp.model.Balance;
import com.example.moviecp.model.Role;
import com.example.moviecp.model.User;
import com.example.moviecp.repository.BalanceRepository;
import com.example.moviecp.repository.RoleRepository;
import com.example.moviecp.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BalanceRepository balanceRepository;


    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BalanceRepository balanceRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.balanceRepository = balanceRepository;
    }

    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    public User createUser(User user) {
        user.setRoles(List.of(roleRepository.findByName("ROLE_VIEWER")));
        user.setBalance(new Balance());
        user.setPassword(
                new BCryptPasswordEncoder().encode(
                        user.getPassword()
                )
        );

        return userRepository.save(user);

    }

    public List<String> signIn(Principal principal) {

        User user = userRepository.findByUsername(principal.getName()).orElseThrow();

        return user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());

    }

    public User putUser(User newUser, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty())
            return null;

        User user = optionalUser.get();
        newUser.setId(user.getId());
        newUser.setPassword(user.getPassword());

        return userRepository.save(newUser);

    }

    @Transactional
    public void deleteFilm(Long id) {

        userRepository.deleteById(id);
    }

    public User addCreditsFromUser(Long id, double sum) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = null;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();

            user.getBalance().setTotal(
                    user.getBalance().getTotal() + sum);

            userRepository.save(user);
        }
        return user;
    }

    public User writeOffCreditsFromUser(Long id, double sum) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = null;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();

            user.getBalance().setTotal(
                    user.getBalance().getTotal() - sum);

            userRepository.save(user);
        }
        return user;
    }

    public User findByUsername(String username) {

        return userRepository.findByUsername(username).orElseThrow();
    }

    public User findById(Long id) {

        return userRepository.findById(id).orElseThrow();
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow();

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }


    public User getMe(Principal principal) {


        return findByUsername(principal.getName());

    }
}
