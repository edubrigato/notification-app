package com.fiap.notification.gateway.queue.core;

import com.fiap.notification.config.NotificationProperties;
import com.fiap.notification.domain.NotificacaoRetorno;
import com.fiap.notification.gateway.queue.INotificacaoRetornoQueueGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificacaoRetornoQueueGateway implements INotificacaoRetornoQueueGateway {

    private final StreamBridge streamBridge;
    private final NotificationProperties notificationProperties;

    @Override
    public void send(Long idNotificacao, boolean confirmada) {
        NotificacaoRetorno retorno = new NotificacaoRetorno(idNotificacao.toString(), confirmada);
        log.info("Notificacao retorno: {}", retorno);
        streamBridge.send(notificationProperties.getNotificationSendChannel(), retorno);
    }

}
