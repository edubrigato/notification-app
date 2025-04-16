package com.fiap.notification.domain.strategy;

import com.fiap.notification.domain.Notificacao;
import com.fiap.notification.domain.TipoNotificacao;

public interface NotificacaoStrategy {

    TipoNotificacao getTipoNotificacao();

    void executar(Notificacao notificacao);
}
