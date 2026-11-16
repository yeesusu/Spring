package com.escola.aluno;

import com.escola.exception.BusinessException;
import com.escola.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository repo;

    @Transactional(readOnly = true)
    public Page<Aluno> listar(String busca, Pageable pageable) {
        if (busca == null || busca.isBlank()) {
            return repo.findAll(pageable);
        }
        return repo.findByNomeContainingIgnorecase(busca, pageable);
    }

    @Transactional(readOnly = true)
    public List<Aluno> listarTodos() {
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public Aluno buscar(Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Aluno " + id + " não encontrado"));
    }

    @Transactional
    public Aluno criar(AlunoDtos.Request r) {
        if (repo.existsByEmail(r.email())) {
            throw new BusinessException("E-mail já cadastrado");
        }
        return repo.save(Aluno.builder()
                .nome(r.nome())
                .email(r.email())
                .matricula(r.matricula())
                .curso(r.curso())
                .build());
    }

    @Transactional
    public Aluno atualizar(Long id, AlunoDtos.Request r) {
        Aluno a = buscar(id);
        a.setNome(r.nome());
        a.setEmail(r.email());
        a.setMatricula(r.matricula());
        a.setCurso(r.curso());
        return a;
    }

    @Transactional
    public void remover(Long id) {
        repo.delete(buscar(id));
    }
}
