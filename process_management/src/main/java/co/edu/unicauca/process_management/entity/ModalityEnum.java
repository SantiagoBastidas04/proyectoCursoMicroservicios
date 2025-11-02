package co.edu.unicauca.process_management.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enumerador de los modalidades de un proyecto de grado
 * 
 * @author edwin
 */
public enum ModalityEnum {
    
    RESEARCH("investigacion"),
    PROFESSIONAL("practica profesional");
    
    private final String atrName;
    
    ModalityEnum(String prmName)
    {
        this.atrName = prmName;
    }
    
    /**
     * Metodo para obtener el nombre del programa
     * 
     * @return El nombre
     */
    public String getName()
    {
        return this.atrName;
    }
    
    /**
     * Metodo para obtener la modalidad a partir de su nombre
     * 
     * @param prmModality Recibe el nombre
     * 
     * @return El programa si se encuentra. De lo contrario null.
     */
    @JsonCreator
    public static ModalityEnum getModality(String prmModality)
    {
        for(ModalityEnum objModality: ModalityEnum.values())
            if(objModality.getName().equals(prmModality))
                return objModality;
        throw new IllegalArgumentException("Modalidad no válida: " + prmModality);
    }
}
