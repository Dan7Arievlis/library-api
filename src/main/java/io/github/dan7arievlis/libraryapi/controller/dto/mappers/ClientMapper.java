package io.github.dan7arievlis.libraryapi.controller.dto.mappers;

import io.github.dan7arievlis.libraryapi.controller.dto.ClientRequestDTO;
import io.github.dan7arievlis.libraryapi.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client requestToEntity(ClientRequestDTO dto);

    ClientRequestDTO toRequest(Client client);
}
