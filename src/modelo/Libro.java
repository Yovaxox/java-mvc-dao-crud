package modelo;

import java.sql.Date;
import javax.swing.JOptionPane;

// Setters se encargan de validar los datos ingresados
// Para validar formato fecha, se recomienda realizarlo en el controlador
public class Libro {
    int id;
    int isbn;
    String titulo;
    String editorial;
    int ejemplares_disponibles;
    Date fecha_publicacion;
    String genero;

    public Libro() {
    }

    public Libro(int id, int isbn, String titulo, String editorial, int ejemplares_disponibles, Date fecha_publicacion, String genero) {
        this.id = id;
        this.isbn = isbn;
        this.titulo = titulo;
        this.editorial = editorial;
        this.ejemplares_disponibles = ejemplares_disponibles;
        this.fecha_publicacion = fecha_publicacion;
        this.genero = genero;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        if (isbn <= 0) {
            throw new ValidacionException("El ISBN debe ser un número positivo.");
        }
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new ValidacionException("El título no puede estar vacío.");
        }
        if (titulo.length() > 255) {
            throw new ValidacionException("El título no puede ser mayor a 255 carácteres.");
        }
        this.titulo = titulo;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        if (editorial == null || editorial.trim().isEmpty()) {
            throw new ValidacionException("La editorial no puede estar vacía.");
        }
        if (editorial.length() > 100) {
            throw new ValidacionException("La editorial no puede ser mayor a 100 carácteres.");
        }
        this.editorial = editorial;
    }

    public int getEjemplares_disponibles() {
        return ejemplares_disponibles;
    }

    public void setEjemplares_disponibles(int ejemplares_disponibles) {
        if (ejemplares_disponibles < 0) {
            throw new ValidacionException("Los ejemplares disponibles no pueden ser negativos.");
        }
        this.ejemplares_disponibles = ejemplares_disponibles;
    }

    public Date getFecha_publicacion() {
        return fecha_publicacion;
    }

    public void setFecha_publicacion(Date fecha_publicacion) {
        this.fecha_publicacion = fecha_publicacion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        if (genero == null || genero.trim().isEmpty()) {
            throw new ValidacionException("El género no puede estar vacío.");
        }
        if (genero.length() > 100) {
            throw new ValidacionException("El género no puede ser mayor a 100 carácteres.");
        }
        this.genero = genero;
    }
}
