package com.fiap.notification.jpa;

import com.fiap.notification.domain.Notificacao;
import com.fiap.notification.gateway.db.entity.NotificacaoEntity;
import com.fiap.notification.gateway.db.repository.NotificacaoRepository;
import com.fiap.notification.gateway.jpa.NotificacaoGatewayJpa;
import com.fiap.notification.gateway.queue.core.NotificacaoRetornoQueueGateway;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class NotificacaoGatewayJpaTest {

    @InjectMocks
    private NotificacaoGatewayJpa notificacaoGatewayJpa;

    @Mock
    private NotificacaoRepository notificacaoRepository;

    @Mock
    private NotificacaoRetornoQueueGateway notificacaoRetornoQueueGateway;

    @Test
    void saveTest() {
        NotificacaoEntity notificacaoEntity = mock(NotificacaoEntity.class);
        when(notificacaoRepository.existsByConsultaId(any())).thenReturn(false);
        when(notificacaoRepository.save(any())).thenReturn(notificacaoEntity);

        Notificacao notificacao = new Notificacao();
        notificacao.setConsultaId(UUID.randomUUID());

        notificacaoGatewayJpa.salvarNotificacao(notificacao);

        verify(notificacaoRepository).save(any());
    }

    @Test
    void updateTest() {
        NotificacaoEntity notificacaoEntity = mock(NotificacaoEntity.class);
        when(notificacaoRepository.findByConsultaId(any())).thenReturn(notificacaoEntity);

        Notificacao notificacao = new Notificacao();
        notificacao.setConsultaId(UUID.randomUUID());
        notificacao.setConfirmada(false);

        notificacaoGatewayJpa.atualizarNotificacao(notificacao.getConsultaId(), notificacao.isConfirmada());

        verify(notificacaoRepository).save(any());
        verify(notificacaoRetornoQueueGateway).send(notificacao.getConsultaId(), notificacao.isConfirmada());
    }

    @Test
    void cancelarConsultasTest() {
        List<NotificacaoEntity> entities = mock(List.class);

        when(notificacaoRepository.findAllByDataNotificacaoBeforeAndDataRecusaIsNull(any())).thenReturn(entities);

        List<Notificacao> result = notificacaoGatewayJpa.cancelarConsultas(LocalDateTime.now());

        assertNotNull(result);
    }


}
