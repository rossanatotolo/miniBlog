package com.example.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class PostDtoOutput {
    private Long id;
    private String title;
    private String content;
    private Long authorId;
    private Instant createdAt;
    private Instant updatedAt;
}
