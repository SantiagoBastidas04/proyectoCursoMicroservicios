package co.edu.unicauca.process_management.service;

import co.edu.unicauca.process_management.entity.User;
import co.edu.unicauca.process_management.infra.config.RabbitConfig;
import co.edu.unicauca.process_management.infra.dto.UserDTO;
import co.edu.unicauca.process_management.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserConsumerService {

    @Autowired
    private UserRepository repository;

    @RabbitListener(queues = RabbitConfig.USER_QUEUE)
    public void receiveMessage(UserDTO dto) {
        System.out.println("Usuario Recibido "+ dto.getEmail());

        User usuario = new User(
                dto.getEmail(),
               dto.getNombre(),
                dto.getApellidos(),
                dto.getRol(),
                dto.getCelular()
        );
        repository.save(usuario);
    }

}
