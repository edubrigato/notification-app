package com.fiap.notification.gateway;

import com.fiap.notification.domain.Notificacao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface NotificacaoGateway {

    void salvarNotificacao(Notificacao notificacao);

    void atualizarNotificacao(UUID idNotificacao, boolean confirmada);

    List<Notificacao> cancelarConsultas(LocalDateTime dataLimite);
}
