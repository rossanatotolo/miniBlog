package com.example.repository;

import com.example.model.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByIdIn(final List<Long> userIds, final PageRequest pageRequest);

    boolean existsByEmailOrUsername(String email, String username);
}
