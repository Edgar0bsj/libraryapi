package com.edgar.github.libraryapi.controller;

import com.edgar.github.libraryapi.dto.AutorDTO;
import com.edgar.github.libraryapi.dto.AutorResponseDTO;
import com.edgar.github.libraryapi.model.Autor;
import com.edgar.github.libraryapi.service.AutorService;
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
public class AutorController {

    private AutorService service;

    public AutorController(AutorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody AutorDTO autor){

        Autor autorEntity = autor.mapearParaAutor();
        service.salvar(autorEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autorEntity.getId())
                .toUri();


        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorResponseDTO> obterDetalhes(@PathVariable("id") String id) {
        UUID autorId = UUID.fromString(id);
        Optional<Autor> autorOptional = this.service.obterPorId(autorId);

        if (autorOptional.isPresent()) {
            Autor autorEntity = autorOptional.get();
            AutorResponseDTO dto = new AutorResponseDTO(
                    autorEntity.getId(),
                    autorEntity.getNome(),
                    autorEntity.getDataNascimento(),
                    autorEntity.getNacionalidade()
            );
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") String id){

        UUID autorId = UUID.fromString(id);
        Optional<Autor> autorOptional = this.service.obterPorId(autorId);

        if (autorOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        this.service.deletar(autorOptional.get());

        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public ResponseEntity<List<AutorResponseDTO>> pesquisa(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade
    ){
        List<Autor> resultado = this.service.pesquisa(nome,nacionalidade);
        List<AutorResponseDTO> lista = resultado.stream()
                .map(autor-> new AutorResponseDTO(
                        autor.getId(),
                        autor.getNome(),
                        autor.getDataNascimento(),
                        autor.getNacionalidade())
                ).collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable("id") String id,
            @RequestBody AutorResponseDTO autorDto
    ){
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