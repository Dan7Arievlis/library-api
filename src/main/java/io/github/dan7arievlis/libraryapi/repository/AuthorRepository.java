package io.github.dan7arievlis.libraryapi.repository;

import io.github.dan7arievlis.libraryapi.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
    public List<Author> findByNameContainingIgnoreCaseAndNationality(String name,String nationality);

    public List<Author> findByNameContainingIgnoreCase(String name);

    public List<Author> findByNationality(String nationality);

    public Optional<Author> findByNameAndBirthDateAndNationality(String name, LocalDate birthDate, String nationality);
}
