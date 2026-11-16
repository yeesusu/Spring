package com.escola.aluno;

import com.escola.event.AlunoCriadoEvent;
import com.escola.exception.BusinessException;
import com.escola.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Regras de negócio. Controller chama Service; Service chama Repository. */
@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository repo;
    private final ApplicationEventPublisher eventos;

    @Transactional(readOnly = true)
    public Page<Aluno> listar(String busca, Pageable pageable) {
        if (busca == null || busca.isBlank()) {
            return repo.findAll(pageable);
        }
        return repo.findByNomeContainingIgnoreCase(busca, pageable);
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
        Aluno salvo = repo.save(Aluno.builder()          // PADRÃO BUILDER
                .nome(r.nome())
                .email(r.email())
                .matricula(r.matricula())
                .curso(r.curso())
                .build());
        // PADRÃO OBSERVER: avisa quem estiver ouvindo (Kafka, notificação...)
        eventos.publishEvent(new AlunoCriadoEvent(salvo.getId(), salvo.getNome(), salvo.getEmail()));
        return salvo;
    }

    @Transactional
    public Aluno atualizar(Long id, AlunoDtos.Request r) {
        Aluno a = buscar(id);
        a.setNome(r.nome());
        a.setEmail(r.email());
        a.setMatricula(r.matricula());
        a.setCurso(r.curso());
        return a; // @Transactional salva sozinho ao final
    }

    @Transactional
    public void remover(Long id) {
        repo.delete(buscar(id)); // LGPD: direito de exclusão
    }
}
