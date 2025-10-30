/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.frontendapp.access;

import co.unicauca.frontendapp.entities.ProjectModel;
import java.net.http.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 *
 * @author Acer
 */
public class ProjectRepositorio implements IProjectRepositorio {
    private static final String BASE = "http://localhost:8081/api/proyectos";
    private final HttpClient http = HttpClient.newHttpClient();
    
    @Override
    public boolean registrarProyecto(ProjectModel proyecto) {
        try {
            String json = toJson(proyecto);
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(BASE))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());
            if (res.statusCode() >= 200 && res.statusCode() < 300) {
                return true;
            } else {
                System.err.println("Registro falló: " + res.statusCode() + " - " + res.body());
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public List<ProjectModel> listarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ProjectModel buscarPorId(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ProjectModel> listarPorEmail(String emailProfesor) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ProjectModel> listarPendientes() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ProjectModel> listarPorEmailEstudiante(String emailEstudiante) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    // ----------------- Helpers -----------------
    private static String enc(String s) {
        return URLEncoder.encode(s == null ? "" : s, StandardCharsets.UTF_8);
    }

   private static String toJson(ProjectModel p) {
    StringBuilder sb = new StringBuilder("{");
    //sb.append(js("atrId", p.getAtrId() != null ? p.getAtrId().toString() : null)).append(",");
    sb.append(js("atrTitle", p.getAtrTitle())).append(",");
    sb.append(js("atrDirectorEmail", p.getAtrDirectorEmail())).append(",");
    sb.append(js("atrCodirectorEmail", p.getAtrCodirectorEmail())).append(",");
    sb.append(js("atrStudent1Email", p.getAtrStudent1Email())).append(",");
    sb.append(js("atrStudent2Email", p.getAtrStudent2Email())).append(",");
    sb.append(js("atrModality", p.getAtrModality() != null ? p.getAtrModality().getName() : null)).append(",");
    sb.append(js("atrCreationDate", p.getAtrCreationDate() != null ? p.getAtrCreationDate().toString() : null)).append(",");
    sb.append(js("atrGeneralObjective", p.getAtrGeneralObjective())).append(",");
    sb.append(js("atrSpecificObjectives", p.getAtrSpecificObjectives())).append(",");
    sb.append(js("atrStatus", p.getAtrStatus() != null ? p.getAtrStatus().name() : null)).append(",");
    sb.append(js("atrNumberOfAttempts", p.getAtrNumberOfAttempts().toString())).append(",");
    sb.append(js("atrObservations", p.getAtrObservations())).append(",");
    sb.append(js("rutaFormatoA", p.getRutaFormatoA())).append(",");
    sb.append(js("rutaAnteproyecto", p.getRutaAnteproyecto()));
    sb.append("}");
    return sb.toString();
}

    private static String js(String k, String v) {
        return quote(k) + ":" + quote(v == null ? "" : v);
    }

    private static String quote(String s) {
        return "\"" + s.replace("\"", "\\\"") + "\"";
    }
    
}
