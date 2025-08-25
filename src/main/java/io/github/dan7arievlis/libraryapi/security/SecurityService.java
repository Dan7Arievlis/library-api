package io.github.dan7arievlis.libraryapi.security;

import io.github.dan7arievlis.libraryapi.model.User;
import io.github.dan7arievlis.libraryapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {
    private final UserService userService;

    public User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof CustomAuthentication customAuth)
            return customAuth.getUser();
        return null;
    }
}
