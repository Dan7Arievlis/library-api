package io.github.dan7arievlis.libraryapi.controller.dto;

import io.github.dan7arievlis.libraryapi.model.Author;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorResponseDTO(
        UUID id,
        String name,
        LocalDate birthDate,
        String nationality) {

    public Author toAuthor() {
        var author = new Author();
        author.setId(id);
        author.setName(name);
        author.setBirthDate(birthDate);
        author.setNationality(nationality);

        return author;
    }
}
