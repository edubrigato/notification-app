package com.fiap.notification.domain.strategy;

import com.fiap.notification.config.mapper.NotificacaoMapper;
import com.fiap.notification.domain.Notificacao;
import com.fiap.notification.domain.TipoNotificacao;
import com.fiap.notification.gateway.NotificadorGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EntradaListaEsperaStrategy implements NotificacaoStrategy{

    private final NotificadorGateway notificadorGateway;

    @Override
    public TipoNotificacao getTipoNotificacao() {
        return TipoNotificacao.ENTRADA_LISTA_ESPERA;
    }

    @Override
    public void executar(Notificacao notificacao) {
        notificadorGateway.notificar(NotificacaoMapper.INSTANCE.toEntity(notificacao));
    }
}
