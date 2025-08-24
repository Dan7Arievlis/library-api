package io.github.dan7arievlis.libraryapi.service;

import io.github.dan7arievlis.libraryapi.model.User;
import io.github.dan7arievlis.libraryapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public void save(User user) {
        user.setPassword(encoder.encode(user.getPassword()));

        repository.save(user);
    }

    public User findByLogin(String login) {
        return repository.findByLogin(login);
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

}
