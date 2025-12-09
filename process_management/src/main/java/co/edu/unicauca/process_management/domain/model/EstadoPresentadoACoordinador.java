package co.edu.unicauca.process_management.domain.model;

/**
 *
 * @author edwin
 */
public class EstadoPresentadoACoordinador implements ProjectState {
    @Override
    public void siguienteEstado(ProjectContext context) {
        context.setCurrentState(new EstadoEnEvaluacionComite());
    }

    @Override
    public void estadoAnterior(ProjectContext context) {
        context.setCurrentState(new EstadoFormatoADiligenciado());
    }

    @Override
    public String getEstadoNombre() {
        return "Presentado a Coordinador";
    }

    @Override
    public Status getStatusEnum() {
        return Status.PRESENTADO_A_COORDINADOR;
    }
}
