/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.frontendapp.entities;

/**
 *
 * @author Santiago Bastidas
 */
import okhttp3.*;

public class KeycloakAuthService {

    private final OkHttpClient client = new OkHttpClient();

    public String login(String username, String password) throws Exception {

        String url = "http://localhost:9090/realms/labproyectocurso/protocol/openid-connect/token";

        RequestBody formBody = new FormBody.Builder()
                .add("client_id", "lab-api")
                .add("client_secret", "BlEEQokSxnp6KvIeMRjJe6zXiH4Vq6yF")
                .add("grant_type", "password")
                .add("username", username)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new Exception("Error en login: " + response.code());
            }

            String json = response.body().string();

            // Extraer el access_token del JSON
            String token = json.split("\"access_token\":\"")[1].split("\"")[0];

            return token;
        }
    }
}