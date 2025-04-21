package com.fiap.notification.job;

import com.fiap.notification.config.MensagemLog;
import com.fiap.notification.domain.Notificacao;
import com.fiap.notification.gateway.NotificacaoGateway;
import com.fiap.notification.gateway.NotificadorGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class EnviarNotificacaoJob {

    private final NotificacaoGateway notificacaoGateway;
    private final NotificadorGateway notificadorGateway;

    @Scheduled(cron = "0 0 20 * * *", zone = "America/Sao_Paulo")
    public void cancelarConsultaNotificacao() {
        log.info(MensagemLog.INICIO_JOB);
        LocalDateTime dataLimite = LocalDateTime.now().minusDays(3);
        List<Notificacao> domainList = notificacaoGateway.cancelarConsultas(dataLimite);

        for (Notificacao domain : domainList) {
            notificadorGateway.cancelarConsulta(domain.getConsultaId());
        }
        log.info(MensagemLog.FIM_JOB);
    }

}
