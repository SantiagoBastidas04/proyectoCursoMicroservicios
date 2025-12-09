package co.edu.unicauca.process_management.domain.model;

/**
 *
 * @author edwin
 */
public class EstadoEscribiendoAnteproyecto implements ProjectState {
    @Override
    public void siguienteEstado(ProjectContext context) {
        context.setCurrentState(new EstadoPresentadoJefatura());
    }

    @Override
    public void estadoAnterior(ProjectContext context) {
        context.setCurrentState(new EstadoAceptadoComite());
    }

    @Override
    public String getEstadoNombre() {
        return "Escribiendo Anteproyecto";
    }

    @Override
    public Status getStatusEnum() {
        return Status.ESCRIBIENDO_ANTEPROYECTO;
    }
}
