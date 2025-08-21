package io.github.dan7arievlis.libraryapi.service;

import io.github.dan7arievlis.libraryapi.model.Book;
import io.github.dan7arievlis.libraryapi.model.enums.BookGenre;
import io.github.dan7arievlis.libraryapi.repository.BookRepository;
import io.github.dan7arievlis.libraryapi.validator.BookValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.github.dan7arievlis.libraryapi.repository.specs.BookSpecs.*;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;
    private final BookValidator validator;

    public Book save(Book book) {
        validator.validate(book);
        return repository.save(book);
    }

    public Optional<Book> findById(UUID id) {
        return repository.findById(id);
    }

    public void delete(Book book) {
        repository.delete(book);
    }

    public Page<Book> search(String isbn, String title, String authorName, BookGenre genre, Integer publishYear, Integer page, Integer pageSize) {
//        Specification<Book> specs = Specification
//                .where(BookSpecs.isbnEqual(isbn))
//                .and(BookSpecs.titleLike(title))
//                .and(BookSpecs.genreEqual(genre));

        Specification<Book> specs = Specification.where((root, query, cb) -> cb.conjunction());

        if (isbn != null) {
            specs = specs.and(isbnEqual(isbn));
        }

        if (title != null) {
            specs = specs.and(titleLike(title));
        }

        if (genre != null) {
            specs = specs.and(genreEqual(genre));
        }

        if (publishYear != null) {
            specs = specs.and(publishYearEqual(publishYear));
        }

        if (authorName != null) {
            specs = specs.and(authorNameLike(authorName));
        }

        Pageable pageRequest = PageRequest.of(page, pageSize);

        return repository.findAll(specs, pageRequest);
    }

    public void update(Book book) {
        if (book.getId() == null)
            throw new IllegalArgumentException("Is necessary to have a saved book in db to update it");

        validator.validate(book);
        repository.save(book);
    }
}
