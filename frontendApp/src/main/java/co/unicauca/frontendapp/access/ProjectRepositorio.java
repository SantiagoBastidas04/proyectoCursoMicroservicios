/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.frontendapp.access;

import co.unicauca.frontendapp.entities.ProjectModel;
import co.unicauca.frontendapp.entities.StatusEnum;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.net.http.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
    public List<ProjectModel> listarPorEstadoYCorreo(StatusEnum estado, String emailProfesor) {
        List<ProjectModel> proyectos = new ArrayList<>();
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(BASE + "/" + emailProfesor + "/" + estado.name()))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());

            if (res.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

                proyectos = mapper.readValue(res.body(), new TypeReference<List<ProjectModel>>() {
                });

            } else {
                System.err.println("Error al obtener proyectos: " + res.statusCode() + " - " + res.body());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return proyectos; // si falla o el status != 200, devuelve lista vacía
    }

    @Override
    public List<ProjectModel> listarPorEmailProfesor(String emailProfesor) {
        List<ProjectModel> proyectos = new ArrayList<>();
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(BASE + "/director/" + emailProfesor))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());

            if (res.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

                proyectos = mapper.readValue(res.body(), new TypeReference<List<ProjectModel>>() {
                });

            } else {
                System.err.println("Error al obtener proyectos: " + res.statusCode() + " - " + res.body());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return proyectos; // si falla o el status != 200, devuelve lista vacía
    }
    @Override
    public boolean actualizarProyecto(ProjectModel proyecto) {
    try {
        // Construir la URL: PUT /api/proyectos/{id}
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE + "/" + proyecto.getAtrId()+"/actualizar"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(toJson(proyecto)))
                .build();

        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            System.out.println("Proyecto actualizado correctamente: " + proyecto.getAtrTitle());
            return true;
        } else {
            System.err.println("Error al actualizar proyecto: " + response.statusCode() + " - " + response.body());
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    return false;
}
    @Override
    public boolean avanzarEstado(ProjectModel proyecto) {
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(BASE + "/" + proyecto.getAtrId() + "/avanzar"))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());

            if (res.statusCode() == 200) {
                System.out.println("Proyecto " + proyecto.getAtrId() + " avanzado exitosamente.");
                return true;
            } else {
                System.err.println("Error al avanzar proyecto: " + res.statusCode() + " - " + res.body());
                return false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean retrocederEstado(ProjectModel proyecto) {
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(BASE + "/" + proyecto.getAtrId() + "/retroceder"))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());
            if (res.statusCode() == 200) {
                System.out.println("Proyecto " + proyecto.getAtrId() + " retrocedido exitosamente.");
                return true;
            } else {
                System.err.println("Error al retroceder proyecto: " + res.statusCode() + " - " + res.body());
                return false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean corregirEstado(ProjectModel proyecto) {
        try {
            // Construir la petición PUT al endpoint del backend
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(BASE + "/" + proyecto.getAtrId() + "/corregir"))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.noBody())
                    .build();

            // Enviar la petición
            HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());

            // Verificar el código de estado HTTP
            if (res.statusCode() == 200) {
                System.out.println("Proyecto " + proyecto.getAtrId() + " corregido exitosamente.");
                return true;
            } else {
                System.err.println("Error al corregir proyecto: " + res.statusCode() + " - " + res.body());
                return false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public List<ProjectModel> listarPendientes() {
        List<ProjectModel> proyectos = new ArrayList<>();
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(BASE + "/state/PRESENTADO_A_COORDINADOR"))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());

            if (res.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

                proyectos = mapper.readValue(res.body(), new TypeReference<List<ProjectModel>>() {
                });

            } else {
                System.err.println("Error al obtener proyectos: " + res.statusCode() + " - " + res.body());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return proyectos; // si falla o el status != 200, devuelve lista vacía
    }

    @Override
    public List<ProjectModel> listarPorEmailEstudiante(String emailEstudiante) {
        List<ProjectModel> proyectos = new ArrayList<>();
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(BASE + "/student/" + emailEstudiante))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());

            if (res.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

                proyectos = mapper.readValue(res.body(), new TypeReference<List<ProjectModel>>() {
                });

            } else {
                System.err.println("Error al obtener proyectos: " + res.statusCode() + " - " + res.body());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return proyectos; // si falla o el status != 200, devuelve lista vacía
    }

    // ----------------- Helpers -----------------
    private static String enc(String s) {
        return URLEncoder.encode(s == null ? "" : s, StandardCharsets.UTF_8);
    }

    private static String toJson(ProjectModel p) {
        StringBuilder sb = new StringBuilder("{");
        //sb.append(js("atrId", p.getAtrId() != null ? p.getAtrId().toString() : null)).append(",");
        sb.append(js("atrTitle", p.getAtrTitle())).append(",");
        sb.append(js("rutaCartaAceptacion", p.getAtrCartaAceptacion())).append(",");
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
