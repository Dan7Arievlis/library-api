package io.github.dan7arievlis.libraryapi.repository;

import io.github.dan7arievlis.libraryapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByLogin(String login);

    User findByEmail(String email);
}
