package com.edgar.github.libraryapi.dto;

import com.edgar.github.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AutorDTO(
        @NotBlank(message = "Campo obrigatório")
        String nome,
        @NotNull(message = "Campo obrigatório")
        LocalDate dataNascimento,
        @NotBlank(message = "Campo obrigatório")
        String nacionalidade
) {
    public Autor mapearParaAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);

        return autor;
    }
}