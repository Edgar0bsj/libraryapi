package com.edgar.github.libraryapi.controller;

import com.edgar.github.libraryapi.dto.autor.AutorDTO;
import com.edgar.github.libraryapi.dto.autor.AutorResponseDTO;
import com.edgar.github.libraryapi.dto.autor.ErroResponseDTO;
import com.edgar.github.libraryapi.exceptions.RegistroDuplicado;
import com.edgar.github.libraryapi.mappers.AutorMapper;
import com.edgar.github.libraryapi.model.Autor;
import com.edgar.github.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
public class AutorController implements GenericController {

    private final AutorService service;
    private final AutorMapper mapper;


    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO dto) {


        Autor entity = this.mapper.toEntity(dto);
        service.salvar(entity);

        URI location = gerarHandlerLocation(entity.getId());

        return ResponseEntity.created(location).build();


    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id) {
        UUID autorId = UUID.fromString(id);
        Optional<Autor> autorOptional = this.service.obterPorId(autorId);

        if (autorOptional.isPresent()) {
            Autor autorEntity = autorOptional.get();
            AutorDTO dto = this.mapper.toDTO(autorEntity);
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") String id) {

        UUID autorId = UUID.fromString(id);
        Optional<Autor> autorOptional = this.service.obterPorId(autorId);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        this.service.deletar(autorOptional.get());

        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public ResponseEntity<List<AutorResponseDTO>> pesquisa(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade
    ) {
        List<Autor> resultado = this.service.pesquisaByExample(nome, nacionalidade);
        List<AutorResponseDTO> lista = resultado.stream()
                .map(autor -> new AutorResponseDTO(
                        autor.getId(),
                        autor.getNome(),
                        autor.getDataNascimento(),
                        autor.getNacionalidade())
                ).collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(
            @PathVariable("id") String id,
            @RequestBody AutorResponseDTO autorDto
    ) {

        UUID autorId = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(autorId);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Autor autor = autorOptional.get();
        autor.setNome(autorDto.nome());
        autor.setNacionalidade(autorDto.nacionalidade());
        autor.setDataNascimento(autorDto.dataNascimento());

        this.service.atualizar(autor);

        return ResponseEntity.noContent().build();

    }
}