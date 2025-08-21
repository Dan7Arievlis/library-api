package io.github.dan7arievlis.libraryapi.controller;

import io.github.dan7arievlis.libraryapi.controller.dto.AuthorRequestDTO;
import io.github.dan7arievlis.libraryapi.controller.dto.AuthorResponseDTO;
import io.github.dan7arievlis.libraryapi.controller.dto.mappers.AuthorMapper;
import io.github.dan7arievlis.libraryapi.service.AuthorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("authors")
@RequiredArgsConstructor
public class AuthorController implements GenericController {

    private final AuthorService service;
    private final AuthorMapper mapper;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid AuthorRequestDTO dto) {
        var author = mapper.RequestToEntity(dto);
        service.save(author);
        URI uri = generateHeaderLocation(author.getId());
        return ResponseEntity.created(uri).build();
    }

    @GetMapping({"{id}"})
    public ResponseEntity<AuthorResponseDTO> getDetails(@PathVariable String id) {
        var authorId = UUID.fromString(id);
        return service.getById(authorId)
                .map(author -> ResponseEntity.ok(mapper.toResponse(author)))
                .orElseThrow(() -> new EntityNotFoundException("Author not found."));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        var authorId = UUID.fromString(id);
        return service.getById(authorId)
                .map(author -> {
                    service.delete(author);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponseDTO>> search(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "nationality", required = false) String nationality) {
        List<AuthorResponseDTO> search = service
                .searchByExample(name, nationality)
                .stream()
                .map(mapper::toResponse)
                .toList();

        return ResponseEntity.ok(search);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(
            @PathVariable String id,
            @RequestBody @Valid AuthorRequestDTO authorDto) {
        var authorId = UUID.fromString(id);
        return service.getById(authorId)
                .map(author -> {
                    author.setName(authorDto.name());
                    author.setBirthDate(authorDto.birthDate());
                    author.setNationality(authorDto.nationality());

                    service.update(author);

                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));
    }

}
