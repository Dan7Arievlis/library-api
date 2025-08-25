package io.github.dan7arievlis.libraryapi.repository;

import io.github.dan7arievlis.libraryapi.model.Author;
import io.github.dan7arievlis.libraryapi.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    Optional<Client> findByClientId(String clientId);
}
