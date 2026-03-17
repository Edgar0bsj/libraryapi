package com.edgar.github.libraryapi.controller;

import com.edgar.github.libraryapi.dto.AutorDTO;
import com.edgar.github.libraryapi.model.Autor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autores")
public class AutorController {

    @PostMapping
    public ResponseEntity salvar(@RequestBody AutorDTO autor){

        Autor autorEntity = autor.mapearParaAutor();

        return new ResponseEntity("Teste: " + autor, HttpStatus.CREATED);
    }
}