package com.edgar.github.libraryapi.service;

import com.edgar.github.libraryapi.model.Autor;
import com.edgar.github.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {
    private AutorRepository repository;

    public AutorService(AutorRepository repository) {
        this.repository = repository;
    }

    public Autor salvar(Autor autor){
        return repository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id){
        return repository.findById(id);
    }

    public void deletar(Autor autor){
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
        this.repository.save(autor);
    }
}
