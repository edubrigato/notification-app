package com.fiap.notification.gateway;

import com.fiap.notification.gateway.db.entity.NotificacaoEntity;

public interface NotificadorGateway {

    void notificar(NotificacaoEntity notificacao);

    void notificarDiaAnterior(NotificacaoEntity notificacao);

    void cancelarConsulta(NotificacaoEntity notificacao);
}
