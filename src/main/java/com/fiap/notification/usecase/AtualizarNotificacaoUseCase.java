package com.fiap.notification.usecase;

import com.fiap.notification.gateway.NotificacaoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AtualizarNotificacaoUseCase {

    private final NotificacaoGateway notificacaoGateway;

    public void atualizarNotificacao(UUID consultaId, boolean confirmada) {
        notificacaoGateway.atualizarNotificacao(consultaId, confirmada);
    }

}
