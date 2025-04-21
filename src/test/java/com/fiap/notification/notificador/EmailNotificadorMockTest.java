package com.fiap.notification.notificador;

import com.fiap.notification.domain.Notificacao;
import com.fiap.notification.gateway.db.entity.NotificacaoEntity;
import com.fiap.notification.gateway.db.repository.NotificacaoRepository;
import com.fiap.notification.gateway.notificador.EmailNotificadorMock;
import com.fiap.notification.gateway.queue.core.NotificacaoRetornoQueueGateway;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class EmailNotificadorMockTest {

    @InjectMocks
    private EmailNotificadorMock emailNotificadorMock;

    @Mock
    private NotificacaoRepository notificacaoRepository;

    @Mock
    private NotificacaoRetornoQueueGateway notificacaoRetornoQueueGateway;

    @Test
    void notificarTest(){
        Notificacao notificacao = new Notificacao();
        notificacao.setEmail("paciente@email.com");
        notificacao.setConsultaId(UUID.randomUUID());
        notificacao.setNomePaciente("Fulano de Tal");
        notificacao.setConsulta("Consulta de rotina");

        assertDoesNotThrow(() -> emailNotificadorMock.notificar(notificacao));
    }

    @Test
    void cancelarConsultaTest(){
        NotificacaoEntity notificacao = mock(NotificacaoEntity.class);
        when(notificacaoRepository.findByConsultaId(any())).thenReturn(notificacao);

        emailNotificadorMock.cancelarConsulta(UUID.randomUUID());

        verify(notificacaoRepository, times(1)).findByConsultaId(any());
    }

}
