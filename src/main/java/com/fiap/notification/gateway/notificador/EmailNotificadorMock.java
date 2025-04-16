package com.fiap.notification.gateway.notificador;

import com.fiap.notification.gateway.NotificadorGateway;
import com.fiap.notification.gateway.db.entity.NotificacaoEntity;
import com.fiap.notification.gateway.db.repository.NotificacaoRepository;
import com.fiap.notification.gateway.queue.INotificacaoRetornoQueueGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailNotificadorMock  implements NotificadorGateway {

    private static final String URL =  "http://localhost:8080/confirmarNotificacao";
    private final NotificacaoRepository notificacaoRepository;
    private final INotificacaoRetornoQueueGateway notificacaoRetornoQueueGateway;

    @Override
    public void notificar(NotificacaoEntity notificacao) {
        log.info("Simulando envio de e-mail para: {}", notificacao.getEmail());
        log.info("Mensagem {}, url {}/{}", notificacao, URL, notificacao.getConsultaId());
        NotificacaoEntity entity = notificacaoRepository.findByConsultaId(notificacao.getConsultaId());
        entity.registrarEnvioNotificacao();
        notificacaoRepository.save(entity);
    }

    @Override
    public void notificarDiaAnterior(NotificacaoEntity notificacao) {
        log.info("Simulando envio de e-mail para: {}", notificacao.getEmail());
        log.info("Mensagem {}, url {}/{}", notificacao, URL, notificacao.getConsultaId());
    }

    @Override
    public void cancelarConsulta(NotificacaoEntity notificacao) {
        log.info("Simulando envio de e-mail para: {}", notificacao.getEmail());
        log.info("Mensagem {}, url {}/{}", notificacao, URL, notificacao.getConsultaId());
        notificacao.recusarAgendamento();
        notificacaoRepository.save(notificacao);
        notificacaoRetornoQueueGateway.send(notificacao.getConsultaId(), notificacao.isConfirmada());
    }

}
