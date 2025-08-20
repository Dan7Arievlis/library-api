package io.github.dan7arievlis.libraryapi.controller;

import io.github.dan7arievlis.libraryapi.controller.dto.AuthorRequestDTO;
import io.github.dan7arievlis.libraryapi.controller.dto.AuthorResponseDTO;
import io.github.dan7arievlis.libraryapi.controller.dto.ErrorResponse;
import io.github.dan7arievlis.libraryapi.exceptions.DuplicatedRegisterException;
import io.github.dan7arievlis.libraryapi.exceptions.OperationNotAllowedException;
import io.github.dan7arievlis.libraryapi.service.AuthorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService service;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid AuthorRequestDTO author) {
        try {
            var authorEntity = author.toAuthor();
            service.save(authorEntity);

            URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(authorEntity.getId())
                    .toUri();

            return ResponseEntity.created(uri).build();
        } catch (DuplicatedRegisterException e) {
            var errorDTO = ErrorResponse.conflict(e.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }

    @GetMapping({"{id}"})
    public ResponseEntity<AuthorResponseDTO> getDetails(@PathVariable String id) {
        try {
            var authorId = UUID.fromString(id);
            var author = service.getById(authorId);
            if (author == null)
                throw new EntityNotFoundException("Author not found");

            var responseDTO = new AuthorResponseDTO(author.getId(),  author.getName(), author.getBirthDate(), author.getNationality());

            return ResponseEntity.ok(responseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        try {
            var authorId = UUID.fromString(id);
            var author = service.getById(authorId);
            if (author == null)
                throw new EntityNotFoundException("Author not found");

            service.delete(author);
            return ResponseEntity.noContent().build();
        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch(OperationNotAllowedException e) {
            var errorDTO = ErrorResponse.defaultResponse(e.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponseDTO>> search(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "nationality", required = false) String nationality) {
        List<AuthorResponseDTO> search = service.searchByExample(name, nationality)
                .stream()
                .map(author -> new AuthorResponseDTO(
                        author.getId(),
                        author.getName(),
                        author.getBirthDate(),
                        author.getNationality())
                ).toList();

        return ResponseEntity.ok(search);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(
            @PathVariable String id,
            @RequestBody @Valid AuthorRequestDTO authorDto) {
        try {
            var authorId = UUID.fromString(id);
            var author = service.getById(authorId);
            if (author == null)
                throw new EntityNotFoundException("Author not found");

            author.setName(authorDto.name());
            author.setBirthDate(authorDto.birthDate());
            author.setNationality(authorDto.nationality());

            service.update(author);

            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch(DuplicatedRegisterException e) {
            var errorDTO = ErrorResponse.conflict(e.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }

}
