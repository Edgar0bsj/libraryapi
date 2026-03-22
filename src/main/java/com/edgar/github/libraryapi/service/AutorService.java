package com.edgar.github.libraryapi.service;

import com.edgar.github.libraryapi.exceptions.OperacaoNaoPermitida;
import com.edgar.github.libraryapi.model.Autor;
import com.edgar.github.libraryapi.repository.AutorRepository;
import com.edgar.github.libraryapi.repository.LivroRepository;
import com.edgar.github.libraryapi.validador.AutorValidador;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {
    private final AutorRepository repository;
    private final LivroRepository livroRepository;
    private final AutorValidador validador;

    public AutorService(AutorRepository repository, AutorValidador validador, LivroRepository livroRepository) {
        this.repository = repository;
        this.validador = validador;
        this.livroRepository = livroRepository;
    }

    public Autor salvar(Autor autor){
        this.validador.validar(autor);
        return repository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id){

        return repository.findById(id);
    }

    public void deletar(Autor autor){
        if (possuirLivro(autor)){
            throw new OperacaoNaoPermitida("Autor possui livros cadastrados!");
        }
        this.repository.delete(autor);
    }

    public List<Autor> pesquisa(String nome, String nacionalidade){

        if (nome != null && nacionalidade != null){
            return this.repository.findByNomeAndNacionalidade(nome,nacionalidade);
        }
        if (nome != null) {
            return this.repository.findByNome(nome);
        }
        if (nacionalidade != null){
            return this.repository.findByNacionalidade(nacionalidade);
        }
        return this.repository.findAll();

    }

    public void atualizar(Autor autor){
        if (autor.getId() == null){
            throw new IllegalArgumentException("Para atualizar é necessário que o Autor já exista no banco de dados");
        }
        this.validador.validar(autor);
        this.repository.save(autor);
    }

    public boolean possuirLivro(Autor autor){
        return this.livroRepository.existsByAutor(autor);
    }
}