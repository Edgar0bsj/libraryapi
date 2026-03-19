package com.edgar.github.libraryapi.validador;

import com.edgar.github.libraryapi.exceptions.RegistroDuplicado;
import com.edgar.github.libraryapi.model.Autor;
import com.edgar.github.libraryapi.repository.AutorRepository;

import java.util.Optional;

public class AutorValidador {

    private AutorRepository repository;

    public AutorValidador(AutorRepository repository) {
        this.repository = repository;
    }

    public void validar(Autor autor){
        if(existeAutorCadastrado(autor)){
            throw new RegistroDuplicado("Autor já existe!");
        }
    }

    public boolean existeAutorCadastrado(Autor autor){
        Optional<Autor> autorEncontrado = this.repository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(),autor.getDataNascimento(), autor.getNacionalidade()
        );

        if(autor.getId() == null){
            return autorEncontrado.isPresent();
        }

        return !autor.getId().equals(autorEncontrado.get().getId()) && autorEncontrado.isPresent();
    }
}
