package com.example.controller;

import com.example.MiniBlogApp;
import com.example.dto.PostDtoInput;
import com.example.dto.PostDtoOutput;
import com.example.service.PostService;
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
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Test
    @DirtiesContext
    @DisplayName("PostController_getAllPosts")
    void testGetAllPosts() throws Exception {

        final List<PostDtoOutput> posts = List.of(new PostDtoOutput());

        when(postService.getAllPosts(null, 0, 10)).thenReturn(posts);

        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(postService, times(1)).getAllPosts(null, 0, 10);
    }

    @Test
    @DirtiesContext
    @DisplayName("PostController_getPostById")
    void testGetPostById() throws Exception {

        final PostDtoOutput postDtoOutput = new PostDtoOutput();

        when(postService.getPostById(anyLong())).thenReturn(postDtoOutput);

        mockMvc.perform(get("/api/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(postDtoOutput.getId()));

        verify(postService, times(1)).getPostById(anyLong());
    }

    @Test
    @DirtiesContext
    @DisplayName("PostController_createPost")
    void testCreatePost() throws Exception {

        final PostDtoOutput postDtoOutput = new PostDtoOutput();

        when(postService.createPost(any(PostDtoInput.class))).thenReturn(postDtoOutput);

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"title1\", \"content\": \"content1\", \"authorId\": 1}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(postDtoOutput.getId()));

        verify(postService, times(1)).createPost(any(PostDtoInput.class));
    }

    @Test
    @DirtiesContext
    @DisplayName("PostController_deletePost")
    void testDeletePost() throws Exception {

        mockMvc.perform(delete("/api/posts/1"))
                .andExpect(status().isNoContent());

        verify(postService, times(1)).deletePost(anyLong());
    }
}