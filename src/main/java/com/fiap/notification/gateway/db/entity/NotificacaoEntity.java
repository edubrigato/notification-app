package com.fiap.notification.gateway.db.entity;

import com.fiap.notification.domain.TipoNotificacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class NotificacaoEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID consultaId;
    private String nomePaciente;
    private String email;
    private String telefone;
    private String consulta;
    private String localConsulta;
    private String nomeMedico;
    private LocalDateTime dataConsultaPadronizada;
    @Enumerated(EnumType.STRING)
    private TipoNotificacao tipoNotificacao;
    private LocalDateTime dataNotificacao;
    private LocalDateTime dataRecusa;
    private boolean confirmada;

}
