package io.github.dan7arievlis.libraryapi.repository;

import io.github.dan7arievlis.libraryapi.model.Author;
import io.github.dan7arievlis.libraryapi.model.Book;
import io.github.dan7arievlis.libraryapi.model.enums.BookGenre;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class AuthorRepositoryTest {

    @Autowired
    AuthorRepository repository;
    @Autowired
    BookRepository bookRepository;

    @Test
    public void saveTest() {
        Author author = Mockito.mock(Author.class);
        author.setName("Lívia");
        author.setNationality("Brasileira");
        author.setBirthDate(LocalDate.of(2004, 3, 8));

        var saved =  repository.save(author);
        Assertions.assertEquals(saved, author);
    }

    @Test
    public void findByIdTest() {
        Author expected = Mockito.mock(Author.class);
        expected.setId(UUID.fromString("4f71af56-67f3-4050-a535-678d37a430c8"));
        expected.setName("Daniel");
        expected.setNationality("Brasileira");
        expected.setBirthDate(LocalDate.of(1980, 1, 1));

        Author actual = repository.findById(expected.getId()).orElse(null);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void updateTest() {
        UUID id = UUID.fromString("4f71af56-67f3-4050-a535-678d37a430c8");
        Author expected = Mockito.mock(Author.class);
        expected.setId(id);
        expected.setName("Lívia");
        expected.setNationality("Brasileira");
        expected.setBirthDate(LocalDate.of(1980, 1, 1));

        Author retrievedAuthor = repository.findById(id).orElse(null);
        if (retrievedAuthor != null) {
            retrievedAuthor.setName("Lívia");
            retrievedAuthor = repository.save(retrievedAuthor);
        }

        Assertions.assertEquals(expected, retrievedAuthor);
    }

    @Test
    void deleteByIdTest() {
        UUID id = UUID.fromString("c04136c7-e981-44e4-b9b4-9c7caf8d405e");
        repository.deleteById(id);
    }

    @Test
    void saveAuthorWithBooksTest() {
        Author author = new Author();
        author.setName("Camila");
        author.setNationality("Alemã");
        author.setBirthDate(LocalDate.of(2000, 6, 17));

        Book book = new Book();
        book.setTitle("Uma história sobre família");
        book.setIsbn("92039-194059");
        book.setPrice(BigDecimal.valueOf(45.99));
        book.setPublishDate(LocalDate.of(2025, 3, 5));
        book.setGenre(BookGenre.ROMANCE);
        book.setAuthor(author);

        Book book2 = new Book();
        book2.setTitle("Uma história sobre memórias");
        book2.setIsbn("92039-194072");
        book2.setPrice(BigDecimal.valueOf(85));
        book2.setPublishDate(LocalDate.of(2025, 7, 5));
        book2.setGenre(BookGenre.ROMANCE);
        book2.setAuthor(author);


        author.getBooks().add(book);
        author.getBooks().add(book2);

        repository.save(author);
        bookRepository.saveAll(List.of(book, book2));
    }

    @Test
    void listAuthorBooksTest() {
        UUID id = UUID.fromString("36cc9d3d-1369-463e-ad07-e2f5330bef22");
        var author = repository.findById(id).orElse(null);

        List<Book> list = bookRepository.findByAuthor(author);

        author.setBooks(list);
        author.getBooks().forEach(System.out::println);
    }
}
