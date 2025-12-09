package co.edu.unicauca.process_management.adapter;

import co.edu.unicauca.process_management.adapter.config.RabbitConfig;
import co.edu.unicauca.process_management.domain.model.User;
import co.edu.unicauca.process_management.port.out.UserRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQUserConsumer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQUserConsumer.class);

    private final UserRepositoryPort userRepositoryPort;

    public RabbitMQUserConsumer(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @RabbitListener(queues = RabbitConfig.PROCESS_USER_QUEUE)
    public void receiveUser(RegisterUserDTO dto) {

        logger.info("Usuario recibido en PROCESS_USER_QUEUE: {}", dto.getEmail());

        User user = new User(
                dto.getEmail(),
                dto.getNombre(),
                dto.getApellidos(),
                dto.getRol(),
                dto.getCelular()
        );

        userRepositoryPort.save(user);

        logger.info("Usuario guardado correctamente: {}", dto.getEmail());
    }
}
