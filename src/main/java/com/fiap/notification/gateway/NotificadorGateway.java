package com.fiap.notification.gateway;

import com.fiap.notification.domain.Notificacao;

import java.util.UUID;

public interface NotificadorGateway {

    void notificar(Notificacao notificacao);

    void cancelarConsulta(UUID consultaId);
}
