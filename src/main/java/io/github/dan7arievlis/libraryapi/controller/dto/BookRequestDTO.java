package io.github.dan7arievlis.libraryapi.controller.dto;

import io.github.dan7arievlis.libraryapi.model.enums.BookGenre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.hibernate.validator.constraints.ISBN;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record BookRequestDTO(
        @NotBlank(message = "obligatory field.")
        @ISBN(message = "must configure an isbn pattern.")
        String isbn,
        @NotBlank(message = "obligatory field.")
        String title,
        @NotNull(message = "obligatory field.")
        @PastOrPresent(message = "date can't be in future.")
        LocalDate publishDate,
        BookGenre genre,
        @NumberFormat(style = NumberFormat.Style.CURRENCY)
        BigDecimal price,
        @NotNull(message = "obligatory field.")
        UUID authorId) {

}
