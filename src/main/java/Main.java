import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    // Método para leer el archivo CSV y devolver una lista de películas
    public static List<String[]> leerCSV(String archivoCSV) {
        List<String[]> peliculas = new ArrayList<>();
        String linea;
        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
            while ((linea = br.readLine()) != null) {
                // Dividimos la línea por comas para obtener los campos
                String[] campos = linea.split(",");
                peliculas.add(campos);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo CSV: " + e.getMessage());
        }
        return peliculas;
    }

    // Método para leer la plantilla HTML
    public static String leerPlantilla(String archivoPlantilla) {
        StringBuilder plantilla = new StringBuilder();
        try {
            List<String> lineas = Files.readAllLines(Paths.get(archivoPlantilla));
            for (String linea : lineas) {
                plantilla.append(linea).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error al leer la plantilla HTML: " + e.getMessage());
        }
        return plantilla.toString();
    }

    // Método para generar los archivos HTML
    public static void generarHTML(String plantilla, List<String[]> peliculas) {
        // Asegurarse de que la carpeta "salida" esté vacía
        File carpetaSalida = new File("salida");
        if (!carpetaSalida.exists()) {
            carpetaSalida.mkdir();
        }

        // Limpiar la carpeta de salida antes de generar nuevos archivos
        for (File file : carpetaSalida.listFiles()) {
            file.delete();
        }

        // Iterar sobre las películas y generar un archivo HTML por cada una
        for (String[] pelicula : peliculas) {
            String contenidoHTML = plantilla
                    .replace("%%1%%", pelicula[0])
                    .replace("%%2%%", pelicula[1])
                    .replace("%%3%%", pelicula[2])
                    .replace("%%4%%", pelicula[3])
                    .replace("%%5%%", pelicula[4]);

            // Nombre del archivo basado en el título y el ID de la película
            String nombreArchivo = "salida/" + pelicula[1] + " - " + pelicula[0] + ".html";

            // Guardar el archivo HTML
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
                writer.write(contenidoHTML);
            } catch (IOException e) {
                System.err.println("Error al escribir el archivo HTML: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        // Leer archivo CSV y plantilla
        List<String[]> peliculas = leerCSV("peliculas.csv");
        String plantillaHTML = leerPlantilla("plantilla.html");

        // Generar los archivos HTML
        generarHTML(plantillaHTML, peliculas);

        System.out.println("Archivos HTML generados correctamente en la carpeta 'salida'.");
    }
}