package io.github.dan7arievlis.libraryapi.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AuthorRequestDTO(
        @NotBlank(message = "obligatory field.")
        @Size(min = 2, max = 100, message = "field out of bounds. ({min} - {max})")
        String name,
        @NotNull(message = "obligatory field.")
        @Past(message = "date can't be in future.")
        LocalDate birthDate,
        @NotBlank(message = "obligatory field.")
        @Size(min = 2, max = 50, message = "field out of max bounds. ({min} - {max})")
        String nationality) {
    
}
