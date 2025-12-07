package com.example.service;

import com.example.dto.PostDtoInput;
import com.example.dto.PostDtoOutput;
import com.example.dto.PostDtoUpdate;
import com.example.exception.NotFoundException;
import com.example.mapper.PostMapper;
import com.example.model.Post;
import com.example.model.User;
import com.example.repository.PostRepository;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceImplTest {
    private PostService postService;
    @Mock
    private PostMapper postMapper;
    @Mock
    private PostRepository postRepository;

    private PostDtoInput postDtoInput;
    private PostDtoOutput postDtoOutput;
    private PostDtoUpdate postDtoUpdate;

    private Post post1;
    private User user1;

    @BeforeEach
    public void setUp() {

        postService = new PostServiceImpl(postRepository, postMapper);

        user1 = new User();
        user1.setUsername("Name1");
        user1.setEmail("example1@yandex.ru");
        user1.setId(1L);

        postDtoInput = new PostDtoInput();
        postDtoInput.setTitle("Title1");
        postDtoInput.setContent("Content1");
        postDtoInput.setAuthorId(user1.getId());

        post1 = new Post();
        post1.setTitle("Title1");
        post1.setContent("Content1");
        post1.setAuthor(user1);
        post1.setId(1L);

        postDtoOutput = new PostDtoOutput();
        postDtoOutput.setTitle("Title1");
        postDtoOutput.setContent("Content1");
        postDtoOutput.setAuthorId(user1.getId());
        postDtoOutput.setId(1L);

        postDtoUpdate = new PostDtoUpdate();
        postDtoUpdate.setTitle("UpdateTitle");
        postDtoUpdate.setContent("UpdateContent");
    }

    @Test
    @DisplayName("PostService_createPost")
    void testCreatePost() {

        when(postMapper.toPost(postDtoInput)).thenReturn(post1);
        when(postRepository.save(post1)).thenReturn(post1);
        when(postMapper.toPostDto(post1)).thenReturn(postDtoOutput);

        final PostDtoOutput result = postService.createPost(postDtoInput);

        assertEquals("Title1", result.getTitle());
        assertEquals("Content1", result.getContent());
        assertEquals(user1.getId(), result.getAuthorId());
        assertEquals(1L, result.getId());

        verify(postMapper).toPost(postDtoInput);
        verify(postRepository).save(post1);
        verify(postMapper).toPostDto(post1);
    }

    @Test
    @DisplayName("PostService_getPostById")
    void testGetPostById() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post1));
        when(postMapper.toPostDto(post1)).thenReturn(postDtoOutput);

        final PostDtoOutput result = postService.getPostById(1L);

        assertEquals("Title1", result.getTitle());
        assertEquals("Content1", result.getContent());
        assertEquals(user1.getId(), result.getAuthorId());

        verify(postRepository).findById(1L);
        verify(postMapper).toPostDto(post1);
    }

    @Test
    @DisplayName("PostService_getPostByNotId")
    void testGetPostByNotId() {

        assertThrows(
                NotFoundException.class,
                () -> postService.getPostById(3L)
        );
    }

    @Test
    @DisplayName("PostService_getAllPosts")
    void testGetAllPosts() {

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        List<Post> postList = List.of(post1);

        when(postRepository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(postList));
        when(postMapper.toListDto(postList)).thenReturn(List.of(postDtoOutput));

        List<PostDtoOutput> posts = postService.getAllPosts(null, 0, 10);

        assertEquals(1, posts.size());
        assertEquals("Title1", posts.get(0).getTitle());

        verify(postRepository).findAll(pageRequest);
        verify(postMapper).toListDto(postList);
    }

    @Test
    @DisplayName("PostService_updatePost")
    void testUpdatePost() {

        Post updatedPost = new Post();
        updatedPost.setId(1L);
        updatedPost.setTitle("UpdateTitle");
        updatedPost.setContent("UpdateContent");
        updatedPost.setAuthor(user1);

        PostDtoOutput expectedDto = new PostDtoOutput();
        expectedDto.setId(1L);
        expectedDto.setTitle("UpdateTitle");
        expectedDto.setContent("UpdateContent");
        expectedDto.setAuthorId(user1.getId());

        when(postRepository.findById(1L))
                .thenReturn(Optional.of(post1));
        when(postRepository.save(any(Post.class)))
                .thenReturn(updatedPost);
        when(postMapper.toPostDto(updatedPost))
                .thenReturn(expectedDto);

        PostDtoOutput result = postService.updatePost(1L, postDtoUpdate);

        assertNotNull(result);
        assertEquals("UpdateTitle", result.getTitle());
        assertEquals("UpdateContent", result.getContent());
        assertEquals(user1.getId(), result.getAuthorId());
        assertEquals(1L, result.getId());

        verify(postRepository).findById(1L);
        verify(postRepository).save(any(Post.class));
        verify(postMapper).toPostDto(updatedPost);
    }

    @Test
    @DisplayName("PostService_deletePostByNotId")
    void testDeletePostByNotId() {

        assertThrows(
                NotFoundException.class,
                () -> postService.deletePost(3L)
        );

        verify(postRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("PostService_deletePost")
    void testDeletePost() {

        when(postRepository.existsById(1L)).thenReturn(true);

        postService.deletePost(1L);

        verify(postRepository).existsById(1L);
        verify(postRepository).deleteById(1L);
    }

}