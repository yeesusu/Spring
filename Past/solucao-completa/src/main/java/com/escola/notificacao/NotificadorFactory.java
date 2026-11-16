package com.escola.notificacao;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/** PADRÃO FACTORY: "me dê o notificador do canal X" sem usar if/else espalhado. */
@Component
public class NotificadorFactory {

    private final Map<String, Notificador> porCanal;

    // O Spring injeta TODOS os beans que implementam Notificador
    public NotificadorFactory(List<Notificador> notificadores) {
        this.porCanal = notificadores.stream()
                .collect(Collectors.toMap(n -> n.canal().toUpperCase(), Function.identity()));
    }

    public Notificador criar(String canal) {
        Notificador n = porCanal.get(canal.toUpperCase());
        if (n == null) {
            throw new IllegalArgumentException("Canal desconhecido: " + canal);
        }
        return n;
    }
}
