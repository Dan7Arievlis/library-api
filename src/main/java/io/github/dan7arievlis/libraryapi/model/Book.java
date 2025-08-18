package io.github.dan7arievlis.libraryapi.model;

import io.github.dan7arievlis.libraryapi.model.enums.BookGenre;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "book")
@Data
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "isbn", length = 20,  nullable = false, unique = true)
    private String isbn;

    @Column(name = "title", length = 150,  nullable = false)
    private String title;

    @Column(name = "publish_date", nullable = false)
    private LocalDate publishDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre", length = 30, nullable = false)
    private BookGenre genre;

    @Column(name = "price", precision = 18, scale = 2)
    private BigDecimal price;
//    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;
}
