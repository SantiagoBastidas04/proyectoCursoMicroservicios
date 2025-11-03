package co.edu.unicauca.document_management.infra.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase DTO para enviar mensajes de notificación
 * al microservicio de notificaciones a través de RabbitMQ.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionDTO {
    private String tipoDocumento;   // FORMATO_A o ANTEPROYECTO
    private String asunto;          // Asunto del correo
    private String mensaje;         // Cuerpo del correo (body)
    private String destinatario;    // A quién se envía
    private String remitente;
}
