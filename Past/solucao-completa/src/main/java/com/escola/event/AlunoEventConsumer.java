package com.escola.event;

import com.escola.notificacao.NotificadorFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/** CONSUMIDOR KAFKA: recebe student.created e dispara a notificação. */
@Component
@RequiredArgsConstructor
@Slf4j
public class AlunoEventConsumer {

    private final NotificadorFactory notificadorFactory;
    private final ObjectMapper mapper;

    @KafkaListener(topics = AlunoEventPublisher.TOPIC)
    public void consumir(String payload) throws Exception {
        AlunoCriadoEvent evento = mapper.readValue(payload, AlunoCriadoEvent.class);
        log.info("[KAFKA] evento recebido: {}", evento);
        notificadorFactory.criar("EMAIL")
                .enviar(evento.email(), "Bem-vindo(a), " + evento.nome() + "!");
    }
}
