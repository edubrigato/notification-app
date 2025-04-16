package com.fiap.notification.gateway.queue;

import java.util.UUID;

public interface INotificacaoRetornoQueueGateway {

    void send(UUID idNotificacao, boolean confirmada);

}
