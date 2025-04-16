package com.fiap.notification.domain;

import lombok.Getter;

@Getter
public enum TipoNotificacao {

    ENTRADA_LISTA_ESPERA (1),
    CONSULTA_AGENDADA (2),
    CONFIRMACAO_CONSULTA (3),
    AVISO_UM_DIA_ANTES (4),
    REMANEJO_CONSULTA (5);

    private final int id;

    TipoNotificacao(int id) {
        this.id = id;
    }

}
