package io.github.dan7arievlis.libraryapi.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UserRequestDTO(
        @NotBlank(message = "obligatory field.")
        @Size(min = 3, message = "field must have at least {min} characters")
        String login,
        @NotBlank(message = "obligatory field.")
//        @Size(min = 8, message = "field must have at least {min} characters")
        String password,
        @NotBlank(message = "obligatory field.")
        @Email(message = "invalid email.")
        @Size(min = 5, max = 150, message = "field out of bounds. ({min} - {max})")
        String email,
        List<String> roles
) {
}
