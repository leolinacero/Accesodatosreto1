import lombok.Data;

@Data

public class Pelicula {
    private int id;
    private String titulo;
    private int anho;
    private String director;
    private String genero;

    public Pelicula(int id, String titulo, int anho, String director, String genero) {
        this.id = id;
        this.titulo = titulo;
        this.anho = anho;
        this.director = director;
        this.genero = genero;

    }





}
