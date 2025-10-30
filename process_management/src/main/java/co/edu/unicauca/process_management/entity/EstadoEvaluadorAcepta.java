package co.edu.unicauca.process_management.entity;
/**
 *
 * @author edwin
 */
public class EstadoEvaluadorAcepta implements ProjectState{
    @Override
    public void siguienteEstado(ProjectContext context) {

    }

    @Override
    public void estadoAnterior(ProjectContext context) {
        context.setCurrentState(new EstadoEvaluacionDepartamento());
    }

    @Override
    public String getEstadoNombre() {
        return "Evaluador Acepta";
    }

    @Override
    public StatusEnum getStatusEnum() {
        return StatusEnum.EVALUADOR_ACEPTA;
    }
}
