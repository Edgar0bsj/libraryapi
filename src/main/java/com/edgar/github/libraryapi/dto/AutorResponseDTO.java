package com.edgar.github.libraryapi.dto;

import com.edgar.github.libraryapi.model.Autor;

import java.time.LocalDate;
import java.util.UUID;

public record AutorResponseDTO(
        UUID id,
        String nome,
        LocalDate dataNascimento,
        String nacionalidade
) {}