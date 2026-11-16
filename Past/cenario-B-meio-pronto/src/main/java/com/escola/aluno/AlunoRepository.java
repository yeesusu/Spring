package com.escola.aluno;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    // Spring Data gera o SQL a partir do NOME do método
    Page<Aluno> findByNomeContainingIgnorecase(String nome, Pageable pageable);

    boolean existsByEmail(String email);

    interface CursoTotal {
        String getCurso();

        Long getTotal();
    }

    @Query("select a.curso as curso, count(a) as total from Aluno a group by a.curso order by a.curso")
    List<CursoTotal> totalPorCurso();
}
