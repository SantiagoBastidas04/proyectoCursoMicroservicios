package co.unicauca.frontendapp.access;

import co.unicauca.frontendapp.entities.User;
import java.net.http.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class UserRepositorio implements IUserRepositorio {

    private static final String BASE = "http://localhost:8080/api/auth";
    private final HttpClient http = HttpClient.newHttpClient();

    @Override
    public boolean guardar(User usuario) {
        try {
            String json = toJson(usuario);
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(BASE + "/register"))
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
    public User iniciarSesion(String email, String contrasenia) {
        try {
            String query = "email=" + enc(email) + "&contrasenia=" + enc(contrasenia);
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(BASE + "/login?" + query))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());

            if (res.statusCode() == 401) {
                System.err.println("Credenciales incorrectas: " + res.body());
                return null;
            }
            if (res.statusCode() >= 400) {
                System.err.println("Error login: " + res.statusCode() + " - " + res.body());
                return null;
            }

            // === Parsear JSON con Jackson ===
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();

            // el JSON tiene la estructura {"message": "...", "usuario": { ... }}
            com.fasterxml.jackson.databind.JsonNode root = mapper.readTree(res.body());
            com.fasterxml.jackson.databind.JsonNode userNode = root.get("usuario");

            if (userNode == null) {
                System.err.println("No se encontró el objeto usuario en la respuesta");
                return null;
            }
            User user = mapper.treeToValue(userNode, User.class);
            return user;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // ----------------- Helpers -----------------
    private static String enc(String s) {
        return URLEncoder.encode(s == null ? "" : s, StandardCharsets.UTF_8);
    }

    private static String toJson(User u) {
        // Construimos JSON manual (simple). Si prefieres, usa Jackson en el cliente.
        StringBuilder sb = new StringBuilder("{");
        sb.append(js("nombre", u.getNombre())).append(",");
        sb.append(js("apellidos", u.getApellidos())).append(",");
        sb.append(js("celular", u.getCelular())).append(",");
        sb.append(js("email", u.getEmail())).append(",");
        sb.append(js("contrasenia", u.getContraseña())).append(",");
        sb.append(js("rol", u.getRol() != null ? u.getRol().name() : null)).append(",");
        sb.append(js("programa", u.getPrograma() != null ? u.getPrograma().name() : null));
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
