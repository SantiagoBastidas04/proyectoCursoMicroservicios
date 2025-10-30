/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.frontendapp.service;

import co.unicauca.frontendapp.access.IProjectRepositorio;
import co.unicauca.frontendapp.entities.ProjectModel;

/**
 *
 * @author Acer
 */
public class ServiceProyecto {
    private final IProjectRepositorio projectRepositorio;

    public ServiceProyecto(IProjectRepositorio projectRepositorio) {
        this.projectRepositorio = projectRepositorio;
    }

    public boolean registrarProyecto(ProjectModel proyecto) {
        return projectRepositorio.registrarProyecto(proyecto);
    }
}
