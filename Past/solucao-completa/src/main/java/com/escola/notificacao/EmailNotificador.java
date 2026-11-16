package com.escola.notificacao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailNotificador implements Notificador {
    @Override
    public String canal() {
        return "EMAIL";
    }

    @Override
    public void enviar(String destino, String mensagem) {
        log.info("[NOTIFICAÇÃO][EMAIL] para={} | {}", destino, mensagem); // simulado
    }
}
