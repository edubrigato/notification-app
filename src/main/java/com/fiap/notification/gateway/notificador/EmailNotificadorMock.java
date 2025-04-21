package com.fiap.notification.gateway.notificador;

import com.fiap.notification.config.mapper.NotificacaoMapper;
import com.fiap.notification.domain.Notificacao;
import com.fiap.notification.gateway.NotificadorGateway;
import com.fiap.notification.gateway.db.repository.NotificacaoRepository;
import com.fiap.notification.gateway.queue.INotificacaoRetornoQueueGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailNotificadorMock  implements NotificadorGateway {

    private static final String URL =  "http://localhost:8080/confirmarNotificacao";
    private final NotificacaoRepository notificacaoRepository;
    private final INotificacaoRetornoQueueGateway notificacaoRetornoQueueGateway;

    @Override
    public void notificar(Notificacao notificacao) {
        log.info("Simulando envio de e-mail para: {}", notificacao.getEmail());
        log.info("Mensagem {}, url {}/{}", notificacao, URL, notificacao.getConsultaId());
    }

    @Override
    public void cancelarConsulta(UUID consultaId) {
        Notificacao notificacao = NotificacaoMapper.INSTANCE.toDomain(notificacaoRepository.findByConsultaId(consultaId));
        log.info("Simulando envio de e-mail para: {}", notificacao.getEmail());
        log.info("Mensagem {}, url {}/{}", notificacao, URL, notificacao.getConsultaId());
        notificacao.recusarAgendamento();
        notificacaoRepository.save(NotificacaoMapper.INSTANCE.toEntity(notificacao));
        notificacaoRetornoQueueGateway.send(notificacao.getConsultaId(), notificacao.isConfirmada());
    }

}
