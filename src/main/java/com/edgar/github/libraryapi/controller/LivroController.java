package com.edgar.github.libraryapi.controller;

import com.edgar.github.libraryapi.dto.autor.ErroResponseDTO;
import com.edgar.github.libraryapi.dto.livro.CadastroLivroDTO;
import com.edgar.github.libraryapi.dto.livro.ResultadoPesquisaLivroDTO;
import com.edgar.github.libraryapi.exceptions.RegistroDuplicado;
import com.edgar.github.libraryapi.mappers.LivroMapper;
import com.edgar.github.libraryapi.model.Livro;
import com.edgar.github.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroMapper mapper;
    private final LivroService service;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO livroDTO) {

        Livro livro = mapper.toEntity(livroDTO);
        service.salvar(livro);

        URI location = gerarHandlerLocation(livro.getId());

        return ResponseEntity.created(location).build();


    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(
            @PathVariable("id") String id
    ) {
        System.out.println("BATEU AQUI>>>>>>>>>>>>>>>>>>>" + id);
        Optional<Livro> entityOptional = service.obterPorId(UUID.fromString(id));

        if (entityOptional.isPresent()) {
            Livro livro = entityOptional.get();
            ResultadoPesquisaLivroDTO livroDTO = mapper.toDTO(livro);
            return ResponseEntity.ok(livroDTO);

        }
        return ResponseEntity.notFound().build();
    }
}
