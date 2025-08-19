package com.aluracursos.literalura.client;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class ConsumoAPI {
    public String obtenerDatos(String url){
        System.out.println("url=" + url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException  | InterruptedException | RuntimeException e) {
            throw new RuntimeException(e);
        }

        return response.body();  //regresa el json de la respuesta
    }
}
