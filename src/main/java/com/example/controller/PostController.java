package com.example.controller;

import com.example.dto.PostDtoInput;
import com.example.dto.PostDtoOutput;
import com.example.dto.PostDtoUpdate;
import com.example.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostDtoOutput> getAllPosts(
            @RequestParam(required = false) final List<Long> ids,
            @RequestParam(defaultValue = "0") @PositiveOrZero final int page,
            @RequestParam(defaultValue = "10") @Positive final int size) {
        return postService.getAllPosts(ids, page, size);
    }

    @GetMapping("/{postId}")
    public PostDtoOutput getPostById(@PathVariable @Positive final long postId) {
        return postService.getPostById(postId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostDtoOutput createPost(@RequestBody @Valid final PostDtoInput postDtoInput) {
        return postService.createPost(postDtoInput);
    }

    @PatchMapping("/{postId}")
    public PostDtoOutput updatePost(@PathVariable @Positive final long postId, @RequestBody final PostDtoUpdate postDtoUpdate) {
        return postService.updatePost(postId, postDtoUpdate);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable final Long postId) {
        postService.deletePost(postId);
    }
}


