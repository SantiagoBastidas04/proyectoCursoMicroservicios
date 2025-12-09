package co.edu.unicauca.process_management.domain.model;

/**
 *
 * @author edwin
 */
public class EstadoFormatoADiligenciado implements ProjectState {
    @Override
    public void siguienteEstado(ProjectContext context) {
        context.setCurrentState(new EstadoPresentadoACoordinador());
    }

    @Override
    public void estadoAnterior(ProjectContext context) {
        context.setCurrentState(new EstadoInicio());
    }

    @Override
    public String getEstadoNombre() {
        return "Formato A Diligenciado";
    }

    @Override
    public Status getStatusEnum() {
        return Status.FORMATO_A_DILIGENCIADO;
    }
}
