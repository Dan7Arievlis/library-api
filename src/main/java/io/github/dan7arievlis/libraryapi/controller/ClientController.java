package io.github.dan7arievlis.libraryapi.controller;

import io.github.dan7arievlis.libraryapi.controller.dto.ClientRequestDTO;
import io.github.dan7arievlis.libraryapi.controller.dto.mappers.ClientMapper;
import io.github.dan7arievlis.libraryapi.model.Client;
import io.github.dan7arievlis.libraryapi.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
public class ClientController implements GenericController {
    private final ClientService service;
    private final ClientMapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Client> save(@RequestBody @Valid ClientRequestDTO dto) {
        Client client = mapper.requestToEntity(dto);
        service.save(client);
        URI uri = generateHeaderLocation(client.getId());
        return ResponseEntity.created(uri).build();
    }
}
