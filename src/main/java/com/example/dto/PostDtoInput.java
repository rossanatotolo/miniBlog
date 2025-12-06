package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDtoInput {
    @NotBlank
    @Size(min = 2, max = 255)
    private String title;
    @NotBlank
    @Size(min = 2, max = 512)
    private String content;
    @NotNull
    private Long authorId;
}
