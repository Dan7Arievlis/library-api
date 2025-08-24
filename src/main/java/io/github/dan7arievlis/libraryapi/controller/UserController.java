package io.github.dan7arievlis.libraryapi.controller;

import io.github.dan7arievlis.libraryapi.controller.dto.UserRequestDTO;
import io.github.dan7arievlis.libraryapi.controller.dto.mappers.UserMapper;
import io.github.dan7arievlis.libraryapi.model.User;
import io.github.dan7arievlis.libraryapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController implements GenericController {

    private final UserService service;
    private final UserMapper mapper;

    @PostMapping
    public ResponseEntity<User> save(@RequestBody @Valid UserRequestDTO dto) {
        var user = mapper.RequestToEntity(dto);
        service.save(user);
        URI uri = generateHeaderLocation(user.getId());
        return ResponseEntity.created(uri).build();
    }
}
