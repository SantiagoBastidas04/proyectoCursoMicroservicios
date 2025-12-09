package co.edu.unicauca.process_management.domain.model;

/**
 *
 * @author edwin
 */
public class EstadoAceptadoComite implements ProjectState {
    @Override
    public void siguienteEstado(ProjectContext context) {
        context.setCurrentState(new EstadoEscribiendoAnteproyecto());
    }

    @Override
    public void estadoAnterior(ProjectContext context) {
        context.setCurrentState(new EstadoEnEvaluacionComite());
    }

    @Override
    public String getEstadoNombre() {
        return "Aceptado por Comité";
    }

    @Override
    public Status getStatusEnum() {
        return Status.ACEPTADO_COMITE;
    }
}
