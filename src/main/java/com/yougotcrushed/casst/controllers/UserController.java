package com.yougotcrushed.casst.controllers;

import com.yougotcrushed.casst.dtos.*;
import com.yougotcrushed.casst.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public Iterable<UserDto> getAllUsers(@RequestParam(required = false, defaultValue = "", name = "sort") String sortBy) {
        return userService.getAllUsers(sortBy);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserRequest request, UriComponentsBuilder uriBuilder) {
        return userService.registerUser(request,  uriBuilder);

    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable(name = "id") Long id, @RequestBody UpdateUserRequest request) {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequest request) {
        return userService.changePassword(id, request);
    }

}
