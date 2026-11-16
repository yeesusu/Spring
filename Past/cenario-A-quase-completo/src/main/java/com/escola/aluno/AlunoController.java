package com.escola.aluno;

import com.escola.export.ExportFactory;
import com.escola.export.ExportStrategy;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService service;
    private final ExportFactory exportFactory;

    // GET /alunos?page=0&size=10&busca=ana
    @GetMapping
    public Page<AlunoDtos.Response> listar(@RequestParam(required = false) String busca, Pageable pageable) {
        return service.listar(busca, pageable).map(AlunoDtos.Response::from);
    }

    // GET /alunos/export?formato=csv|pdf   (declarado ANTES de /{id})
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportar(@RequestParam(defaultValue = "csv") String formato) {
        ExportStrategy estrategia = exportFactory.obter(formato); // PADRÃO STRATEGY + FACTORY
        byte[] arquivo = estrategia.exportar(service.listarTodos());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=alunos." + estrategia.formato())
                .contentType(MediaType.parseMediaType(estrategia.contentType()))
                .body(arquivo);
    }

    @GetMapping("/{id}")
    public AlunoDtos.Response buscar(@PathVariable Long id) {
        return AlunoDtos.Response.from(service.buscar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN','PROFESSOR')")
    public AlunoDtos.Response criar(@Valid @RequestBody AlunoDtos.Request req) {
        return AlunoDtos.Response.from(service.criar(req));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PROFESSOR')")
    public AlunoDtos.Response atualizar(@PathVariable Long id, @Valid @RequestBody AlunoDtos.Request req) {
        return AlunoDtos.Response.from(service.atualizar(id, req));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void remover(@PathVariable Long id) {
        service.remover(id);
    }
}
