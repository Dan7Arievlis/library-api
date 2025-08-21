package io.github.dan7arievlis.libraryapi.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorResponseDTO(
        UUID id,
        String name,
        LocalDate birthDate,
        String nationality) {

}
