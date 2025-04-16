package com.fiap.notification.config;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class NotificationProperties {

    private String notificationSendChannel = "confirmacao-consulta-dlx";

}
