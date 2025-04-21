package com.fiap.notification.controller;

import com.fiap.notification.config.mapper.NotificacaoMapper;
import com.fiap.notification.domain.Notificacao;
import com.fiap.notification.gateway.db.repository.NotificacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class NotificacaoControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    private UUID consultaId;

    @BeforeEach
    void setup() {
        // Criar uma notificação de teste e salvar no banco
        Notificacao notificacao = new Notificacao();
        consultaId = UUID.randomUUID();
        notificacao.setConsultaId(consultaId);
        notificacao.setConfirmada(false); // ou null, dependendo do seu design
        notificacaoRepository.save(NotificacaoMapper.INSTANCE.toEntity(notificacao));
    }

    @Test
    void deveAtualizarNotificacaoComSucesso() throws Exception {
        mockMvc.perform(patch("/confirmarNotificacao")
                        .param("consultaId", consultaId.toString())
                        .param("confirmada", "true"))
                .andExpect(status().isOk())
                .andExpect(content().string("Agendamento confirmado"));

        // Valida no banco se foi realmente atualizado
        Optional<Notificacao> updated = Optional.ofNullable(NotificacaoMapper.INSTANCE.toDomain(notificacaoRepository.findByConsultaId(consultaId)));
        assertTrue(updated.isPresent());
        assertTrue(updated.get().isConfirmada());
    }
}

