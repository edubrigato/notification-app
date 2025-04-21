package com.fiap.notification.job;

import com.fiap.notification.domain.Notificacao;
import com.fiap.notification.domain.TipoNotificacao;
import com.fiap.notification.gateway.NotificacaoGateway;
import com.fiap.notification.gateway.NotificadorGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class EnviarNotificacaoJobTest {

    @Mock
    private NotificacaoGateway notificacaoGateway;

    @Mock
    private NotificadorGateway notificadorGateway;

    @InjectMocks
    private EnviarNotificacaoJob enviarNotificacaoJob;

    private Notificacao notificacao1;
    private Notificacao notificacao2;

    @BeforeEach
    void setUp() {
        notificacao1 = new Notificacao(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "João Silva",
                "joao.silva@example.com",
                "11987654321",
                "Consulta de rotina",
                "Clínica ABC",
                "Dr. Carlos",
                "2025-04-22 10:00:00",
                TipoNotificacao.REMANEJO_CONSULTA,
                LocalDateTime.now().minusDays(3),
                LocalDateTime.now().plusDays(1),
                null,
                false
        );

        notificacao2 = new Notificacao(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Maria Oliveira",
                "maria.oliveira@example.com",
                "11987654322",
                "Consulta de emergência",
                "Hospital XYZ",
                "Dr. Fernanda",
                "2025-04-23 14:00:00",
                TipoNotificacao.REMANEJO_CONSULTA,
                LocalDateTime.now().minusDays(3),
                LocalDateTime.now().plusDays(1),
                null,
                false
        );
    }

    @Test
    void testCancelarConsultaNotificacao() {
        List<Notificacao> notificacoes = Arrays.asList(notificacao1, notificacao2);

        when(notificacaoGateway.cancelarConsultas(any())).thenReturn(notificacoes);

        enviarNotificacaoJob.cancelarConsultaNotificacao();

        verify(notificadorGateway, times(1)).cancelarConsulta(notificacao1.getConsultaId());

        verify(notificadorGateway, times(1)).cancelarConsulta(notificacao2.getConsultaId());
    }
}

