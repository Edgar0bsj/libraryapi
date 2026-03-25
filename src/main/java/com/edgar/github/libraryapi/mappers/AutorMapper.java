package com.edgar.github.libraryapi.mappers;

import com.edgar.github.libraryapi.dto.autor.AutorDTO;
import com.edgar.github.libraryapi.model.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "dataNascimento", target = "dataNascimento")
    @Mapping(source = "nacionalidade", target = "nacionalidade")
    Autor toEntity(AutorDTO autor);

    AutorDTO toDTO(Autor autor);

}