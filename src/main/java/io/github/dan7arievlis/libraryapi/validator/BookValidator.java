package io.github.dan7arievlis.libraryapi.validator;

import io.github.dan7arievlis.libraryapi.exceptions.DuplicatedRegisterException;
import io.github.dan7arievlis.libraryapi.exceptions.InvalidFieldException;
import io.github.dan7arievlis.libraryapi.model.Book;
import io.github.dan7arievlis.libraryapi.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookValidator {

    private final BookRepository repository;

    private static final int REQUIRED_PRICE_YEAR = 2020;

    public void validate(Book book) {
        if (existsBookWithIsbn(book))
            throw new DuplicatedRegisterException("ISBN already exists.");

        if (isObligatoryPriceIsNull(book))
            throw new InvalidFieldException("price", "Price is required for books published after 2020.");
    }

    private boolean isObligatoryPriceIsNull(Book book) {
        return book.getPrice() == null && book.getPublishDate().getYear() >= REQUIRED_PRICE_YEAR;
    }

    public boolean existsBookWithIsbn(Book book) {
        Optional<Book> retrievedBook = repository.findByIsbn(book.getIsbn());

        if (book.getId() == null)
            return retrievedBook.isPresent();

        return retrievedBook
                .map(Book::getId)
                .stream()
                .anyMatch(id -> !id.equals(book.getId()));
    }
}
