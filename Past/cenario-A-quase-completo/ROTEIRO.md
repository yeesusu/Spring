# Cenário A – "Quase completo"

**Situação:** o professor entrega um sistema que já tem login JWT, CRUD de alunos, Kafka, Flyway (V1), exportação e notificações. **Faltam peças** (veja abaixo). Objetivo: tirar a nota máxima completando o que falta e deixando cada critério com evidência visível.

## O que está faltando neste pacote (procure você mesmo antes de olhar a solução!)

| # | Falta | Critério afetado | Onde copiar (em `../solucao-completa/`) |
|---|---|---|---|
| 1 | Pacote `aop/` inteiro (POA) | Uso de POA (0,5) + Desempenho | `src/main/java/com/escola/aop/` |
| 2 | Migração de índices | Banco/Flyway, Desempenho | `src/main/resources/db/migration/V2__add_indexes.sql` |
| 3 | `Dockerfile` e `docker-compose.yml` | Uso de Docker (0,5) | raiz |
| 4 | `README.md` (arquitetura, mapa dos critérios, padrões) | Modelo arquitetural (0,7), Padrões (0,5) | raiz (reescreva com suas palavras) |
| 5 | Teste unitário | Qualidade (1,0) | `src/test/java/com/escola/export/ExportTest.java` |
| 6 | `application-h2.yml` existe, mas você precisa **saber usá-lo** | plano B sem Docker | — |

## Passo a passo (meta: 1h30 no treino; no dia, o que o tempo permitir, nesta ordem)

1. **Checklist de 10 min:** `mvn clean package -DskipTests` → depois `mvn spring-boot:run -Dspring-boot.run.profiles=h2`. Abra `http://localhost:8080/swagger-ui.html`.
2. Faça login com `admin@escola.com / admin123`, clique em *Authorize* no Swagger e teste `POST /alunos`.
3. Procure no código: **há quantos padrões de projeto?** (Builder, Strategy, Factory, Observer). Anote classe + motivo.
4. Crie o pacote `aop/` (copie `LogAspect`; escreva o `AuditAspect` sozinho se der tempo). Reinicie e veja os logs `[AOP]`.
5. Adicione `V2__add_indexes.sql`. Reinicie: o Flyway aplica sozinho (log `Migrating schema ... to version "2"`).
6. Crie `Dockerfile` + `docker-compose.yml`. Teste `docker compose up -d --build`.
7. Escreva o README (use o da solução como molde; **troque pelos nomes reais das classes do código do professor**).
8. `git init`, commits pequenos (`feat:`, `fix:`, `docs:`), push para o GitHub.
9. Ensaie o `demo.sh` (está em `../solucao-completa/demo.sh`).

## Autoteste (você consegue explicar?)
- Por que `ddl-auto: validate` e não `update`?
- O que o `@Around` faz e por que o `LogAspect` serve de evidência para "resposta < 2 s"?
- Por que o papel do usuário NÃO vem do JSON de registro?
