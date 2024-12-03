package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

// DAO se encarga de realizar la comunicación directa a la base de datos y..
// retornar los datos al controlador. Una vez están en el controlador,
// el controlador valida los datos obtenidos/enviados y son devueltos a la vista (dependiendo del caso)
public class LibroDAO {
    PreparedStatement ps;
    ResultSet rs;
    Conexion c = new Conexion();
    Connection con;
    
    public List listar() {
        List<Libro> lista = new ArrayList<>();
        // Se realiza ORDER BY ID DESC para obtener una lista descendiente y ordenada
        String sql = "SELECT * FROM LIBRO ORDER BY ID DESC";
        try {
            con = c.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {                
                Libro l = new Libro();
                l.setId(rs.getInt(1));
                l.setIsbn(rs.getInt(2));
                l.setTitulo(rs.getString(3));
                l.setEditorial(rs.getString(4));
                l.setEjemplares_disponibles(rs.getInt(5));
                l.setFecha_publicacion(rs.getDate(6));
                l.setGenero(rs.getString(7));
                lista.add(l);
            }
        } catch (Exception e) {
        }
        return lista;
    }
    
    public boolean crear(Libro l) {
        String sql = "INSERT INTO LIBRO(ISBN, TITULO, EDITORIAL, EJEMPLARES_DISPONIBLES, FECHA_PUBLICACION, GENERO) VALUES(?, ?, ?, ?, ?, ?)";
        
        try {
            con = c.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, l.getIsbn());
            ps.setString(2, l.getTitulo());
            ps.setString(3, l.getEditorial());
            ps.setInt(4, l.getEjemplares_disponibles());
            ps.setDate(5, l.getFecha_publicacion());
            ps.setString(6, l.getGenero());
            ps.executeUpdate();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public boolean modificar(Libro l) {
        String sql = "UPDATE LIBRO SET ISBN = ?, TITULO = ?, EDITORIAL = ?, EJEMPLARES_DISPONIBLES = ?, FECHA_PUBLICACION = ?, GENERO = ? WHERE ID = ?";
        
        try {
            con = c.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, l.getIsbn());
            ps.setString(2, l.getTitulo());
            ps.setString(3, l.getEditorial());
            ps.setInt(4, l.getEjemplares_disponibles());
            ps.setDate(5, l.getFecha_publicacion());
            ps.setString(6, l.getGenero());
            ps.setInt(7, l.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public boolean eliminar(int id) {
        String sql = "DELETE FROM LIBRO WHERE ID = " + id;
        
        try {
            con = c.conectar();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public List<Libro> buscar(String terminoBusqueda) {
        List<Libro> lista = new ArrayList<>();

        // Utilizar LIKE para realizar una búsqueda parcial en todos los campos
        // Agrega método LOWER() para no discriminar por capitalizaciones
        String sql = "SELECT * FROM LIBRO WHERE LOWER(ISBN) LIKE LOWER(?) OR LOWER(TITULO) LIKE LOWER(?) OR LOWER(EDITORIAL) LIKE LOWER(?) OR LOWER(EJEMPLARES_DISPONIBLES) LIKE LOWER(?) OR LOWER(FECHA_PUBLICACION) LIKE LOWER(?) OR LOWER(GENERO) LIKE LOWER(?) ORDER BY ID DESC";

        try {
            con = c.conectar();
            ps = con.prepareStatement(sql);

            ps.setString(1, "%" + terminoBusqueda + "%");
            ps.setString(2, "%" + terminoBusqueda + "%");
            ps.setString(3, "%" + terminoBusqueda + "%");
            ps.setString(4, "%" + terminoBusqueda + "%");
            ps.setString(5, "%" + terminoBusqueda + "%");
            ps.setString(6, "%" + terminoBusqueda + "%");

            rs = ps.executeQuery();

            while (rs.next()) {
                Libro l = new Libro();
                l.setId(rs.getInt("ID"));
                l.setIsbn(rs.getInt("ISBN"));
                l.setTitulo(rs.getString("TITULO"));
                l.setEditorial(rs.getString("EDITORIAL"));
                l.setEjemplares_disponibles(rs.getInt("EJEMPLARES_DISPONIBLES"));
                l.setFecha_publicacion(rs.getDate("FECHA_PUBLICACION"));
                l.setGenero(rs.getString("GENERO"));
                lista.add(l);
            }
        } catch (Exception e) {
        }

        return lista;
    }
}
