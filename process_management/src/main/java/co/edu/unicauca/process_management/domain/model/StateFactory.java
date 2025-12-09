package co.edu.unicauca.process_management.domain.model;
 /*
 * @author edwin
 */

/**
 * Factory para crear estados basados en StatusEnum
 */
public class StateFactory {
    public static ProjectState createState(Status status) {


        return switch (status) {
            case INICIO -> new EstadoInicio();
            case FORMATO_A_DILIGENCIADO -> new EstadoFormatoADiligenciado();
            case PRESENTADO_A_COORDINADOR -> new EstadoPresentadoACoordinador();
            case EN_EVALUACION_COMITE -> new EstadoEnEvaluacionComite();
            case CORRECCIONES_COMITE -> new EstadoCorreccionesComite();
            case ACEPTADO_COMITE -> new EstadoAceptadoComite();
            case RECHAZADO_COMITE -> new EstadoRechazadoComite();
            case ESCRIBIENDO_ANTEPROYECTO -> new EstadoEscribiendoAnteproyecto();
            case PRESENTADO_JEFATURA -> new EstadoPresentadoJefatura();
            case EVALUACION_DEPARTAMENTO -> new EstadoEvaluacionDepartamento();
            case EVALUADOR_PIDE_CORRECCIONES -> new EstadoEvaluadorPideCorrecciones();
            case EVALUADOR_ACEPTA -> new EstadoEvaluadorAcepta();
            case EVALUADOR_RECHAZA -> new EstadoEvaluadorRechaza();


        };
    }
}
