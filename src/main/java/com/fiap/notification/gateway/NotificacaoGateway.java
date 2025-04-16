package com.fiap.notification.gateway;

import com.fiap.notification.domain.Notificacao;
import com.fiap.notification.gateway.db.entity.NotificacaoEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface NotificacaoGateway {

    void salvarNotificacao(Notificacao notificacao);

    void atualizarNotificacao(UUID idNotificacao, boolean confirmada);

    List<NotificacaoEntity> buscarNotificacoesParaEnvio();

    List<NotificacaoEntity> buscarNotificacoesDiaAnterior(LocalDateTime inicio, LocalDateTime fim);

    List<NotificacaoEntity> cancelarConsultas(LocalDateTime dataLimite);
}
