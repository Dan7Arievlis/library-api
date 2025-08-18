package io.github.dan7arievlis.libraryapi;

import io.github.dan7arievlis.libraryapi.model.Author;
import io.github.dan7arievlis.libraryapi.repository.AuthorRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        var context = SpringApplication.run(Application.class, args);
        AuthorRepository repository = context.getBean(AuthorRepository.class);

        saveAuthorExample(repository);
    }

    public static void saveAuthorExample(AuthorRepository authorRepository) {
        Author author = new Author();
        author.setName("Daniel");
        author.setNationality("Brasileira");
        author.setBirthDate(LocalDate.of(1980, 1, 1));

        Author savedAuthor = authorRepository.save(author);
        System.out.println("Author saved: " + savedAuthor);
    }

}
