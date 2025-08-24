package io.github.dan7arievlis.libraryapi.controller;

import io.github.dan7arievlis.libraryapi.security.CustomAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginViewController {
    @GetMapping("/login")
    public String loginView() {
        return "login";
    }

    @GetMapping("/")
    @ResponseBody
    public String homePage(Authentication authentication) {
        if (authentication instanceof CustomAuthentication customAuth) {
            System.out.println(customAuth.getUser());
        }
        return "Hello " + authentication.getName();
    }
}
