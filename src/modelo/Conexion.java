package modelo;

import java.sql.Connection;
import java.sql.DriverManager;

// Clase para realizar conexión con la base de datos
// Mantener simple. Puedes agregar un mensaje para la excepción
public class Conexion {
    Connection con;
    String url = "jdbc:oracle:thin:@localhost:1521:orcl";
    String user = "javier";
    String pass = "123";
    
    public Connection conectar() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
        }
        return con;
    }
}
