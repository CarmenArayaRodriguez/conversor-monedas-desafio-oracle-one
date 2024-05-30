package com.conversormonedas.conversor;

import com.conversormonedas.configuracion.Configuracion;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TasaDeCambio {
    private HttpClient client;
    private Map<String, Double> tasasDeCambio;
    private String monedaBaseActual;

    public TasaDeCambio() {
        this.client = HttpClient.newHttpClient();
        this.tasasDeCambio = new HashMap<>();
    }

    public void cargarTasas(String monedaBase) {
        this.monedaBaseActual = monedaBase;
        String apiKey = Configuracion.obtenerPropiedad("api_key");
        if (apiKey == null) {
            System.out.println("La clave API no está configurada.");
            return;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.exchangerate-api.com/v4/latest/" + monedaBase))
                .header("api-key", apiKey)
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::almacenarTasas)
                .join();
    }

    private String almacenarTasas(String cuerpoRespuesta) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(cuerpoRespuesta, JsonObject.class);
        JsonObject rates = jsonObject.getAsJsonObject("rates");

        if (rates == null) {
            System.out.println("No se encontraron tasas en la respuesta de la API.");
            return "Error: No se encontraron tasas.";
        }

        List<String> monedasIncluidas = Arrays.asList("ARS", "BOB", "BRL", "CLP", "COP", "USD");
        tasasDeCambio.clear();

        for (String clave : rates.keySet()) {
            if (monedasIncluidas.contains(clave)) {
                tasasDeCambio.put(clave, rates.get(clave).getAsDouble());
            }
        }

        return "Tasas filtradas y almacenadas correctamente.";
    }

    public boolean tasaDisponible(String moneda) {
        return tasasDeCambio.containsKey(moneda);
    }

    public void mostrarTasas() {
        System.out.println("Tasas de cambio actuales basadas en " + monedaBaseActual + ":");
        tasasDeCambio.forEach((moneda, tasa) -> System.out.println(moneda + ": " + tasa));
    }

    public double obtenerTasa(String moneda) {
        return tasasDeCambio.getOrDefault(moneda, 0.0);
    }

}
