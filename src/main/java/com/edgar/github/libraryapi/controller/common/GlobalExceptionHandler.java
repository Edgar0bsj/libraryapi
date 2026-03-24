package com.edgar.github.libraryapi.controller.common;

import com.edgar.github.libraryapi.dto.autor.ErroDTO;
import org.springframework.http.HttpStatus;
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
    public ErroResponseDTO hancleMethodArgumentNotValidException(MethodArgumentNotValidException err){
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
}
