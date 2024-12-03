package modelo;

// Clase especial para crear una tipo de Exception para cláusula try catch
// que sirve para identificar si el error es de validación
public class ValidacionException extends RuntimeException {
    public ValidacionException(String mensaje) {
        super(mensaje);
    }
}
