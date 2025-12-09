package co.edu.unicauca.document_management.messaging;

import co.edu.unicauca.document_management.entity.Documento;
import co.edu.unicauca.document_management.infra.DTO.NotificacionDTO;
import co.edu.unicauca.document_management.infra.config.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class SubmissionPublisher {
    private static final Logger logger = LoggerFactory.getLogger(SubmissionPublisher.class);

    private final RabbitTemplate rabbitTemplate;

    public SubmissionPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarDocumento(Documento documento){
        try{
            rabbitTemplate.convertAndSend(RabbitConfig.DOCUMENT_QUEUE, documento);

            logger.info("[SubmissionPublisher] Documento  enviado a la cola '{}': {}",
                    RabbitConfig.DOCUMENT_QUEUE, documento.getTipo());
            //Crear mensaje de notificación
            NotificacionDTO notificacion = construirMensajeNotificacion(documento);

            rabbitTemplate.convertAndSend(RabbitConfig.NOTIFICATION_QUEUE, notificacion);
            logger.info("[SubmissionPublisher] Notificación enviada a '{}': {}",
                    RabbitConfig.NOTIFICATION_QUEUE, notificacion.getAsunto());
        }catch (Exception e) {
            logger.error("Error enviando mensaje a RabbitMQ: {}", e.getMessage());
        }

    }


    private NotificacionDTO construirMensajeNotificacion(Documento documento){
        NotificacionDTO notificacion = new NotificacionDTO();
        notificacion.setRemitente("sistemas@unicauca.edu.co");
        switch (documento.getTipo().toUpperCase()) {
            case "FORMATO_A" -> {
                notificacion.setDestinatario("coordinador@unicauca.edu.co");
                notificacion.setAsunto("Nuevo Formato A recibido");
                notificacion.setMensaje("Se ha recibido un nuevo Formato A del proyecto: " +
                        documento.getNombreArchivo());
            }
            case "FORMATO_A_REVISION" -> {
                notificacion.setDestinatario("coordinador@unicauca.edu.co");
                notificacion.setAsunto("Nueva versión de Formato A");
                notificacion.setMensaje("Se ha recibido una nueva versión del Formato A del proyecto: " +
                        documento.getNombreArchivo());
            }
            case "ANTEPROYECTO" -> {
                notificacion.setDestinatario("jefe.departamento@unicauca.edu.co");
                notificacion.setAsunto("Nuevo Anteproyecto recibido");
                notificacion.setMensaje("Se ha recibido un anteproyecto del proyecto: " +
                        documento.getNombreArchivo());
            }
        }
        notificacion.setTipoDocumento(documento.getTipo());
        return notificacion;
    }

    public void notificarAsignacionEvaluadores(Long anteproyectoId, String docente1, String docente2) {

        NotificacionDTO notificacion1 = new NotificacionDTO(
                "ASIGNACIÓN_EVALUADOR",
                "Asignación como evaluador de Anteproyecto",
                "Usted ha sido asignado como evaluador del anteproyecto con ID: " + anteproyectoId,
                docente1,
                "jefe.departamento@unicauca.edu.co"
        );

        NotificacionDTO notificacion2 = new NotificacionDTO(
                "ASIGNACIÓN_EVALUADOR",
                "Asignación como evaluador de Anteproyecto",
                "Usted ha sido asignado como evaluador del anteproyecto con ID: " + anteproyectoId,
                docente2,
                "jefe.departamento@unicauca.edu.co"
        );

        rabbitTemplate.convertAndSend(RabbitConfig.NOTIFICATION_QUEUE, notificacion1);
        rabbitTemplate.convertAndSend(RabbitConfig.NOTIFICATION_QUEUE, notificacion2);

        logger.info("[SubmissionPublisher] Notificaciones enviadas para evaluadores del anteproyecto {}.", anteproyectoId);
    }
}
