package com.edgar.github.libraryapi.controller;

import com.edgar.github.libraryapi.dto.autor.ErroResponseDTO;
import com.edgar.github.libraryapi.dto.livro.CadastroLivroDTO;
import com.edgar.github.libraryapi.exceptions.RegistroDuplicado;
import com.edgar.github.libraryapi.mappers.LivroMapper;
import com.edgar.github.libraryapi.model.Livro;
import com.edgar.github.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroMapper mapper;
    private final LivroService service;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO livroDTO){
        try{
            Livro livro = mapper.toEntity(livroDTO);
            service.salvar(livro);

            return ResponseEntity.ok(livro);
        }catch (RegistroDuplicado err) {
            var erroDto = ErroResponseDTO.conflito(err.getMessage());
            return ResponseEntity.status(erroDto.Status()).body(erroDto);
        }

    }
}
