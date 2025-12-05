package com.example.mapper;

import com.example.dto.UserDtoInput;
import com.example.dto.UserDtoOutput;
import com.example.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    public static UserDtoOutput toUserDto(final User user) {
        final UserDtoOutput userDtoOutput = new UserDtoOutput();

        userDtoOutput.setId(user.getId());
        userDtoOutput.setUsername(user.getUsername());
        userDtoOutput.setEmail(user.getEmail());

        return userDtoOutput;
    }

    public static User toUser(final UserDtoInput userDtoInput) {
        final User user = new User();

        user.setUsername(userDtoInput.getUsername());
        user.setEmail(userDtoInput.getEmail());

        return user;
    }

    public static List<UserDtoOutput> toListDto(Iterable<User> users) {
        List<UserDtoOutput> result = new ArrayList<>();

        for (User user : users) {
            result.add(toUserDto(user));
        }
        return result;
    }
}
