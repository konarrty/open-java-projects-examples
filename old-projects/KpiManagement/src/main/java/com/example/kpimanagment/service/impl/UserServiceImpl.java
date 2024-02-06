package com.example.kpimanagment.service.impl;

import com.example.kpimanagment.enums.ERole;
import com.example.kpimanagment.exception.NoSuchEntityException;
import com.example.kpimanagment.exception.ServerLogicException;
import com.example.kpimanagment.model.Employee;
import com.example.kpimanagment.model.User;
import com.example.kpimanagment.permission.UserWithPrivileges;
import com.example.kpimanagment.repository.UserRepository;
import com.example.kpimanagment.service.EmployeeService;
import com.example.kpimanagment.service.UserService;
import com.example.kpimanagment.utils.ReflectionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Lazy)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final EmployeeService employeeService;

    private final PasswordEncoder passwordEncoder;
    private final ReflectionUtils reflectionUtils;

    @Value("${admin.invitationCode}")
    private String adminInvitationCode;

    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();

    }

    @Override
    public User getUserById(Long id) {

        return userRepository.findById(id).orElseThrow(() ->
                new NoSuchEntityException("Нет такой учетной записи!"));

    }

    @Override
    public User getUserByIdWithLocking(Long id) {

        return userRepository.findWithLockingById(id).orElseThrow(() ->
                new NoSuchEntityException("Нет такой учетной записи!"));

    }


    @Override
    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername()))
            throw new ServerLogicException("Пользователь !");

        user.setRole(ERole.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);

    }

    @Override
    public User putUser(User newUser, Long userId) {

        User user = findById(userId);

        newUser.setId(user.getId());

        return userRepository.save(newUser);
    }

    @Override
    public User patchUser(User newUser, Long id) {

        User user = getUserById(id);
        user = reflectionUtils.merge(user, newUser);

        return userRepository.save(user);
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
    public User findByPrincipal(Principal principal) {
        if (principal == null)
            return null;
        else
            return userRepository.findByUsername(principal.getName())
                    .orElseThrow();
    }

    @Override
    public User findById(Long id) {

        return userRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("Нет такого пользователя!"));
    }

    @Transactional
    @Override
    public User registrationUser(String login, String password, String invitationCode) {

        if (getAllUsers().isEmpty() && invitationCode.equals(adminInvitationCode)) {

            Employee employee = employeeService.createEmployee(
                    new Employee("Админ", "Админ",
                            new User(login, passwordEncoder.encode(password),
                                    adminInvitationCode, ERole.ROLE_ADMIN)));

            return employee.getUser();
        }

        User regUser = getAllUsers().stream().filter(user -> user.getInvitationCode().equals(invitationCode)).findFirst()
                .orElseThrow(() -> new ServerLogicException("Введенный пригласительный код недействителен!"));
        if (regUser.getUsername() != null)
            throw new ServerLogicException("Введенный пригласительный код уже активирован!");

        if (userRepository.existsByUsername(login))
            throw new ServerLogicException("Пользователь с таким логином уже существует!");

        regUser.setUsername(login);
        regUser.setPassword(passwordEncoder.encode(password));

        return regUser;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);

        if (user == null)
            throw new UsernameNotFoundException("Нет такого пользователя!");

        return new UserWithPrivileges(user.getUsername(), user.getPassword(),
                Collections.singleton(user.getRole()), user.getPrivileges());
    }

}
