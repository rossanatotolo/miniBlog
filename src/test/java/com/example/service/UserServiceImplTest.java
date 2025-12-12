package com.example.service;

import com.example.dto.UserDtoInput;
import com.example.dto.UserDtoOutput;
import com.example.exception.NotFoundException;
import com.example.model.User;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private UserDtoInput userDtoInput;

    private User user1;

    @BeforeEach
    public void setUp() {

        userService = new UserServiceImpl(userRepository);

        userDtoInput = new UserDtoInput();
        userDtoInput.setUsername("Name1");
        userDtoInput.setEmail("example1@mail.ru");

        user1 = new User();
        user1.setUsername("Name1");
        user1.setEmail("example1@yandex.ru");
        user1.setId(1L);
    }

    @Test
    @DisplayName("UserService_createUser")
    void testCreateUser() {

        when(userRepository.save(any(User.class))).thenReturn(user1);

        final UserDtoOutput userDto = userService.createUser(userDtoInput);

        assertEquals("Name1", userDto.getUsername());
        assertEquals(1L, userDto.getId());
    }

    @Test
    @DisplayName("UserService_getUserById")
    void testGetUserById() {

        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        assertEquals("Name1", userService.getUserById(1L).getUsername());
        verify(userRepository).findById(1L);
    }

    @Test
    @DisplayName("UserService_getUserByNotId")
    void testGetUserByNotId() {

        assertThrows(
                NotFoundException.class,
                () -> userService.getUserById(3L)
        );
    }

    @Test
    @DisplayName("UserService_getAllUsers")
    void testGetAllUsers() {

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("createdAt").descending());

        when(userRepository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(List.of(user1)));

        List<UserDtoOutput> users = userService.getAllUsers(null, 0, 10);

        assertEquals(1, users.size());
        assertEquals("Name1", users.get(0).getUsername());
        verify(userRepository).findAll(pageRequest);
    }

    @Test
    @DisplayName("UserService_deleteUserByNotId")
    void testDeleteUserByNotId() {

        assertThrows(
                NotFoundException.class,
                () -> userService.deleteUser(3L)
        );

        verify(userRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("UserService_deleteUser")
    void testDeleteUser() {

        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository).existsById(1L);
        verify(userRepository).deleteById(1L);
    }
}