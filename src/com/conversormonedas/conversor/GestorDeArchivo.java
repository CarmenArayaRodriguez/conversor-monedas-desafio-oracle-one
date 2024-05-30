package com.conversormonedas.conversor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class GestorDeArchivo {
    private List<Conversion> historialConversiones;
    private String nombreArchivo;
    // Directorio fijo donde todos los archivos serán guardados
    private static final String DIRECTORIO = "historialDeConversiones";

    public GestorDeArchivo(String nombreArchivo, List<Conversion> historialConversiones) {
        this.nombreArchivo = nombreArchivo;
        this.historialConversiones = historialConversiones;
        asegurarDirectorio();
    }

    private void asegurarDirectorio() {
        File dir = new File(DIRECTORIO);
        if (!dir.exists()) {
            boolean wasCreated = dir.mkdirs(); // Crea el directorio si no existe
            if (!wasCreated) {
                System.out.println("No se pudo crear el directorio: " + DIRECTORIO);
            }
        }
    }

    public void registrarConversion(Conversion conversion) {
        historialConversiones.add(conversion);
        guardarHistorialEnArchivo();
    }

    private void guardarHistorialEnArchivo() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting()
                .create();

        // Usar el directorio fijo para guardar los archivos
        String rutaCompleta = DIRECTORIO + File.separator + nombreArchivo;
        try (FileWriter writer = new FileWriter(rutaCompleta)) {
            gson.toJson(historialConversiones, writer);
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    public void mostrarHistorial() {
        if (historialConversiones.isEmpty()) {
            System.out.println("No hay conversiones registradas.");
        } else {
            for (Conversion conv : historialConversiones) {
                System.out.println(conv);
            }
        }
    }
}
