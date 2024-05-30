package com.conversormonedas.conversor;

import java.time.LocalDateTime;

class Conversion {
    private LocalDateTime timestamp;
    private String monedaOrigen;
    private String monedaDestino;
    private double cantidad;
    private double valorConvertido;

    public Conversion(LocalDateTime timestamp, String monedaOrigen, String monedaDestino, double cantidad, double valorConvertido) {
        this.timestamp = timestamp;
        this.monedaOrigen = monedaOrigen;
        this.monedaDestino = monedaDestino;
        this.cantidad = cantidad;
        this.valorConvertido = valorConvertido;
    }

    // Obtiene una representación textual de la conversión.
    @Override
    public String toString() {
        return String.format("%s: %f %s convertidos a %f %s",
                timestamp, cantidad, monedaOrigen, valorConvertido, monedaDestino);
    }
}
