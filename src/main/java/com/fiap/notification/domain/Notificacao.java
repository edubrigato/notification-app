package com.fiap.notification.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class Notificacao {

    private UUID idNotificacao;
    private String nomePaciente;
    private String email;
    private String telefone;
    private String consulta;
    private String localConsulta;
    private String nomeMedico;
    private LocalDateTime dataConsulta;
    private TipoNotificacao tipoNotificacao;
    private LocalDateTime dataNotificacao;
}
