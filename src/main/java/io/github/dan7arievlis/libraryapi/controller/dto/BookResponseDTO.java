package io.github.dan7arievlis.libraryapi.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dan7arievlis.libraryapi.model.enums.BookGenre;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record BookResponseDTO(
        UUID id,
        String isbn,
        String title,
        @JsonFormat(shape =  JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate publishDate,
        BookGenre genre,
        BigDecimal price,
        AuthorRequestDTO author) {

}
