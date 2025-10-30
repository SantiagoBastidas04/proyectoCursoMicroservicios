package co.edu.unicauca.process_management.entity;
/**
 *
 * @author edwin
 */
public class EstadoPresentadoJefatura implements ProjectState{
    @Override
    public void siguienteEstado(ProjectContext context) {
        context.setCurrentState(new EstadoEvaluacionDepartamento());
    }

    @Override
    public void estadoAnterior(ProjectContext context) {
        context.setCurrentState(new EstadoEscribiendoAnteproyecto());
    }

    @Override
    public String getEstadoNombre() {
        return "Presentado a Jefatura";
    }

    @Override
    public StatusEnum getStatusEnum() {
        return StatusEnum.PRESENTADO_JEFATURA;
    }
}
