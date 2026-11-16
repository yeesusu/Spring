package com.escola.export;

import com.escola.aluno.Aluno;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Component
public class PdfExport implements ExportStrategy {

    @Override
    public String formato() {
        return "pdf";
    }

    @Override
    public String contentType() {
        return "application/pdf";
    }

    @Override
    public byte[] exportar(List<Aluno> alunos) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document doc = new Document();
        PdfWriter.getInstance(doc, out);
        doc.open();
        doc.add(new Paragraph("Relatório de Alunos"));
        doc.add(new Paragraph(" "));
        for (Aluno a : alunos) {
            doc.add(new Paragraph(a.getMatricula() + " - " + a.getNome()
                    + " (" + a.getCurso() + ") - " + a.getEmail()));
        }
        doc.close();
        return out.toByteArray();
    }
}
