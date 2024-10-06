import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

     /**
     * Lee un archivo CSV y devuelve una lista con los datos de las películas.
     * Devulve Una lista de Strings, donde cada uno contiene los datos de una película.
     */
    public static List<String[]> leercsv(String archivocsv) {
        List<String[]> peliculas = new ArrayList<>();
        String linea;
        try (BufferedReader br = new BufferedReader(new FileReader(archivocsv))) {
            while ((linea = br.readLine()) != null) {

                String[] campos = linea.split(",");
                peliculas.add(campos);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo CSV: " + e.getMessage());
        }
        return peliculas;
    }

    /**
     * Lee un archivo de plantilla HTML y devuelve su contenido como una cadena de texto.
     * Devuelve El contenido del archivo de plantilla como un String.
     */
    public static String leerPlantilla(String planti) {
        StringBuilder plantilla = new StringBuilder();
        try {
            List<String> lineas = Files.readAllLines(Paths.get(planti));
            for (String linea : lineas) {
                plantilla.append(linea).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error al leer la plantilla HTML: " + e.getMessage());
        }
        return plantilla.toString();
    }

    /**
     * Genera archivos HTML para cada película en la lista proporcionada, utilizando una plantilla HTML.
     * Los archivos generados se guardan en una carpeta 'salida'.
     */
    public static void generaht(String plantilla, List<String[]> peliculas) {

        File carpetaSalida = new File("salida");
        if (!carpetaSalida.exists()) {
            carpetaSalida.mkdir();
        }


        for (File file : carpetaSalida.listFiles()) {
            file.delete();
        }


        for (String[] pelicula : peliculas) {
            String contenidoHTML = plantilla
                    .replace("%%1%%", pelicula[0])
                    .replace("%%2%%", pelicula[1])
                    .replace("%%3%%", pelicula[2])
                    .replace("%%4%%", pelicula[3])
                    .replace("%%5%%", pelicula[4]);


            String nombre = "salida/" + pelicula[1] + " - " + pelicula[0] + ".html";

            // Guardar el archivo HTML
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombre))) {
                writer.write(contenidoHTML);
            } catch (IOException e) {
                System.err.println("Error al escribir el archivo HTML: " + e.getMessage());
            }
        }
    }
    /**
     * Método principal que coordina la lectura de los archivos CSV y plantilla, y genera los archivos HTML.
     */

    public static void main(String[] args) {

        List<String[]> peliculas = leercsv("peliculas.csv");
        String plantillaHTML = leerPlantilla("plantilla.html");


        generaht(plantillaHTML, peliculas);

        System.out.println("Archivos HTML generados correctamente en la carpeta 'salida'.");
    }
}