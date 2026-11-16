package com.escola.relatorio;

import com.escola.aluno.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Relatório geral (requisito "outras funcionalidades"). */
@RestController
@RequestMapping("/relatorios")
@RequiredArgsConstructor
public class RelatorioController {

    private final AlunoRepository alunos;

    public record CursoTotalDto(String curso, Long total) {
    }

    @GetMapping("/alunos-por-curso")
    @PreAuthorize("hasAnyRole('ADMIN','PROFESSOR')")
    public List<CursoTotalDto> alunosPorCurso() {
        return alunos.totalPorCurso().stream()
                .map(t -> new CursoTotalDto(t.getCurso(), t.getTotal()))
                .toList();
    }
}
