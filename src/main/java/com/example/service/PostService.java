package com.example.service;

import com.example.dto.PostDtoInput;
import com.example.dto.PostDtoOutput;
import com.example.dto.PostDtoUpdate;

import java.util.List;

public interface PostService {
    List<PostDtoOutput> getAllPosts(final List<Long> ids, final int from, final int size);

    PostDtoOutput getPostById(final long postId);

    PostDtoOutput createPost(final PostDtoInput postDtoInput);

    PostDtoOutput updatePost(final long postId, final PostDtoUpdate postDtoUpdate);

    void deletePost(final long postId);
}
