package io.github.dan7arievlis.libraryapi.controller.dto;

import io.github.dan7arievlis.libraryapi.model.enums.BookGenre;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record BookResponseDTO(
        UUID id,
        String isbn,
        String title,
        LocalDate publishDate,
        BookGenre genre,
        BigDecimal price,
        AuthorRequestDTO author) {

}
