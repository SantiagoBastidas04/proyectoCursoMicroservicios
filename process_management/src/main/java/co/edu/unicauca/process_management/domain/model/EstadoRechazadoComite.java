package co.edu.unicauca.process_management.domain.model;

/**
 *
 * @author edwin
 */
public class EstadoRechazadoComite implements ProjectState {
    @Override
    public void siguienteEstado(ProjectContext context) {
        // Estado final - no hay siguiente estado
    }

    @Override
    public void estadoAnterior(ProjectContext context) {
        context.setCurrentState(new EstadoEnEvaluacionComite());
    }

    @Override
    public String getEstadoNombre() {
        return "Rechazado por Comité";
    }

    @Override
    public Status getStatusEnum() {
        return Status.RECHAZADO_COMITE;
    }
}
