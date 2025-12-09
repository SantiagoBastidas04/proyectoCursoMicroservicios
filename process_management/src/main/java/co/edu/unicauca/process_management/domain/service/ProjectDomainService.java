package co.edu.unicauca.process_management.domain.service;

import co.edu.unicauca.process_management.domain.exception.CorreosIgualesException;
import co.edu.unicauca.process_management.domain.model.Project;
import org.springframework.stereotype.Service;

@Service
public class ProjectDomainService {

    public void validarCorreos(Project p) {

        if (p.directorEqualsStudent()) {
            throw new CorreosIgualesException("El director y estudiante tienen el mismo correo");
        }

        if (p.duplicatedStudents()) {
            throw new CorreosIgualesException("Los estudiantes no pueden tener correos duplicados");
        }
    }
}
