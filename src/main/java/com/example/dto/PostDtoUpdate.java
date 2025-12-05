package com.example.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDtoUpdate {
    @Size(min = 2, max = 255)
    private String title;
    @Size(min = 2, max = 512)
    private String content;
}
