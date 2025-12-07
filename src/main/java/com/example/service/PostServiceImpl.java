package com.example.service;

import com.example.dto.PostDtoInput;
import com.example.dto.PostDtoOutput;
import com.example.dto.PostDtoUpdate;
import com.example.exception.NotFoundException;
import com.example.mapper.PostMapper;
import com.example.model.Post;
import com.example.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Override
    @Transactional(readOnly = true)
    public List<PostDtoOutput> getAllPosts(List<Long> ids, final int page, final int size) {
        PageRequest pageRequest = PageRequest.of(page, Math.min(size, 50), Sort.by(Sort.Direction.DESC, "createdAt"));
        log.info("Запрос на получение списка постов.");
        final List<Post> posts;

        if (Objects.isNull(ids) || ids.isEmpty()) {
            posts = postRepository.findAll(pageRequest).getContent();
            log.info("Получен список всех постов.");
        } else {
            posts = postRepository.findByIdIn(ids, pageRequest);
            log.info("Получен список постов по заданным id.");
        }

        return postMapper.toListDto(posts);
    }

    @Override
    @Transactional(readOnly = true)
    public PostDtoOutput getPostById(final long postId) {
        final Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(String.format("Пост с id = %d не найден.", postId)));

        log.info("Получен пост c id = {}.", postId);
        return postMapper.toPostDto(post);
    }

    @Override
    public PostDtoOutput createPost(final PostDtoInput postDtoInput) {
        final Post post = postRepository.save(postMapper.toPost(postDtoInput));

        log.info("Пост с id = {} добавлен.", post.getId());
        return postMapper.toPostDto(post);
    }

    @Override
    public PostDtoOutput updatePost(final long postId, final PostDtoUpdate postDtoUpdate) {
        final Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(String.format("Пост с id = %d не найден.", postId)));
        postMapper.updatePostFromDto(post, postDtoUpdate);

        log.info("Пост с id = {} обновлен.", post.getId());
        return postMapper.toPostDto(postRepository.save(post));
    }

    @Override
    public void deletePost(final long postId) {
        if (!postRepository.existsById(postId)) {
            log.warn("Пост для удаления не найден: id = {}.", postId);
            throw new NotFoundException(String.format("Пост с id = %d не найден.", postId));
        }

        postRepository.deleteById(postId);
        log.info("Пост с id  = {} удален.", postId);
    }
}
