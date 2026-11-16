# Cenário C – "Esqueleto"

**Situação:** o professor entrega quase nada: `pom.xml` incompleto, uma classe `Aluno` sem JPA, um controller vazio. **Não tente fazer tudo.** Faça um **corte vertical mínimo que funcione de ponta a ponta** e documente o resto como "projetado, não implementado".

## Meta realista (Fatia mínima)

Banco + Flyway + Docker rodando → CRUD de Aluno com DTO e validação → login JWT → AOP → 3 padrões → Kafka (um evento) → README honesto.

## Passo a passo

| Passo | Tarefa | Tempo de treino |
|---|---|---|
| 1 | Corrigir o `pom.xml` (use o da solução como referência) até `mvn clean package` compilar | 20 min |
| 2 | `docker-compose.yml` com PostgreSQL + `application.yml` + Flyway `V1` | 25 min |
| 3 | `Aluno` como entidade, `AlunoRepository`, `AlunoService`, DTOs, `AlunoController` CRUD | 50 min |
| 4 | `GlobalExceptionHandler` + validações (`@Valid`) | 15 min |
| 5 | AOP `LogAspect` | 10 min |
| 6 | Padrões: Builder (Lombok) + Strategy/Factory de exportação CSV | 25 min |
| 7 | Segurança JWT (User, JwtService, filtro, SecurityConfig, login) | 50 min |
| 8 | Kafka (evento `student.created`) | 25 min |
| 9 | Dockerfile, README, git, push | 25 min |

Total de treino: ≈ 4 h. No dia, **pare onde o tempo acabar**, mas deixe sempre o projeto **compilando e commitado**: um sistema pequeno que roda vale mais do que um grande quebrado.

## Regra de ouro
A cada passo: compile → rode → `git commit`. Nunca avance com o projeto quebrado.

## O que dizer no README sobre o que não fez
> "Módulos de professores, turmas e notas foram projetados (ver diagrama) mas não implementados por limite de tempo; a arquitetura modular permite adicioná-los copiando o padrão do módulo `aluno`."

Honestidade clara pontua melhor do que fingir.
