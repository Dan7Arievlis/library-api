package io.github.dan7arievlis.libraryapi.repository;

import io.github.dan7arievlis.libraryapi.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
}
