package co.edu.unicauca.process_management.service;

import co.edu.unicauca.process_management.entity.ProjectModel;
import co.edu.unicauca.process_management.infra.config.RabbitConfig;
import co.edu.unicauca.process_management.infra.dto.DocumentoDTO;
import co.edu.unicauca.process_management.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor

public class DocumentConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(DocumentConsumerService.class);

    private final ProjectRepository projectRepository;
    @RabbitListener(queues = RabbitConfig.DOCUMENT_QUEUE)
    public void recibirDocumento(DocumentoDTO documento){
        logger.info("Documento recibido: tipo={}, ruta={}",documento.getTipo(),documento.getRutaArchivo());

        //buscar proyecto asociado

        Optional<ProjectModel> projectOpt =
                projectRepository.findByAtrDirectorEmailAndAtrStudent1Email(
                        documento.getDirectorId(),
                        documento.getEstudiante1Id()
                );
        if(projectOpt.isPresent()){
            ProjectModel objProject = projectOpt.get();

            if("FORMATO_A".equalsIgnoreCase(documento.getTipo())){
                objProject.setRutaFormatoA(documento.getRutaArchivo());
            }else if("ANTEPROYECTO".equalsIgnoreCase(documento.getTipo())){
                objProject.setRutaAnteproyecto(documento.getRutaArchivo());

            }
            projectRepository.save(objProject);
            logger.info("Documento asociado al trabajo de grado correctamente")
;        }else{
            logger.warn("No se encontro trabajo de grado para diirector={}, y estudiante={}",
                    documento.getDirectorId(),documento.getEstudiante1Id());
        }
    }

}
