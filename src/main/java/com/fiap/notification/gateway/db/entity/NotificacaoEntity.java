package com.fiap.notification.gateway.db.entity;

import com.fiap.notification.domain.TipoNotificacao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notificacao")
@NoArgsConstructor
@Getter
@Setter
public class NotificacaoEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Long idNotificacao;
    private String nomePaciente;
    private String email;
    private String telefone;
    private String consulta;
    private String localConsulta;
    private String nomeMedico;
    private LocalDateTime dataConsulta;
    @Enumerated(EnumType.STRING)
    private TipoNotificacao tipoNotificacao;
    private LocalDateTime dataNotificacao;
    private LocalDateTime dataRecusa;
    private boolean confirmada;

    public void registrarEnvioNotificacao() {
        this.dataNotificacao = LocalDateTime.now();
        this.confirmada = false;
    }

    public void confirmarAgendamento() {
        this.confirmada = true;
    }

    public void recusarAgendamento() {
        this.confirmada = false;
        this.dataRecusa = LocalDateTime.now();
    }
}
