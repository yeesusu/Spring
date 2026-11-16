package com.escola.aluno;

import lombok.Getter;
import lombok.Setter;

// TODO: transformar em entidade JPA (@Entity, @Table, @Id, @GeneratedValue, @Column...)
@Getter
@Setter
public class Aluno {
    private Long id;
    private String nome;
    private String email;
    private String matricula;
    private String curso;
}
