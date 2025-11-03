package co.unicauca.microservicio_auth.messaging;


import co.unicauca.microservicio_auth.config.RabbitConfig;
import co.unicauca.microservicio_auth.entity.User;
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

    public void enviarUsuario(User user) {
        try {
            rabbitTemplate.convertAndSend(RabbitConfig.USER_EXCHANGE, "", user);


            logger.info("[SubmissionPublisher] Usuario enviado a la cola '{}': {}",
                    RabbitConfig.USER_EXCHANGE, user.getNombre());
        } catch (Exception e) {
            logger.error("Error enviando mensaje a RabbitMQ: {}", e.getMessage());
        }
    }
}