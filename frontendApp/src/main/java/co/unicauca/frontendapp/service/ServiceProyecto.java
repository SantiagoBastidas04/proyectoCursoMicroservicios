/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.frontendapp.service;

import co.unicauca.frontendapp.access.IProjectRepositorio;
import co.unicauca.frontendapp.entities.ProjectModel;
import co.unicauca.frontendapp.entities.StatusEnum;
import java.util.List;

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
    public List<ProjectModel> listarPorEmailEstudiante(String emailEstudiante){
        return projectRepositorio.listarPorEmailEstudiante(emailEstudiante);
    }
    public List<ProjectModel> listarPendientes(){
        return projectRepositorio.listarPendientes();
    }
    public boolean actualizarProyecto(ProjectModel proyecto){
        return projectRepositorio.actualizarProyecto(proyecto);
    }
     public boolean avanzarEstado(ProjectModel proyecto){
         return projectRepositorio.avanzarEstado(proyecto);
     }
    public boolean retrocederEstado(ProjectModel proyecto){
        return projectRepositorio.retrocederEstado(proyecto);
        
    }
    public boolean corregirEstado(ProjectModel proyecto){
        return projectRepositorio.corregirEstado(proyecto);
    }
    public List<ProjectModel> listarPorEmailProfesor(String emailProfesor){
        return projectRepositorio.listarPorEmailProfesor(emailProfesor);
    }
    public List<ProjectModel> listarPorEstadoYCorreo(StatusEnum estado, String emailProfesor){
        return projectRepositorio.listarPorEstadoYCorreo(estado,emailProfesor);
    }
    public List<ProjectModel> listarPorEstado(StatusEnum estado){
        return projectRepositorio.listarPorEstado(estado);
    }
}
