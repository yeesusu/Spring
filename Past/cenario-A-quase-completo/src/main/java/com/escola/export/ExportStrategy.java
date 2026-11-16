package com.escola.export;

import com.escola.aluno.Aluno;

import java.util.List;

/** PADRÃO STRATEGY: cada formato de exportação é uma "estratégia" intercambiável. */
public interface ExportStrategy {
    String formato();      // "csv", "pdf"

    String contentType();  // "text/csv", "application/pdf"

    byte[] exportar(List<Aluno> alunos);
}
