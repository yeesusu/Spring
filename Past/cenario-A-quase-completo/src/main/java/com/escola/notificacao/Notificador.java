package com.escola.notificacao;

public interface Notificador {
    String canal();

    void enviar(String destino, String mensagem);
}
