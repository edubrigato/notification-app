package com.fiap.notification.gateway.queue.core;

import com.fiap.notification.domain.Notificacao;
import com.fiap.notification.gateway.NotificacaoGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificacaoQueueGateway implements Consumer<Notificacao> {

    private final NotificacaoGateway notificacaoGateway;

    @Override
    public void accept(Notificacao notificacao) {
        log.info("Notificacao Recebida - Processando");
        notificacaoGateway.salvarNotificacao(notificacao);
        log.info("Processamento finalizado");
    }
}
