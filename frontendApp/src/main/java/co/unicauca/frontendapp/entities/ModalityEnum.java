/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package co.unicauca.frontendapp.entities;

/**
 *
 * @author Acer
 */
public enum ModalityEnum {
    RESEARCH("investigacion"),
    PROFESSIONAL("practica profesional");

    private final String atrName;

    ModalityEnum(String prmName) {
        this.atrName = prmName;
    }

    public String getName() {
        return this.atrName;
    }
}
