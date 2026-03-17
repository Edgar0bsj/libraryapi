package com.edgar.github.libraryapi.controller;

import com.edgar.github.libraryapi.dto.AutorDTO;
import com.edgar.github.libraryapi.model.Autor;
import com.edgar.github.libraryapi.service.AutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URL;

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
}