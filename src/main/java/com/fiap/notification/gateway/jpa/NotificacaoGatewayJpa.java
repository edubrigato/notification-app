package com.fiap.notification.gateway.jpa;

import com.fiap.notification.config.mapper.NotificacaoMapper;
import com.fiap.notification.domain.Notificacao;
import com.fiap.notification.gateway.NotificacaoGateway;
import com.fiap.notification.gateway.db.entity.NotificacaoEntity;
import com.fiap.notification.gateway.db.repository.NotificacaoRepository;
import com.fiap.notification.gateway.queue.INotificacaoRetornoQueueGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificacaoGatewayJpa implements NotificacaoGateway {

    private final NotificacaoRepository notificacaoRepository;
    private final INotificacaoRetornoQueueGateway notificacaoRetornoQueueGateway;

    @Override
    public void salvarNotificacao(Notificacao notificacao) {
        if (!existeNotificacao(notificacao.getConsultaId())) {
            notificacaoRepository.save(NotificacaoMapper.INSTANCE.toEntity(notificacao));
            log.info("Notificacao salvo com sucesso");
        }
    }

    @Override
    public void atualizarNotificacao(UUID idNotificacao, boolean confirmada) {
        NotificacaoEntity entity = notificacaoRepository.findByConsultaId(idNotificacao);
        if (confirmada) {
            entity.confirmarAgendamento();
        } else {
            entity.recusarAgendamento();
        }
        notificacaoRetornoQueueGateway.send(idNotificacao, confirmada);
        notificacaoRepository.save(entity);
    }

    @Override
    public List<NotificacaoEntity> buscarNotificacoesParaEnvio() {
        return notificacaoRepository.findAllByDataNotificacaoIsNull();
    }

    @Override
    public List<NotificacaoEntity> buscarNotificacoesDiaAnterior(LocalDateTime inicio, LocalDateTime fim) {
        return notificacaoRepository.findAllByConfirmadaTrueAndDataConsultaPadronizadaBetween(inicio, fim);
    }

    @Override
    public List<NotificacaoEntity> cancelarConsultas(LocalDateTime dataLimite){
        return notificacaoRepository.findAllByDataNotificacaoBeforeAndConfirmadaIsFalse(dataLimite);
    }

    private boolean existeNotificacao(UUID consultaId) {
        return notificacaoRepository.existsByConsultaId(consultaId);
    }
}
