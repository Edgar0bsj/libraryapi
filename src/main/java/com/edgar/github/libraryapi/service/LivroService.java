package com.edgar.github.libraryapi.service;

import com.edgar.github.libraryapi.model.Livro;
import com.edgar.github.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;

    public Livro salvar(Livro livro) {

       return livroRepository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id) {
    return livroRepository.findById(id);
    }
}
