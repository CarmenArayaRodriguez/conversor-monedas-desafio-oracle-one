package com.conversormonedas.configuracion;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuracion {
    private static Properties propiedades = new Properties();

    public static void cargarPropiedades() {
        try (FileInputStream input = new FileInputStream("resources/config.properties")) {
            // Se cargan las propiedades desde el archivo.
            propiedades.load(input);
        } catch (IOException e) {
            // Si ocurre un error al cargar las propiedades, se muestra un mensaje y se cierra la aplicación.
            System.out.println("Error al cargar el archivo de configuración: " + e.getMessage());
            System.exit(1); // Salir si no hay archivo de configuración
        }
    }

    // Permite obtener la clave de la API.
    public static String obtenerPropiedad(String clave) {
        return propiedades.getProperty(clave);
    }
}
