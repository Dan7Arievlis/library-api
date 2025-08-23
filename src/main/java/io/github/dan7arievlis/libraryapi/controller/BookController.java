package io.github.dan7arievlis.libraryapi.controller;

import io.github.dan7arievlis.libraryapi.controller.dto.BookRequestDTO;
import io.github.dan7arievlis.libraryapi.controller.dto.BookResponseDTO;
import io.github.dan7arievlis.libraryapi.controller.dto.mappers.BookMapper;
import io.github.dan7arievlis.libraryapi.model.Book;
import io.github.dan7arievlis.libraryapi.model.enums.BookGenre;
import io.github.dan7arievlis.libraryapi.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
//@PreAuthorize("hasAnyRole('OPERATOR', 'MANAGER')")
public class BookController implements GenericController {

    private final BookService service;
    private final BookMapper mapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('OPERATOR', 'MANAGER')")
    public ResponseEntity<Void> save(@RequestBody @Valid BookRequestDTO dto) {
        Book book = mapper.RequestToEntity(dto);
        service.save(book);
        URI uri = generateHeaderLocation(book.getId());
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERATOR', 'MANAGER')")
    public ResponseEntity<BookResponseDTO> findById(@PathVariable String id) {
        return service.findById(UUID.fromString(id))
                .map(book -> ResponseEntity.ok(mapper.toResponse(book)))
                .orElseThrow( () -> new EntityNotFoundException("Book not found."));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERATOR', 'MANAGER')")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return service.findById(UUID.fromString(id))
                .map( book -> {
                    service.delete(book);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow( () -> new EntityNotFoundException("Book not found.") );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERATOR', 'MANAGER')")
    public ResponseEntity<Page<BookResponseDTO>> search(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "title", required = false)
            String title,
            @RequestParam(value = "author-name", required = false)
            String authorName,
            @RequestParam(value = "genre", required = false)
            BookGenre genre,
            @RequestParam(value = "publish-year", required = false)
            Integer publishYear,
            @RequestParam(value = "page", defaultValue = "0")
            Integer page,
            @RequestParam(value = "page-size", defaultValue = "10")
            Integer pageSize) {
        Page<BookResponseDTO> result = service.search(isbn, title, authorName, genre, publishYear, page, pageSize).map(mapper::toResponse);

        return ResponseEntity.ok(result);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERATOR', 'MANAGER')")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody @Valid BookRequestDTO dto) {
        return service.findById(UUID.fromString(id))
                .map(book -> {
                    Book aux = mapper.RequestToEntity(dto);
                    book.setIsbn(aux.getIsbn());
                    book.setTitle(aux.getTitle());
                    book.setPrice(aux.getPrice());
                    book.setAuthor(aux.getAuthor());
                    book.setGenre(aux.getGenre());
                    book.setPublishDate(aux.getPublishDate());

                    service.update(book);

                    return ResponseEntity.noContent().build();
                })
                .orElseThrow( () -> new EntityNotFoundException("Book not found."));
    }
}
