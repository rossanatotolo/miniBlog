package com.example.repository;

import com.example.model.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByIdIn(List<Long> ids, PageRequest pageRequest);
}
