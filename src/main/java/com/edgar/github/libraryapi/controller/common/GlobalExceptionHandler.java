package com.edgar.github.libraryapi.controller.common;

import com.edgar.github.libraryapi.dto.autor.ErroDTO;
import com.edgar.github.libraryapi.exceptions.OperacaoNaoPermitida;
import com.edgar.github.libraryapi.exceptions.RegistroDuplicado;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.edgar.github.libraryapi.dto.autor.ErroResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErroResponseDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException err) {
        List<FieldError> fieldErrors = err.getFieldErrors();
        List<ErroDTO> listaErros = fieldErrors.stream()
                .map(fiel -> new ErroDTO(fiel.getField(), fiel.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ErroResponseDTO(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Error de validação",
                listaErros
        );
    }

    @ExceptionHandler(RegistroDuplicado.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResponseDTO handleRegistroDuplicado(RegistroDuplicado err) {
        return ErroResponseDTO.conflito(err.getMessage());
    }

    @ExceptionHandler(OperacaoNaoPermitida.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResponseDTO handleOperacaoNaoPermitida(OperacaoNaoPermitida err) {
        return ErroResponseDTO.respostaPadrao(err.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResponseDTO handleErrosNaoTratados(RuntimeException err) {
        return new ErroResponseDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro inesperado. Entre em contato com o suporte",
                List.of()
        );
    }
}
