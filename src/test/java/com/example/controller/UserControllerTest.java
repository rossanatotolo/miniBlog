package com.example.controller;

import com.example.MiniBlogApp;
import com.example.dto.UserDtoInput;
import com.example.dto.UserDtoOutput;
import com.example.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MiniBlogApp.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @DirtiesContext
    @DisplayName("UserController_getAllUsers")
    void testGetAllUsers() throws Exception {

        final List<UserDtoOutput> users = List.of(new UserDtoOutput());

        when(userService.getAllUsers(null, 0, 10)).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(userService, times(1)).getAllUsers(null, 0, 10);
    }

    @Test
    @DirtiesContext
    @DisplayName("UserController_getUserById")
    void testGetUserById() throws Exception {

        final UserDtoOutput userDtoOutput = new UserDtoOutput();

        when(userService.getUserById(anyLong())).thenReturn(userDtoOutput);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDtoOutput.getId()));

        verify(userService, times(1)).getUserById(anyLong());
    }

    @Test
    @DirtiesContext
    @DisplayName("UserController_createUser")
    void testCreateUser() throws Exception {

        final UserDtoOutput userDtoOutput = new UserDtoOutput();

        when(userService.createUser(any(UserDtoInput.class))).thenReturn(userDtoOutput);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"username1\", \"email\": \"ex@mail.ru\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(userDtoOutput.getId()));

        verify(userService, times(1)).createUser(any(UserDtoInput.class));
    }

    @Test
    @DirtiesContext
    @DisplayName("UserController_deleteUser")
    void testDeleteUser() throws Exception {

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(anyLong());
    }
}