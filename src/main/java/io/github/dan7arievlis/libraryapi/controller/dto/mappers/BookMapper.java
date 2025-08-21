package io.github.dan7arievlis.libraryapi.controller.dto.mappers;

import io.github.dan7arievlis.libraryapi.controller.dto.BookRequestDTO;
import io.github.dan7arievlis.libraryapi.controller.dto.BookResponseDTO;
import io.github.dan7arievlis.libraryapi.model.Book;
import io.github.dan7arievlis.libraryapi.repository.AuthorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AuthorMapper.class)
public abstract class BookMapper {

    @Autowired
    AuthorRepository authorRepository;

    @Mapping(target = "author", expression = "java( authorRepository.findById(dto.authorId()).orElse(null) )")
    public abstract Book RequestToEntity(BookRequestDTO dto);

    public abstract BookResponseDTO toResponse(Book book);
}
