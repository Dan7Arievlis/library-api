package io.github.dan7arievlis.libraryapi.controller.dto.mappers;

import io.github.dan7arievlis.libraryapi.controller.dto.UserRequestDTO;
import io.github.dan7arievlis.libraryapi.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User RequestToEntity(UserRequestDTO dto);

    UserRequestDTO toRequest(User user);
}
