package com.escola.aluno;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public final class AlunoDtos {

    private AlunoDtos() {
    }

    /** Entrada (POST/PUT). As anotações são as VALIDAÇÕES. */
    public record Request(@NotBlank(message = "nome é obrigatório") String nome,
                          @NotBlank @Email(message = "e-mail inválido") String email,
                          @NotBlank(message = "matrícula é obrigatória") String matricula,
                          @NotBlank(message = "curso é obrigatório") String curso) {
    }

    /** Saída. */
    public record Response(Long id, String nome, String email, String matricula,
                           String curso, LocalDate dataCadastro) {
        public static Response from(Aluno a) {
            return new Response(a.getId(), a.getNome(), a.getEmail(), a.getMatricula(),
                    a.getCurso(), a.getDataCadastro());
        }
    }
}
