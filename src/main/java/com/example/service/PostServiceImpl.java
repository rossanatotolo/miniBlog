package com.example.service;

import com.example.dto.PostDtoInput;
import com.example.dto.PostDtoOutput;
import com.example.dto.PostDtoUpdate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class PostServiceImpl implements PostService {
    @Override
    @Transactional(readOnly = true)
    public List<PostDtoOutput> getAllPosts(List<Long> ids, int from, int size) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public PostDtoOutput getPostById(long postId) {
        return null;
    }

    @Override
    public PostDtoOutput createPost(PostDtoInput postDtoInput) {
        return null;
    }

    @Override
    public PostDtoOutput updatePost(long postId, PostDtoUpdate postDtoUpdate) {
        return null;
    }

    @Override
    public void deletePost(Long postId) {

    }
}
