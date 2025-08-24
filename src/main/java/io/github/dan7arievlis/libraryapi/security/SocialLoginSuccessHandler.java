package io.github.dan7arievlis.libraryapi.security;

import io.github.dan7arievlis.libraryapi.model.User;
import io.github.dan7arievlis.libraryapi.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SocialLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService userService;
    private final PasswordEncoder encoder;

    private static final String DEFAULT_PASSWORD = "password";

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        OAuth2User oauth2User = token.getPrincipal();
        String email = oauth2User.getAttribute("email");

        User user = userService.findByEmail(email);
        if (user == null)
            user = registerUser(email);

        authentication = new CustomAuthentication(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        super.onAuthenticationSuccess(request, response, authentication);
    }

    private User registerUser(String email) {
        User user;
        user = new User();
        user.setEmail(email);
        user.setLogin(email.substring(0, email.indexOf("@")));
        user.setPassword(encoder.encode(DEFAULT_PASSWORD));
        user.setRoles(List.of("OPERATOR"));
        userService.save(user);
        return user;
    }
}
