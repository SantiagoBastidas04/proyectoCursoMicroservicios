package co.edu.unicauca.process_management.entity;
/**
 *
 * @author edwin
 */
public class EstadoPresentadoACoordinador implements ProjectState{
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
    public StatusEnum getStatusEnum() {
        return StatusEnum.PRESENTADO_A_COORDINADOR;
    }
}
