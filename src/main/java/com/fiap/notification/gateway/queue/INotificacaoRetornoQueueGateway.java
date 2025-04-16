package com.fiap.notification.gateway.queue;

public interface INotificacaoRetornoQueueGateway {

    void send(Long idNotificacao, boolean confirmada);

}
