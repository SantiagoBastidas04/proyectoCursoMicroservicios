package co.edu.unicauca.process_management.domain.model;

/**
 *
 * @author edwin
 */

/**
 * Interfaz para el patrón State que define el comportamiento de los estados del proyecto
 */
public interface ProjectState {
    void siguienteEstado(ProjectContext context);
    void estadoAnterior(ProjectContext context);
    String getEstadoNombre();
    Status getStatusEnum();
}
