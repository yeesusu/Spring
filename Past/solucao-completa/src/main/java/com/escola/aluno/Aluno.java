package com.escola.aluno;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * PADRÃO BUILDER: Aluno.builder().nome("Ana").email("a@a.com")...build()
 * (gerado pelo Lombok com @Builder).
 */
@Entity
@Table(name = "alunos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String matricula;

    @Column(nullable = false)
    private String curso;

    @Column(name = "data_cadastro", nullable = false)
    @Builder.Default
    private LocalDate dataCadastro = LocalDate.now();
}
