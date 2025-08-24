package io.github.dan7arievlis.libraryapi.security;

import io.github.dan7arievlis.libraryapi.model.User;
import io.github.dan7arievlis.libraryapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String entryPassword = authentication.getCredentials().toString();

        User userFound = userService.findByLogin(login);

        if (userFound == null)
            throw getUsernameNotFoundException();

        String hashedPassword = encoder.encode(entryPassword);

        boolean matches = encoder.matches(entryPassword, hashedPassword);

        if (matches) {
            return new CustomAuthentication(userFound);
        }

        throw getUsernameNotFoundException();
    }

    private static UsernameNotFoundException getUsernameNotFoundException() {
        return new UsernameNotFoundException("User and/or password incorrect");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
