package com.escola.export;

import com.escola.aluno.Aluno;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class CsvExport implements ExportStrategy {

    @Override
    public String formato() {
        return "csv";
    }

    @Override
    public String contentType() {
        return "text/csv";
    }

    @Override
    public byte[] exportar(List<Aluno> alunos) {
        StringBuilder sb = new StringBuilder("id;nome;email;matricula;curso\n");
        for (Aluno a : alunos) {
            sb.append(a.getId()).append(';')
              .append(a.getNome()).append(';')
              .append(a.getEmail()).append(';')
              .append(a.getMatricula()).append(';')
              .append(a.getCurso()).append('\n');
        }
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }
}
