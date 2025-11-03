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
        log.info("[Notificación recibida en NOTIFICATION]");
        log.info("De: {}", dto.getRemitente());
        log.info("Para:  {} , Jefe del departamento", dto.getDestinatario());
        log.info("Asunto: {}", dto.getAsunto());
        log.info("Body: {}", dto.getMensaje());
    }
}
