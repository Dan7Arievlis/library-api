package io.github.dan7arievlis.libraryapi.service;

import io.github.dan7arievlis.libraryapi.model.Book;
import io.github.dan7arievlis.libraryapi.model.enums.BookGenre;
import io.github.dan7arievlis.libraryapi.repository.BookRepository;
import io.github.dan7arievlis.libraryapi.repository.specs.BookSpecs;
import io.github.dan7arievlis.libraryapi.security.SecurityService;
import io.github.dan7arievlis.libraryapi.validator.BookValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;
    private final BookValidator validator;
    private final SecurityService securityService;

    public Book save(Book book) {
        validator.validate(book);
        book.setUser(securityService.getLoggedUser());
        return repository.save(book);
    }

    public Optional<Book> findById(UUID id) {
        return repository.findById(id);
    }

    public void delete(Book book) {
        repository.delete(book);
    }

    public Page<Book> search(String isbn, String title, String authorName, BookGenre genre, Integer publishYear, Integer page, Integer pageSize) {

        Specification<Book> specs = Stream.of(
                    optSpec(isbn, BookSpecs::isbnEqual),
                    optSpec(title, BookSpecs::titleLike),
                    optSpec(genre, BookSpecs::genreEqual),
                    optSpec(publishYear, BookSpecs::publishYearEqual),
                    optSpec(authorName, BookSpecs::authorNameLike)
                )
                .reduce(Specification.where(null), Specification::and);

        Pageable pageRequest = PageRequest.of(page, pageSize);

        return repository.findAll(specs, pageRequest);
    }

    public void update(Book book) {
        if (book.getId() == null)
            throw new IllegalArgumentException("Is necessary to have a saved book in db to update it");

        validator.validate(book);
        book.setUser(securityService.getLoggedUser());
        repository.save(book);
    }

    private static <V, T> Specification<Book> optSpec(V v, Function<V, Specification<Book>> f) {
        return v == null ? Specification.where(null) : f.apply(v);
    }
}
