package io.github.dan7arievlis.libraryapi.repository.specs;

import io.github.dan7arievlis.libraryapi.model.Book;
import io.github.dan7arievlis.libraryapi.model.enums.BookGenre;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecs {
    public static Specification<Book> isbnEqual(String isbn) {
        return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    public static Specification<Book> titleLike(String title) {
        return (root, query, cb) -> cb.like(cb.upper(root.get("title")), "%" + title.toUpperCase() + "%");
    }

    public static Specification<Book> genreEqual(BookGenre genre) {
        return (root, query, cb) -> cb.equal(root.get("genre"), genre);
    }

    public static Specification<Book> publishYearEqual(Integer publishYear) {
        return (root, query, cb) -> cb.equal(
                cb.function("to_char", String.class, root.get("publishDate") , cb.literal("YYYY"))
                , publishYear.toString());
    }

    public static Specification<Book> authorNameLike(String name) {
        return (root, query, cb) -> {
            Join<Object, Object> joinAuthor = root.join("author", JoinType.INNER    );
            return cb.like(cb.upper(joinAuthor.get("name")), "%" + name.toUpperCase() + "%");

//            return cb.like(cb.upper(root.get("author").get("name")), "%" + name.toUpperCase() + "%");
        };
    }
}
