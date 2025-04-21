package com.fiap.notification.gateway.db.repository;

import com.fiap.notification.gateway.db.entity.NotificacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface NotificacaoRepository extends JpaRepository<NotificacaoEntity, UUID> {

    NotificacaoEntity findByConsultaId(UUID consultaId);

    List<NotificacaoEntity> findAllByDataNotificacaoBeforeAndDataRecusaIsNull(LocalDateTime dataLimite);

    boolean existsByConsultaId(UUID consultaId);

}
