package com.fiap.notification.gateway.queue.core;

import com.fiap.notification.domain.Notificacao;
import com.fiap.notification.domain.TipoNotificacao;
import com.fiap.notification.domain.strategy.NotificacaoStrategy;
import com.fiap.notification.gateway.NotificacaoGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class NotificacaoQueueGateway {

    private final NotificacaoGateway notificacaoGateway;
    private final Map<TipoNotificacao, NotificacaoStrategy> strategyMap;

    @Autowired
    public NotificacaoQueueGateway(List<NotificacaoStrategy> strategies, NotificacaoGateway notificacaoGateway) {
        this.strategyMap = strategies.stream()
                .collect(Collectors.toMap(NotificacaoStrategy::getTipoNotificacao, Function.identity()));
        this.notificacaoGateway = notificacaoGateway;
    }

    @Bean
    public Consumer<Message<Notificacao>> receberNotificacao() {
        return message -> {
            try {
                log.info("Notificacao Recebida - Processando");
                message.getPayload().padrodinizarDataConsulta();
                notificacaoGateway.salvarNotificacao(message.getPayload());
                NotificacaoStrategy strategy = strategyMap.get(message.getPayload().getTipoNotificacao());
                strategy.executar(message.getPayload());
                log.info("Processamento finalizado");
            } catch (Exception e) {
                log.error("Erro ao processar notificacao", e);
            }
        };
    }
}
