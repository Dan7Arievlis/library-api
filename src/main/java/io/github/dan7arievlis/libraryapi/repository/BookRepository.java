package io.github.dan7arievlis.libraryapi.repository;

import io.github.dan7arievlis.libraryapi.model.Author;
import io.github.dan7arievlis.libraryapi.model.Book;
import io.github.dan7arievlis.libraryapi.model.enums.BookGenre;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @see BookRepositoryTest
 */

public interface BookRepository extends JpaRepository<Book, UUID> {
    public List<Book> findByAuthor(Author author);

    public List<Book> findByTitleContainingIgnoreCase(String title);

    public Book findByIsbn(String isbn);

    public List<Book> findByTitleContainingIgnoreCaseAndPriceLessThanEqual(String title, BigDecimal priceIsLessThan);

    public List<Book> findByTitleContainingIgnoreCaseOrIsbn(String title, String isbn);

    @Query("select b from Book b order by b.title, b.price")
    public List<Book> findAllBooks();

    @Query("select a from Book b join b.author a order by a.name")
    public List<Author> findAllBookAuthors();

    @Query("select distinct a.name from Book b join b.author a order by a.name")
    public List<String> findDistinctAuthorsNames();

    @Query("""
        select distinct b.genre 
        from Book b join b.author a 
        where a.nationality = 'Brasileira' 
        order by b.genre    
        """)
    public List<BookGenre> findAllBookGenresFromBrazilianAuthors();

    @Query("select b from Book b where b.genre = :genre order by :orderParam")
    public List<Book> findByGenreNamedParameters(@Param("genre") BookGenre genre, @Param("orderParam") String orderProperty);

    @Query("select b from Book b where b.genre = ?1 order by ?2")
    public List<Book> findByGenrePositionalParameters(BookGenre genre, String orderProperty);

    @Modifying
    @Transactional
    @Query("delete from Book where genre = ?1")
    public void deleteByGenre(BookGenre genre);

    @Modifying
    @Transactional
    @Query("update Book b set b.publishDate = ?2 where b.title like CONCAT('%', ?1, '%')")
    public void updatePublishDateForTitles(String title, LocalDate newDate);
}
