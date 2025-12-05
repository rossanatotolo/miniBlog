package com.example.dto;

import com.example.model.User;
import lombok.Data;

@Data
public class PostDtoOutput {
    private Long id;
    private String title;
    private String content;
    private User author;
}
