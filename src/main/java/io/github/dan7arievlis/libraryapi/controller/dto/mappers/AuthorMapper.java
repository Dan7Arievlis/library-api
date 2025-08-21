package io.github.dan7arievlis.libraryapi.controller.dto.mappers;

import io.github.dan7arievlis.libraryapi.controller.dto.AuthorResponseDTO;
import io.github.dan7arievlis.libraryapi.controller.dto.AuthorRequestDTO;
import io.github.dan7arievlis.libraryapi.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "birthDate", target = "birthDate")
    @Mapping(source = "nationality", target = "nationality")
    Author RequestToEntity(AuthorRequestDTO dto);

    Author ResponseToEntity(AuthorResponseDTO dto);

    AuthorRequestDTO toRequest(Author author);

    AuthorResponseDTO toResponse(Author author);
}
