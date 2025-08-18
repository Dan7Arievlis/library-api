package io.github.dan7arievlis.libraryapi.repository;

import io.github.dan7arievlis.libraryapi.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
}
