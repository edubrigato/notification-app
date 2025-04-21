package com.fiap.notification.strategy;

import com.fiap.notification.domain.Notificacao;
import com.fiap.notification.domain.strategy.ConsultaAgendadaStrategy;
import com.fiap.notification.gateway.NotificadorGateway;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ConsultaAgendadaStrategyTest {

    @InjectMocks
    private ConsultaAgendadaStrategy strategy;

    @Mock
    private NotificadorGateway gateway;

    @Test
    void testConsultaAgendadaStrategy() {
        Notificacao notificacao = mock(Notificacao.class);

        strategy.executar(notificacao);

        verify(gateway).notificar(notificacao);
    }

}
