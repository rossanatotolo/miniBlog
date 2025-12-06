package com.example.service;

import com.example.dto.PostDtoInput;
import com.example.dto.PostDtoOutput;
import com.example.dto.PostDtoUpdate;
import com.example.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

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
