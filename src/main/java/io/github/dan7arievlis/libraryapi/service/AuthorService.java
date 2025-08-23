package io.github.dan7arievlis.libraryapi.service;

import io.github.dan7arievlis.libraryapi.exceptions.OperationNotAllowedException;
import io.github.dan7arievlis.libraryapi.model.Author;
import io.github.dan7arievlis.libraryapi.repository.AuthorRepository;
import io.github.dan7arievlis.libraryapi.repository.BookRepository;
import io.github.dan7arievlis.libraryapi.security.SecurityService;
import io.github.dan7arievlis.libraryapi.validator.AuthorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository repository;
    private final AuthorValidator validator;
    private final BookRepository bookRepository;
    private final SecurityService securityService;

    public Author save(Author author) {
        validator.validate(author);
        author.setUser(securityService.getLoggedUser());
        return repository.save(author);
    }

    public void update(Author author) {
        if (author.getId() == null)
            throw new IllegalArgumentException("Is necessary to have a saved author in db to update it");

        validator.validate(author);
        author.setUser(securityService.getLoggedUser());
        repository.save(author);
    }

    public Optional<Author> getById(UUID authorId) {
        return repository.findById(authorId);
    }

    public void delete(Author author) {
        if(hasBooks(author))
            throw new OperationNotAllowedException("Its not allowed to delete an Author that has registered Books.");
        repository.delete(author);
    }

//    public List<Author> search(String name, String nationality) {
//        if (name != null && nationality != null)
//            return repository.findByNameContainingIgnoreCaseAndNationality(name, nationality);
//
//        else if (name != null)
//            return repository.findByNameContainingIgnoreCase(name);
//
//        else if (nationality != null)
//            return repository.findByNationality(nationality);
//
//        return repository.findAll();
//    }

    public List<Author> searchByExample(String name, String nationality) {
        var author = new Author();
        author.setName(name);
        author.setNationality(nationality);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnorePaths("id", "registrationDate", "birthDate")
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Author> authorExample = Example.of(author, matcher);

        return repository.findAll(authorExample);
    }

    public boolean hasBooks(Author author) {
        return bookRepository.existsByAuthor(author);
    }
}
