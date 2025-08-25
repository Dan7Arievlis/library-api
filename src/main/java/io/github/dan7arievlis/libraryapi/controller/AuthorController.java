package io.github.dan7arievlis.libraryapi.controller;

import io.github.dan7arievlis.libraryapi.controller.dto.AuthorRequestDTO;
import io.github.dan7arievlis.libraryapi.controller.dto.AuthorResponseDTO;
import io.github.dan7arievlis.libraryapi.controller.dto.mappers.AuthorMapper;
import io.github.dan7arievlis.libraryapi.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("authors")
@RequiredArgsConstructor
@Tag(name = "Autores")
public class AuthorController implements GenericController {

    private final AuthorService service;
    private final AuthorMapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Salvar", description = "Cadastrar novo autor.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso."),
            @ApiResponse(responseCode = "402", description = "Erro de validação."),
            @ApiResponse(responseCode = "409", description = "Author já cadastrado.")
    })
    public ResponseEntity<Void> save(@RequestBody @Valid AuthorRequestDTO dto) {
        var author = mapper.RequestToEntity(dto);
        service.save(author);
        URI uri = generateHeaderLocation(author.getId());
        return ResponseEntity.created(uri).build();
    }

    @GetMapping({"{id}"})
    @PreAuthorize("hasAnyRole('MANAGER', 'OPERATOR')")
    @Operation(summary = "Obter detalhes", description = "Retorna os dados do autor pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autor encontrado."),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado.")
    })
    public ResponseEntity<AuthorResponseDTO> getDetails(@PathVariable String id) {
        var authorId = UUID.fromString(id);
        return service.getById(authorId)
                .map(author -> ResponseEntity.ok(mapper.toResponse(author)))
                .orElseThrow(() -> new EntityNotFoundException("Author not found."));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Deletar", description = "Deleta um autor existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado."),
            @ApiResponse(responseCode = "400", description = "Author possui livro cadastrado.")
    })
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
    @PreAuthorize("hasAnyRole('MANAGER', 'OPERATOR')")
    @Operation(summary = "Pesquisar", description = "Realiza pesquisa de autores por parâmetros.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso.")
    })
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
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Atualizar", description = "Atualiza um autor existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado."),
            @ApiResponse(responseCode = "409", description = "Author já cadastrado.")
    })
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
