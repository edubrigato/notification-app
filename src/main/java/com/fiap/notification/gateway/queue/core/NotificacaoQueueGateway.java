package com.fiap.notification.gateway.queue.core;

import com.fiap.notification.config.mapper.NotificacaoMapper;
import com.fiap.notification.domain.Notificacao;
import com.fiap.notification.gateway.NotificacaoGateway;
import com.fiap.notification.gateway.NotificadorGateway;
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
    private final NotificadorGateway notificadorGateway;

    @Bean
    public Consumer<Message<Notificacao>> receberNotificacao() {
        return message -> {
            try {
                log.info("Notificacao Recebida - Processando");
                message.getPayload().padrodinizarDataConsulta();
                notificacaoGateway.salvarNotificacao(message.getPayload());
                notificadorGateway.notificar(NotificacaoMapper.INSTANCE.toEntity(message.getPayload()));
                log.info("Processamento finalizado");
            } catch (Exception e) {
                log.error("Erro ao processar notificacao", e);
            }
        };
    }
}
