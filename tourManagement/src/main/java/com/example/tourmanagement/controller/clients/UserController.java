package com.example.tourmanagement.controller.clients;

import com.example.tourmanagement.dto.UserDTO;
import com.example.tourmanagement.model.entity.User;
import com.example.tourmanagement.service.clients.UserService;
import com.example.tourmanagement.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("*")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService usersService;
    private final FileUtils fileUtils;

    @GetMapping
    public ResponseEntity<?> getAllUsers() {

        Iterable<UserDTO> usersList = usersService.getAllUsers();
        if (usersList.iterator().hasNext())
            return ResponseEntity.ok(usersList);
        else
            return ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserDTO createUsers(@RequestBody UserDTO userDTO) {

        return usersService.createUser(userDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteUsers(@PathVariable Long id) {

        usersService.deleteUserById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public UserDTO patchUsers(@RequestBody UserDTO userDTO, @PathVariable Long id) {

        return usersService.patchUsers(userDTO, id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}/image")
    public void uploadHotelImage(@RequestPart MultipartFile image, @PathVariable Long id) {

        fileUtils.saveImage(image, User.class.getSimpleName().toLowerCase(), id);
    }

    @PutMapping("/{id}/image")
    public void replaceHotelImage(@RequestPart MultipartFile image, @RequestPart String url, @PathVariable Long id) {

        fileUtils.deleteImage(id, User.class.getSimpleName().toLowerCase(), url);
        fileUtils.saveImage(image, User.class.getSimpleName().toLowerCase(), id);
    }
}
