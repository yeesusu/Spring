#!/usr/bin/env bash
# Roteiro de demonstração (PIT). Uso: bash demo.sh   (API rodando em localhost:8080)
set -e
API=${API:-http://localhost:8080}

echo "== 1) Sem token => 401"
curl -s -o /dev/null -w "HTTP %{http_code}\n" "$API/alunos"

echo "== 2) Login como ADMIN"
TOKEN=$(curl -s -X POST "$API/auth/login" -H 'Content-Type: application/json' \
  -d '{"email":"admin@escola.com","senha":"admin123"}' | sed -E 's/.*"token":"([^"]+)".*/\1/')
echo "token: ${TOKEN:0:30}..."

echo "== 3) Criar aluno (gera evento student.created no Kafka)"
curl -s -X POST "$API/alunos" -H "Authorization: Bearer $TOKEN" -H 'Content-Type: application/json' \
  -d "{\"nome\":\"Ana Souza\",\"email\":\"ana$RANDOM@x.com\",\"matricula\":\"2026001\",\"curso\":\"ADS\"}"; echo

echo "== 4) Validação: nome vazio => 400"
curl -s -X POST "$API/alunos" -H "Authorization: Bearer $TOKEN" -H 'Content-Type: application/json' \
  -d '{"nome":"","email":"x","matricula":"","curso":""}'; echo

echo "== 5) Listar com paginação e busca"
curl -s "$API/alunos?page=0&size=5&busca=ana" -H "Authorization: Bearer $TOKEN"; echo

echo "== 6) Exportar CSV e PDF"
curl -s "$API/alunos/export?formato=csv" -H "Authorization: Bearer $TOKEN"
curl -s "$API/alunos/export?formato=pdf" -H "Authorization: Bearer $TOKEN" -o alunos.pdf && echo "alunos.pdf salvo"

echo "== 7) Relatório"
curl -s "$API/relatorios/alunos-por-curso" -H "Authorization: Bearer $TOKEN"; echo
