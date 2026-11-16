package com.escola.aluno;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// TODO: criar AlunoRepository, AlunoService, DTOs com validação
// TODO: CRUD completo (GET, POST, PUT, DELETE)
@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @GetMapping
    public List<Aluno> listar() {
        // TODO: buscar no banco
        return null;
    }
}
