package com.escola.export;

import com.escola.aluno.Aluno;
import com.escola.exception.BusinessException;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Teste unitário puro (sem Spring, sem banco): roda em milissegundos. */
class ExportTest {

    private final ExportFactory factory = new ExportFactory(List.of(new CsvExport(), new PdfExport()));

    private List<Aluno> alunos() {
        return List.of(Aluno.builder().id(1L).nome("Ana").email("ana@x.com")
                .matricula("M1").curso("ADS").build());
    }

    @Test
    void csvContemOsDados() {
        byte[] bytes = factory.obter("csv").exportar(alunos());
        String texto = new String(bytes, StandardCharsets.UTF_8);
        assertTrue(texto.contains("Ana"));
        assertTrue(texto.startsWith("id;nome"));
    }

    @Test
    void pdfComecaComAssinaturaPdf() {
        byte[] bytes = factory.obter("pdf").exportar(alunos());
        assertEquals("%PDF", new String(bytes, 0, 4, StandardCharsets.US_ASCII));
    }

    @Test
    void formatoDesconhecidoLancaExcecao() {
        assertThrows(BusinessException.class, () -> factory.obter("xml"));
    }
}
