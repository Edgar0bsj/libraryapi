package com.edgar.github.libraryapi.dto.livro;

import com.edgar.github.libraryapi.model.LivroGenero;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CadastroLivroDTO(
        String isbn,
        String titulo,
        LocalDate dataPublicacao,
        LivroGenero genero,
        BigDecimal preco,
        UUID idAutor
) {
}
