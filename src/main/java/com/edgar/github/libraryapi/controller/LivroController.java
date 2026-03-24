package com.edgar.github.libraryapi.controller;

import com.edgar.github.libraryapi.dto.autor.ErroResponseDTO;
import com.edgar.github.libraryapi.dto.livro.CadastroLivroDTO;
import com.edgar.github.libraryapi.exceptions.RegistroDuplicado;
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

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO livroDTO){
        try{

            return ResponseEntity.ok(livroDTO);
        }catch (RegistroDuplicado err) {
            var erroDto = ErroResponseDTO.conflito(err.getMessage());
            return ResponseEntity.status(erroDto.Status()).body(erroDto);
        }

    }
}
