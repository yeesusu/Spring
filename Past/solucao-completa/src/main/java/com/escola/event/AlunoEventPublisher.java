package com.escola.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * PADRÃO OBSERVER (lado "ouvinte" do evento interno do Spring) +
 * PRODUTOR KAFKA: repassa o evento para o tópico student.created.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AlunoEventPublisher {

    public static final String TOPIC = "student.created";

    private final KafkaTemplate<String, String> kafka;
    private final ObjectMapper mapper;

    @Value("${app.kafka.enabled:true}")
    private boolean kafkaHabilitado;

    @EventListener
    public void aoCriarAluno(AlunoCriadoEvent evento) {
        if (!kafkaHabilitado) {
            log.info("[KAFKA DESLIGADO] evento ignorado: {}", evento);
            return;
        }
        try {
            kafka.send(TOPIC, String.valueOf(evento.id()), mapper.writeValueAsString(evento));
            log.info("[KAFKA] evento enviado para {}: {}", TOPIC, evento);
        } catch (Exception e) {
            // A API não pode cair só porque o broker caiu.
            log.warn("[KAFKA] falha ao enviar evento: {}", e.getMessage());
        }
    }
}
