package com.fiap.notification.usecase;

import com.fiap.notification.gateway.NotificacaoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtualizarNotificacaoUseCase {

    private final NotificacaoGateway notificacaoGateway;

    public void atualizarNotificacao(Long pedidoId, boolean confirmada) {
        notificacaoGateway.atualizarNotificacao(pedidoId, confirmada);
    }

}
