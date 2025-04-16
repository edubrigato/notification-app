package com.fiap.notification.gateway.queue.core;

import com.fiap.notification.domain.Notificacao;
import com.fiap.notification.gateway.NotificacaoGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificacaoQueueGateway {

    private final NotificacaoGateway notificacaoGateway;

    @Bean
    public Consumer<Message<Notificacao>> receberNotificacao() {
        return message -> {
            try {
                log.info("Notificacao Recebida - Processando");
                notificacaoGateway.salvarNotificacao(message.getPayload());
                log.info("Processamento finalizado");
            } catch (Exception e) {
                log.error("Erro ao processar notificacao", e);
            }
        };
    }
}
