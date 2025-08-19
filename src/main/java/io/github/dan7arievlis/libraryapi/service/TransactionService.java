package io.github.dan7arievlis.libraryapi.service;

import io.github.dan7arievlis.libraryapi.model.Author;
import io.github.dan7arievlis.libraryapi.model.Book;
import io.github.dan7arievlis.libraryapi.model.enums.BookGenre;
import io.github.dan7arievlis.libraryapi.repository.AuthorRepository;
import io.github.dan7arievlis.libraryapi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransactionService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Transactional
    public void updateWithoutUpdating() {
        var book = bookRepository.findById(UUID.fromString("0ac79922-5aad-4eca-9cd1-3840616d2f02")).orElse(null);

        assert book != null;
        book.setPublishDate(LocalDate.of(2020, 10, 1));
    }

    @Transactional
    public void execute() {
        Author author = new Author();
        author.setName("Gabriel");
        author.setBirthDate(LocalDate.of(2001, 3, 8));
        author.setNationality("Brasileira");

        authorRepository.save(author);

        Book book = new Book();
        book.setTitle("Livro Gabriel");
        book.setIsbn("90989-306969");
        book.setPrice(BigDecimal.valueOf(74.9));
        book.setPublishDate(LocalDate.of(2021, 12, 3));
        book.setGenre(BookGenre.FICTION);
        book.setAuthor(author);

        bookRepository.save(book);

        if(author.getName().equalsIgnoreCase("Gabriel")){
            throw new RuntimeException("Rollback!");
        }
    }
}
