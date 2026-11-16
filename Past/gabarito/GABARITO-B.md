# Gabarito do Cenário B – erros plantados

**Não leia antes de tentar!**

| # | Sintoma | Causa | Correção |
|---|---|---|---|
| 1 | `mvn package` falha com "cannot find symbol: class Email" em `AlunoDtos` | Falta `import jakarta.validation.constraints.Email;` | Adicionar o import |
| 2 | App sobe e cai: `Driver ... org.postgres.Driver ... not found` | Typo no `application.yml` | `org.postgresql.Driver` (ou remover a linha: o Spring detecta sozinho) |
| 3 | App cai com `No property 'ignorecase' found for type 'Aluno'` | Nome do método do repositório digitado errado | `findByNomeContainingIgnoreCase` (**IgnoreCase** com C maiúsculo) em `AlunoRepository` e `AlunoService` |
| 4 | Todas as rotas `/alunos` retornam 404 | `AlunoController` sem `@RestController` | Adicionar `@RestController` e conferir o import |
| 5 | POST com nome vazio ou e-mail inválido é aceito | Falta `@Valid` antes de `@RequestBody` (2 métodos) | `@Valid @RequestBody ...` |

## Dicas gerais de diagnóstico
- O **primeiro** erro do log é o importante; leia de cima para baixo até "Caused by".
- 404 em tudo = o Spring nem registrou o controller (anotação faltando) ou o caminho está errado.
- 400/500 diferente do esperado = olhe o stack trace no terminal, não só a resposta do Swagger.
