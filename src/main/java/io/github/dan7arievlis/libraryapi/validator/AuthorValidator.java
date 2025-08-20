package io.github.dan7arievlis.libraryapi.validator;

import io.github.dan7arievlis.libraryapi.exceptions.DuplicatedRegisterException;
import io.github.dan7arievlis.libraryapi.model.Author;
import io.github.dan7arievlis.libraryapi.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthorValidator {
    private final AuthorRepository authorRepository;

    public AuthorValidator(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void validate(Author author) {
        if (existsRegisteredAuthor(author)) {
            throw new DuplicatedRegisterException("Author already exists");
        }
    }

    private boolean existsRegisteredAuthor(Author author) {
        Optional<Author> foundAuthor = authorRepository.findByNameAndBirthDateAndNationality(
                author.getName(),
                author.getBirthDate(),
                author.getNationality()
        );

        if(author.getId() == null)
            return foundAuthor.isPresent();

        return foundAuthor.isPresent() && !author.getId().equals(foundAuthor.get().getId());
    }
}
