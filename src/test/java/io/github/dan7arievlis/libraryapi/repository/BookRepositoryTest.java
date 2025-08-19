package io.github.dan7arievlis.libraryapi.repository;

import io.github.dan7arievlis.libraryapi.model.Author;
import io.github.dan7arievlis.libraryapi.model.Book;
import io.github.dan7arievlis.libraryapi.model.enums.BookGenre;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;

    @Test
    void saveTest() {
        Book book = new Book();
        book.setTitle("Book Title");
        book.setIsbn("90009-29384");
        book.setPrice(BigDecimal.valueOf(82.34));
        book.setPublishDate(LocalDate.of(1999, 9, 29));
        book.setGenre(BookGenre.FICTION);

        Author author = authorRepository.findById(UUID.fromString("c275a276-504b-47a2-b457-07c900fca812")).orElse(null);
        book.setAuthor(author);

        bookRepository.save(book);
    }

    @Test
    void saveBookAuthorTest() {
        Book book = new Book();
        book.setTitle("Meu segundo Livro");
        book.setIsbn("90989-306900");
        book.setPrice(BigDecimal.valueOf(74.9));
        book.setPublishDate(LocalDate.of(2021, 12, 3));
        book.setGenre(BookGenre.FICTION);

        Author author = new Author();
        author.setName("Gabriel");
        author.setBirthDate(LocalDate.of(2001, 3, 8));
        author.setNationality("Brasileira");

        authorRepository.save(author);

        book.setAuthor(author);

        bookRepository.save(book);
    }

    @Test
    void saveCascadeTest() {
        Book book = new Book();
        book.setTitle("Meu primeiro Livro");
        book.setIsbn("90789-296900");
        book.setPrice(BigDecimal.valueOf(102.34));
        book.setPublishDate(LocalDate.of(2017, 1, 29));
        book.setGenre(BookGenre.BIOGRAPHY);

        Author author = new Author();
        author.setName("Lívia");
        author.setBirthDate(LocalDate.of(2004, 3, 8));
        author.setNationality("Brasileira");

        book.setAuthor(author);
        bookRepository.save(book);
    }

    @Test
    void updateBookAuthorTest() {
        UUID bookId = UUID.fromString("cc84b1ed-b386-4480-afcb-582d87f91c12");
        Book bookToUpdate = bookRepository.findById(bookId).orElse(null);

        UUID authorId = UUID.fromString("36cc9d3d-1369-463e-ad07-e2f5330bef22");
        Author author = authorRepository.findById(authorId).orElse(null);

        bookToUpdate.setAuthor(author);

        bookRepository.save(bookToUpdate);
    }

    @Test
    void deleteByIdTest() {
        UUID bookId = UUID.fromString("a8036715-1873-4ef1-9eeb-80b5a512d980");
        bookRepository.deleteById(bookId);
    }

    @Test
    @Transactional
    void findAuthorByBookIdTest() {
        UUID bookId = UUID.fromString("cc84b1ed-b386-4480-afcb-582d87f91c12");
        Book book = bookRepository.findById(bookId).orElse(null);
        System.out.println("Livro: " + book.getTitle());
        System.out.println("Autor: " + book.getAuthor().getName());
    }

    @Test
    void findBookByTitleAndPriceLessThanEqualTest() {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCaseAndPriceLessThanEqual("livro", BigDecimal.valueOf(102.33));
        System.out.println("Livros: ");
        books.forEach(System.out::println);
    }

    @Test
    void findAllBooksWithJPQLTest() {
        List<Book> books = bookRepository.findAllBooks();

        System.out.println("Livros: ");
        books.forEach(System.out::println);
    }

    @Test
    void findAllBooksAuthorsWithJPQLTest() {
        List<Author> authors = bookRepository.findAllBookAuthors();

        System.out.println("Autores: ");
        authors.forEach(System.out::println);
    }

    @Test
    void findDistinctAuthorsTest() {
        bookRepository.findDistinctAuthorsNames().forEach(System.out::println);
    }

    @Test
    void findAllBookGenresFromBrazilianAuthorsTest() {
        bookRepository.findAllBookGenresFromBrazilianAuthors().forEach(System.out::println);
    }

    @Test
    void findBookGenreNamedParametersTest() {
        bookRepository.findByGenreNamedParameters(BookGenre.FICTION, "publish_date").forEach(System.out::println);
    }

    @Test
    void findBookGenrePositionalParametersTest() {
        bookRepository.findByGenrePositionalParameters(BookGenre.FICTION, "publish_date").forEach(System.out::println);
    }

    @Test
    void updateBookPublishDateByTitleTest() {
        bookRepository.updatePublishDateForTitles("title", LocalDate.of(1997, 1, 29));
        bookRepository.findAllBooks().forEach(System.out::println);
    }
}