package com.edgar.github.libraryapi.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErroResponseDTO(int Status, String mensagem, List<ErroDTO> erros) {

    public static ErroResponseDTO respostaPadrao(String mensagem){
        return new ErroResponseDTO(HttpStatus.BAD_REQUEST.value(), mensagem, List.of());
    }

    public static ErroResponseDTO conflito(String mensagem){
        return new ErroResponseDTO(HttpStatus.CONFLICT.value(), mensagem, List.of());
    }

}
