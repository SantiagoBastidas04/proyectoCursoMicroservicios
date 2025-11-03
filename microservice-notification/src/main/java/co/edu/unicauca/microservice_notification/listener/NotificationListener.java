package co.edu.unicauca.microservice_notification.listener;

import co.edu.unicauca.microservice_notification.config.RabbitConfig;
import co.edu.unicauca.microservice_notification.model.NotificacionDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationListener {
    @RabbitListener(queues = RabbitConfig.NOTIFICATION_QUEUE)
    public void recibirNotificacion(NotificacionDTO dto) {
        log.info("""
                ------------------------------------------------------------
                📬  NUEVA NOTIFICACIÓN RECIBIDA
                ------------------------------------------------------------
                Remitente:     {}
                Destinatario:  {}
                Asunto:        {}
                Mensaje:
                {}
                ------------------------------------------------------------
                ✅  Correo enviado exitosamente a {}
                ------------------------------------------------------------
                """,
                dto.getRemitente(),
                dto.getDestinatario(),
                dto.getAsunto(),
                dto.getMensaje(),
                dto.getDestinatario());
    }

}
