package com.escola.notificacao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SmsNotificador implements Notificador {
    @Override
    public String canal() {
        return "SMS";
    }

    @Override
    public void enviar(String destino, String mensagem) {
        log.info("[NOTIFICAÇÃO][SMS] para={} | {}", destino, mensagem); // simulado
    }
}
