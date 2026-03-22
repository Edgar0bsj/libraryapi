package com.edgar.github.libraryapi.service;

import com.edgar.github.libraryapi.exceptions.OperacaoNaoPermitida;
import com.edgar.github.libraryapi.model.Autor;
import com.edgar.github.libraryapi.repository.AutorRepository;
import com.edgar.github.libraryapi.repository.LivroRepository;
import com.edgar.github.libraryapi.validador.AutorValidador;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {
    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;
    private final AutorValidador validador;

    public Autor salvar(Autor autor){
        this.validador.validar(autor);
        return autorRepository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id){

        return autorRepository.findById(id);
    }

    public void deletar(Autor autor){
        if (possuirLivro(autor)){
            throw new OperacaoNaoPermitida("Autor possui livros cadastrados!");
        }
        this.autorRepository.delete(autor);
    }

    public List<Autor> pesquisa(String nome, String nacionalidade){

        if (nome != null && nacionalidade != null){
            return this.autorRepository.findByNomeAndNacionalidade(nome,nacionalidade);
        }
        if (nome != null) {
            return this.autorRepository.findByNome(nome);
        }
        if (nacionalidade != null){
            return this.autorRepository.findByNacionalidade(nacionalidade);
        }
        return this.autorRepository.findAll();

    }

    public void atualizar(Autor autor){
        if (autor.getId() == null){
            throw new IllegalArgumentException("Para atualizar é necessário que o Autor já exista no banco de dados");
        }
        this.validador.validar(autor);
        this.autorRepository.save(autor);
    }

    public boolean possuirLivro(Autor autor){
        return this.livroRepository.existsByAutor(autor);
    }
}