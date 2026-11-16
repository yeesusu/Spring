package com.escola.event;

/** Evento "student.created": um aluno acabou de ser cadastrado. */
public record AlunoCriadoEvent(Long id, String nome, String email) {
}
