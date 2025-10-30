package co.edu.unicauca.process_management.entity;
/**
 *
 * @author edwin
 */
public class EstadoFormatoADiligenciado implements ProjectState{
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
    public StatusEnum getStatusEnum() {
        return StatusEnum.FORMATO_A_DILIGENCIADO;
    }
}
