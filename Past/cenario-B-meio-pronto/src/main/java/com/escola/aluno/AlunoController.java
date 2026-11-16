package com.escola.aluno;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/alunos")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService service;

    @GetMapping
    public Page<AlunoDtos.Response> listar(@RequestParam(required = false) String busca, Pageable pageable) {
        return service.listar(busca, pageable).map(AlunoDtos.Response::from);
    }

    @GetMapping("/{id}")
    public AlunoDtos.Response buscar(@PathVariable Long id) {
        return AlunoDtos.Response.from(service.buscar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlunoDtos.Response criar(@RequestBody AlunoDtos.Request req) {
        return AlunoDtos.Response.from(service.criar(req));
    }

    @PutMapping("/{id}")
    public AlunoDtos.Response atualizar(@PathVariable Long id, @RequestBody AlunoDtos.Request req) {
        return AlunoDtos.Response.from(service.atualizar(id, req));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.remover(id);
    }
}
