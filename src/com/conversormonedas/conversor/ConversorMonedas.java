package com.conversormonedas.conversor;

import com.conversormonedas.configuracion.Configuracion;
import java.net.http.HttpClient;
import java.time.LocalDateTime;
import java.util.*;

public class ConversorMonedas {
    private static HttpClient client = HttpClient.newHttpClient();
    private static TasaDeCambio tasaDeCambio = new TasaDeCambio();
    private static GestorDeArchivo gestorDeArchivo;
    private static String nombreArchivo;

    public static void ejecutar(String[] args) {
        // Carga las propiedades necesarias como claves API.
        Configuracion.cargarPropiedades();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el nombre del archivo donde deseas guardar el historial de conversiones (ej: conversiones.json):");
        nombreArchivo = scanner.nextLine();

        if (!nombreArchivo.endsWith(".json")) {
            nombreArchivo += ".json";
        }

        // Se prepara la lista donde se guardarán las conversiones y se crea el gestor de archivo.
        List<Conversion> historialConversiones = new ArrayList<>();
        gestorDeArchivo = new GestorDeArchivo(nombreArchivo, historialConversiones);

        // Se solicita la moneda base para las conversiones.
        String monedaBase = seleccionarMonedaBase(scanner);
        tasaDeCambio.cargarTasas(monedaBase);

        int opcion;

        do {
            System.out.println("Bienvenido al Conversor de Monedas. Selecciona una opción:");
            System.out.println("1. Mostrar tasas de cambio");
            System.out.println("2. Convertir moneda");
            System.out.println("3. Mostrar historial de conversiones");
            System.out.println("4. Salir");
            while (!scanner.hasNextInt()) {
                System.out.println("Por favor, introduce un número válido.");
                scanner.next(); //
            }
            opcion = scanner.nextInt();

            // Se ejecuta la opción seleccionada.
            switch (opcion) {
                case 1:
                    tasaDeCambio.mostrarTasas();
                    break;
                case 2:
                    convertirMoneda(scanner);
                    break;
                case 3:
                    gestorDeArchivo.mostrarHistorial();
                    break;
                default:
                    if (opcion != 4) {
                        System.out.println("Opción no válida, intenta de nuevo.");
                    }
                    break;
            }
        } while (opcion != 4);

        System.out.println("Gracias por usar el conversor de monedas.");
        scanner.close();
    }

    private static String seleccionarMonedaBase(Scanner scanner) {
        System.out.println("Introduce la moneda base para las conversiones (ejemplo: USD):");
        return scanner.next().toUpperCase();
    }

    private static void convertirMoneda(Scanner scanner) {
        System.out.println("Introduce la moneda de origen (ejemplo: USD):");
        String monedaOrigen = scanner.next().toUpperCase();

        // Verificar si la moneda de origen está en la lista de monedas disponibles
        if (!tasaDeCambio.tasaDisponible(monedaOrigen)) {
            System.out.println("Moneda de origen no disponible o escrita incorrectamente. Asegúrate de usar el código correcto (ej. USD, CLP).");
            return;  // Salir del método si la moneda no está disponible
        }

        System.out.println("Introduce la moneda de destino (ejemplo: CLP):");
        String monedaDestino = scanner.next().toUpperCase();

        // Verificar si la moneda de destino está en la lista de monedas disponibles
        if (!tasaDeCambio.tasaDisponible(monedaDestino)) {
            System.out.println("Moneda de destino no disponible o escrita incorrectamente. Asegúrate de usar el código correcto (ej. USD, CLP).");
            return;  // Salir del método si la moneda no está disponible
        }

        System.out.println("Introduce la cantidad a convertir:");
        double cantidad = scanner.nextDouble();

        double tasaOrigen = tasaDeCambio.obtenerTasa(monedaOrigen);
        double tasaDestino = tasaDeCambio.obtenerTasa(monedaDestino);

        if (tasaOrigen == 0 || tasaDestino == 0) {
            System.out.println("Una o ambas monedas no están disponibles.");
            return;
        }

        double valorConvertido = (cantidad / tasaOrigen) * tasaDestino;
        System.out.printf("%.2f %s equivale a %.2f %s\n", cantidad, monedaOrigen, valorConvertido, monedaDestino);

        // Se registra la conversión en el historial.
        Conversion nuevaConversion = new Conversion(LocalDateTime.now(), monedaOrigen, monedaDestino, cantidad, valorConvertido);
        gestorDeArchivo.registrarConversion(nuevaConversion);
    }


}