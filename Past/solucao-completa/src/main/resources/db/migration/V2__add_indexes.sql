-- Desempenho (RNF): buscas por nome e relatórios por curso
CREATE INDEX idx_alunos_nome  ON alunos (nome);
CREATE INDEX idx_alunos_curso ON alunos (curso);
