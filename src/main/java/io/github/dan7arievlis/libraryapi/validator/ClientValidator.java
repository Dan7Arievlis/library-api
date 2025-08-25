package io.github.dan7arievlis.libraryapi.validator;

import io.github.dan7arievlis.libraryapi.exceptions.DuplicatedRegisterException;
import io.github.dan7arievlis.libraryapi.model.Author;
import io.github.dan7arievlis.libraryapi.model.Client;
import io.github.dan7arievlis.libraryapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClientValidator {
    private final ClientRepository repository;

    public void validate(Client client) {
        if (existsRegisteredClient(client)) {
            throw new DuplicatedRegisterException("Client already exists");
        }
    }

    private boolean existsRegisteredClient(Client client) {
        Optional<Client> foundClient = repository.findByClientId(client.getClientId());

        if(client.getId() == null)
            return foundClient.isPresent();

        return foundClient.isPresent() && !client.getId().equals(foundClient.get().getId());
    }
}
