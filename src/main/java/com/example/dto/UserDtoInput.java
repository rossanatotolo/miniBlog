package com.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDtoInput {
    @NotBlank
    @Size(min = 6, max = 255)
    @Email(message = "Имейл должен содержать символ «@». Формат имейла: example@mail.com")
    private String email;
    @Size(min = 2, max = 50)
    @NotBlank(message = "Имя пользователя должно быть указано")
    private String username;
}
