# Cenário B – "Meio pronto"

**Situação:** o professor entrega CRUD de alunos "quase funcionando", sem segurança, sem Kafka, sem Flyway, sem Docker, sem AOP, sem padrões, sem README — **e com erros escondidos**. Objetivo: fazer rodar e depois acrescentar camadas na ordem de melhor custo-benefício.

> Os erros plantados estão listados em `../gabarito/GABARITO-B.md`. **Só abra depois de tentar sozinho** — no dia da prova ninguém lhe entrega a lista.

## Fase 1 – Fazer rodar (meta: 30–40 min)

1. `mvn clean package -DskipTests` — leia o PRIMEIRO erro (os outros costumam ser consequência).
2. Suba o PostgreSQL (você ainda vai escrever o `docker-compose.yml`, então comece por um comando rápido):
   ```bash
   docker run -d --name db -e POSTGRES_DB=escola -e POSTGRES_USER=app -e POSTGRES_PASSWORD=app123 -p 5432:5432 postgres:16
   ```
3. `mvn spring-boot:run` — leia a mensagem final ("Caused by: ...") de cada falha.
4. Teste com Swagger/curl. Se tudo responde 404, pense em **anotações da classe controller**.
5. Teste um POST com JSON inválido: se salvar mesmo assim, as validações não estão ativas.

## Fase 2 – Acrescentar (ordem recomendada, copiando e adaptando de `../solucao-completa/`)

| Ordem | Tarefa | Critério (pontos) | Tempo |
|---|---|---|---|
| 1 | Flyway: dependências + `V1` + `ddl-auto: validate` | Banco (0,5) | 20 min |
| 2 | Dockerfile + docker-compose (db) | Docker (0,5) | 20 min |
| 3 | AOP (`LogAspect`) | POA (0,5) | 15 min |
| 4 | 3 padrões: Builder já existe; adicionar Strategy/Factory (export) | Padrões (0,5) | 30 min |
| 5 | Segurança JWT + `@PreAuthorize` | Autenticação (0,5) | 45 min |
| 6 | Kafka produtor + consumidor | Kafka (0,5) | 30 min |
| 7 | README + git + push | Arquitetura (0,7+) | 20 min |

Se faltar tempo, **não** comece o item 5 ou 6 sem ter terminado 1–4 e o README: eles são mais rápidos e valem tanto quanto.
