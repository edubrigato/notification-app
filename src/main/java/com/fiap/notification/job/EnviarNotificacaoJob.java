package com.fiap.notification.job;

import com.fiap.notification.gateway.NotificacaoGateway;
import com.fiap.notification.gateway.NotificadorGateway;
import com.fiap.notification.gateway.db.entity.NotificacaoEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class EnviarNotificacaoJob {

    private final NotificacaoGateway notificacaoGateway;
    private final NotificadorGateway notificadorGateway;

    @Scheduled(cron = "0 0 8 * * *", zone = "America/Sao_Paulo")
    public void enviarNotificacao() {
        log.info("Iniciando Job...");
        List<NotificacaoEntity> entityList = notificacaoGateway.buscarNotificacoesParaEnvio();
        for (NotificacaoEntity entity : entityList) {
            notificadorGateway.notificar(entity);
        }
        log.info("Fim do Job");
    }

    @Scheduled(cron = "0 0 8 * * *", zone = "America/Sao_Paulo")
    public void enviarNotificacaoDiaAnterior() {
        log.info("Iniciando Job...");
        LocalDateTime inicioAmanha = LocalDate.now().plusDays(1).atStartOfDay();
        LocalDateTime fimAmanha = LocalDate.now().plusDays(1).atTime(LocalTime.MAX);

        List<NotificacaoEntity> entityList = notificacaoGateway.buscarNotificacoesDiaAnterior(inicioAmanha, fimAmanha);
        for (NotificacaoEntity entity : entityList) {
            notificadorGateway.notificarDiaAnterior(entity);
        }
        log.info("Fim do Job");
    }

    @Scheduled(cron = "0 0 20 * * *", zone = "America/Sao_Paulo")
    public void cancelarConsultaNotificacao() {
        log.info("Iniciando Job...");
        LocalDateTime dataLimite = LocalDateTime.now().minusDays(3);
        List<NotificacaoEntity> entityList = notificacaoGateway.cancelarConsultas(dataLimite);

        for (NotificacaoEntity entity : entityList) {
            notificadorGateway.cancelarConsulta(entity);
        }
    }

}
