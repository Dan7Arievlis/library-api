package io.github.dan7arievlis.libraryapi.service;

import io.github.dan7arievlis.libraryapi.model.Client;
import io.github.dan7arievlis.libraryapi.repository.ClientRepository;
import io.github.dan7arievlis.libraryapi.validator.ClientValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository repository;
    private final ClientValidator validator;
    private final PasswordEncoder encoder;

    public Client save(Client client) {
        client.setClientSecret(encoder.encode(client.getClientSecret()));
        validator.validate(client);
        return repository.save(client);
    }

    public Client findByClientId(String clientId) {
        return repository.findByClientId(clientId).orElseGet(null);
    }
}
