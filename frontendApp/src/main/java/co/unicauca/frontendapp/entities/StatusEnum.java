/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package co.unicauca.frontendapp.entities;

import java.io.Serializable;

/**
 *
 * @author Acer
 */
public enum StatusEnum implements Serializable{
    FIRST("Primera Evaluación Formato A"),
    SECOND("Segunda Evaluación Formato A"),
    THIRD("Tercera Evaluación Formato A"),
    ACCEPTED("Aceptado Formato A"),
    REJECTED("Rechazado Formato A"),
    
    // Estados del proceso
    INICIO("Inicio"),
    FORMATO_A_DILIGENCIADO("Formato A Diligenciado"),
    PRESENTADO_A_COORDINADOR("Presentado a Coordinador"),
    EN_EVALUACION_COMITE("En Evaluación Comité"),
    CORRECCIONES_COMITE("Correcciones Comité"),
    ACEPTADO_COMITE("Aceptado por Comité"),
    RECHAZADO_COMITE("Rechazado por Comité"),
    ESCRIBIENDO_ANTEPROYECTO("Escribiendo Anteproyecto"),
    PRESENTADO_JEFATURA("Presentado a Jefatura"),
    EVALUACION_DEPARTAMENTO("Evaluación Departamento"),
    EVALUADOR_PIDE_CORRECCIONES("Evaluador Pide Correcciones"),
    EVALUADOR_ACEPTA("Evaluador Acepta"),
    EVALUADOR_RECHAZA("Evaluador Rechaza");
    
    private final String displayName;

    StatusEnum(String displayName) {
        this.displayName = displayName;
    }


    /** 
     * Retorna el nombre legible (con espacios y acentos)
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Busca el enum correspondiente según el nombre legible (útil al parsear respuestas del backend)
     */
    public static StatusEnum fromDisplayName(String name) {
        for (StatusEnum status : StatusEnum.values()) {
            if (status.displayName.equalsIgnoreCase(name.trim())) {
                return status;
            }
        }
        return null;
    }
    
    
    @Override
    public String toString() {
        // Esto es útil si lo cargas directamente en un JComboBox
        return displayName;
    }
}
