package io.github.dan7arievlis.libraryapi.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UserRequestDTO(
        @NotNull
        @Size(min = 3)
        String login,
        @NotNull
        String password,
        List<String> roles
) {
}
