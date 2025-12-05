package com.example.service;

import com.example.dto.UserDtoInput;
import com.example.dto.UserDtoOutput;

import java.util.List;

public interface UserService {
    List<UserDtoOutput> getAllUsers(List<Long> ids, final int from, final int size);

    UserDtoOutput getUserById(long userId);

    UserDtoOutput createUser(final UserDtoInput userDtoInput);

    void deleteUser(final Long userId);
}
