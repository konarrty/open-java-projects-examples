package com.example.kpimanagment.controller;

import com.example.kpimanagment.model.User;
import com.example.kpimanagment.service.PrivilegeService;
import com.example.kpimanagment.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/showList")
    public String getAllUser(Model model) {

        List<User> userList = userService.getAllUsers();

        model.addAttribute("users", userList);


        return "user/show";
    }

    @GetMapping("/add")
    public String showAddPage(Model model) {

        model.addAttribute("user", new User());

        return "user/add";
    }

    @GetMapping("/edit/{id}")
    public String showEditPage(Model model, @PathVariable Long id) {

        model.addAttribute("user", userService.getUserById(id));

        return "user/edit";
    }

    @GetMapping("/admin/edit/{id}")
    public String showAdminEditPage(Model model, @PathVariable Long id) {

        model.addAttribute("user", userService.getUserById(id));
//        model.addAttribute("privileges", privilegeService.getAllPrivilege());

        return "admin/edit";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute @Valid User user) {


        userService.createUser(user);

        return "redirect:showList";

    }

    @PutMapping("/edit/{id}")
    public String putUser(@ModelAttribute User user, @PathVariable Long id) {

        userService.putUser(user, id);

        return "redirect:../showList";
    }


    @PatchMapping("/edit/{id}")
    public String patchUser(@ModelAttribute User user, @PathVariable Long id) {

        userService.patchUser(user, id);


        return "redirect:/employees/showList";
    }

    @DeleteMapping("delete/{id}")
    public String deleteUser(@PathVariable Long id) {

        userService.deleteUserById(id);

        return "redirect:../showList";
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {

        return userService.getUserById(id);
    }
}
