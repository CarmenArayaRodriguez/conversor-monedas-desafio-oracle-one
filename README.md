# Conversor de Monedas

## Descripción
Este proyecto es un conversor de monedas que permite a los usuarios obtener tasas de cambio actuales y convertir montos entre varias monedas. Utiliza la API de ExchangeRate-API para recuperar datos en tiempo real y guarda un historial de conversiones en un archivo JSON.

## Características
- **Obtener y mostrar tasas de cambio:** Permite a los usuarios ver tasas de cambio actuales para una lista de monedas seleccionadas.
- **Convertir montos entre diversas monedas:** Facilita la conversión de montos entre monedas usando tasas de cambio en tiempo real.
- **Validación de entradas:** Asegura que las entradas de los usuarios sean correctas antes de realizar cualquier cálculo.
- **Interfaz de usuario amigable a través de la consola:** Proporciona una experiencia de usuario clara y sencilla directamente desde la consola.
- **Registro de historial:** Guarda cada conversión realizada en un archivo JSON para referencia futura.

## Cómo Empezar
1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/CarmenArayaRodriguez/conversor-monedas-desafio-oracle-one.git

2. **Configuración**
Para ejecutar este proyecto, necesitarás una clave API de ExchangeRate-API. Coloca tu clave API en el lugar indicado en el código.

3. **Ejecución**
Compila y ejecuta el programa usando tu IDE favorito o a través de la línea de comandos:

```bash
javac com.conversormonedas.conversor.ConversorMonedas.java
java com.conversormonedas.conversor.ConversorMonedas
```

## Documentación de la API
Para obtener más información sobre las capacidades de la API de ExchangeRate-API y cómo utilizarla, puedes visitar la [Documentación Oficial de ExchangeRate-API](https://www.exchangerate-api.com/docs/overview). Este recurso es excelente para entender todas las funcionalidades que esta API ofrece y cómo puedes integrarlas de manera más efectiva en tu proyecto.

## Uso
Simplemente sigue las instrucciones en la consola. Selecciona si deseas ver tasas de cambio o convertir monedas, introduce las monedas de interés y los montos, y el sistema hará el resto. Los resultados de las conversiones se guardarán automáticamente en un archivo JSON en el directorio historialDeConversiones.

