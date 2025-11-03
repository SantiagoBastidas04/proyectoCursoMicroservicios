package co.edu.unicauca.microservice_notification.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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