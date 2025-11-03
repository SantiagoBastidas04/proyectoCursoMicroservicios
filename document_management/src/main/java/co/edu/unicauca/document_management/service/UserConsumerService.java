package co.edu.unicauca.document_management.service;

import co.edu.unicauca.document_management.entity.User;
import co.edu.unicauca.document_management.infra.DTO.UserDTO;
import co.edu.unicauca.document_management.infra.config.RabbitConfig;
import co.edu.unicauca.document_management.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserConsumerService {

    @Autowired
    private UserRepository repository;

    @RabbitListener(queues = RabbitConfig.DOCUMENT_USER_QUEUE)
    public void receiveUser(UserDTO dto) {
        System.out.println("Usuario recibido en DOCUMENT: " + dto.getEmail());
        repository.save(new User(dto.getEmail(), dto.getNombre(), dto.getApellidos(), dto.getRol(), dto.getCelular()));
    }
}
