package com.fiap.notification.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class Notificacao {

    private Long idNotificacao;
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
