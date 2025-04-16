package com.fiap.notification.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class Notificacao {

    private UUID consultaId;
    private String nomePaciente;
    private String email;
    private String telefone;
    private String consulta;
    private String localConsulta;
    private String nomeMedico;
    private String dataConsulta;
    private TipoNotificacao tipoNotificacao;
    private LocalDateTime dataNotificacao;
    private LocalDateTime dataConsultaPadronizada;

    public void padrodinizarDataConsulta() {
        this.dataConsultaPadronizada = LocalDateTime.parse(this.dataConsulta, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

}
