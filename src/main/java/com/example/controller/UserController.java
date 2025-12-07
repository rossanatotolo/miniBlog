package com.example.controller;

import com.example.dto.UserDtoInput;
import com.example.dto.UserDtoOutput;
import com.example.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDtoOutput> getAllUsers(
            @RequestParam(required = false) final List<Long> ids,
            @RequestParam(defaultValue = "0") @PositiveOrZero final int page,
            @RequestParam(defaultValue = "10") @Positive final int size) {
        return userService.getAllUsers(ids, page, size);
    }

    @GetMapping("/{userId}")
    public UserDtoOutput getUserById(@PathVariable @Positive final long userId) {
        return userService.getUserById(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDtoOutput createUser(@RequestBody @Valid final UserDtoInput userDtoInput) {
        return userService.createUser(userDtoInput);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable final long userId) {
        userService.deleteUser(userId);
    }
}

