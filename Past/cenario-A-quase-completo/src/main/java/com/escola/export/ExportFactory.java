package com.escola.export;

import com.escola.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/** Escolhe a estratégia certa pelo nome do formato. */
@Component
public class ExportFactory {

    private final Map<String, ExportStrategy> estrategias;

    public ExportFactory(List<ExportStrategy> lista) {
        this.estrategias = lista.stream()
                .collect(Collectors.toMap(e -> e.formato().toLowerCase(), Function.identity()));
    }

    public ExportStrategy obter(String formato) {
        ExportStrategy e = estrategias.get(formato.toLowerCase());
        if (e == null) {
            throw new BusinessException("Formato não suportado: " + formato);
        }
        return e;
    }
}
