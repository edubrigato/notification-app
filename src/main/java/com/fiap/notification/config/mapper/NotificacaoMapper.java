package com.fiap.notification.config.mapper;

import com.fiap.notification.domain.Notificacao;
import com.fiap.notification.gateway.db.entity.NotificacaoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NotificacaoMapper {

    NotificacaoMapper INSTANCE = Mappers.getMapper(NotificacaoMapper.class);

    NotificacaoEntity toEntity(Notificacao notificacao);
    Notificacao toDomain(NotificacaoEntity entity);
}
