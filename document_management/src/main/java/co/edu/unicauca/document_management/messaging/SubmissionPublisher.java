package co.edu.unicauca.document_management.messaging;

import co.edu.unicauca.document_management.entity.Documento;
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
        }catch (Exception e) {
            logger.error("Error enviando mensaje a RabbitMQ: {}", e.getMessage());
        }
    }

}
