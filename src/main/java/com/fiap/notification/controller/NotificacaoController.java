package com.fiap.notification.controller;

import com.fiap.notification.usecase.AtualizarNotificacaoUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/confirmarNotificacao")
public class NotificacaoController {

    private final AtualizarNotificacaoUseCase atualizarNotificacaoUseCase;

    @PatchMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> atualizarNotificacao(@RequestParam Long idNotificacao, @RequestParam boolean confirmada) {
        atualizarNotificacaoUseCase.atualizarNotificacao(idNotificacao, confirmada);
        log.info("Notificacao atualizada com sucesso");
        return new ResponseEntity<>("Agendamento Confirmado", HttpStatus.OK);
    }
}
