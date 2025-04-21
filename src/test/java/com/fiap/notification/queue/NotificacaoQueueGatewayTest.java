package com.fiap.notification.queue;

import com.fiap.notification.domain.Notificacao;
import com.fiap.notification.domain.TipoNotificacao;
import com.fiap.notification.domain.strategy.NotificacaoStrategy;
import com.fiap.notification.gateway.NotificacaoGateway;
import com.fiap.notification.gateway.queue.core.NotificacaoQueueGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
class NotificacaoQueueGatewayTest {

    @Mock
    private NotificacaoGateway notificacaoGateway;

    @Mock
    private NotificacaoStrategy strategy;

    @Captor
    private ArgumentCaptor<Notificacao> notificacaoCaptor;

    private NotificacaoQueueGateway notificacaoQueueGateway;

    @BeforeEach
    void setUp() {
        when(strategy.getTipoNotificacao()).thenReturn(TipoNotificacao.REMANEJO_CONSULTA);

        notificacaoQueueGateway = new NotificacaoQueueGateway(List.of(strategy), notificacaoGateway);
    }

    @Test
    void deveProcessarNotificacaoComSucesso() {
        Notificacao notificacao = Mockito.spy(new Notificacao());
        notificacao.setDataConsulta("2025-05-12T14:30:00");
        notificacao.setTipoNotificacao(TipoNotificacao.REMANEJO_CONSULTA);

        Message<Notificacao> mensagem = MessageBuilder.withPayload(notificacao).build();

        notificacaoQueueGateway.receberNotificacao().accept(mensagem);

        verify(notificacao, times(1)).padrodinizarDataConsulta();
        verify(notificacaoGateway, times(1)).salvarNotificacao(notificacao);
        verify(strategy, times(1)).executar(notificacao);
    }

    @Test
    void deveLogarErroQuandoOcorreExcecao() {
        Notificacao notificacao = Mockito.mock(Notificacao.class);

        doThrow(new RuntimeException("Erro ao processar notificacao")).when(notificacao).padrodinizarDataConsulta();

        Message<Notificacao> mensagem = MessageBuilder.withPayload(notificacao).build();

        assertDoesNotThrow(() -> notificacaoQueueGateway.receberNotificacao().accept(mensagem));
        verify(notificacaoGateway, never()).salvarNotificacao(any());
        verify(strategy, never()).executar(any());
    }
}

