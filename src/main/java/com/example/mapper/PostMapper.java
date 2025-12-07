package com.example.mapper;

import com.example.dto.PostDtoInput;
import com.example.dto.PostDtoOutput;
import com.example.dto.PostDtoUpdate;
import com.example.exception.NotFoundException;
import com.example.model.Post;
import com.example.model.User;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PostMapper {
    private final UserRepository userRepository;
    public PostDtoOutput toPostDto(final Post post) {
        final PostDtoOutput postDtoOutput = new PostDtoOutput();

        postDtoOutput.setId(post.getId());
        postDtoOutput.setTitle(post.getTitle());
        postDtoOutput.setContent(post.getContent());

        if (post.getAuthor() != null) {
            postDtoOutput.setAuthorId(post.getAuthor().getId());
        }

        return postDtoOutput;
    }

    public Post toPost(final PostDtoInput postDtoInput) {
        final Post post = new Post();

        post.setTitle(postDtoInput.getTitle());
        post.setContent(postDtoInput.getContent());

        final User author = userRepository.findById(postDtoInput.getAuthorId())
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с id = %d не найден.", postDtoInput.getAuthorId())));

        post.setAuthor(author);

        return post;
    }

    public void updatePostFromDto(Post post, PostDtoUpdate postDtoUpdate) {
        if (postDtoUpdate.getTitle() != null) {
            post.setTitle(postDtoUpdate.getTitle());
        }

        if (postDtoUpdate.getContent() != null) {
            post.setContent(postDtoUpdate.getContent());
        }
    }

    public List<PostDtoOutput> toListDto(Iterable<Post> posts) {
        List<PostDtoOutput> result = new ArrayList<>();

        for (Post post : posts) {
            result.add(toPostDto(post));
        }
        return result;
    }
}
