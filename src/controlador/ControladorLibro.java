package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Libro;
import modelo.LibroDAO;
import vista.Libros;
import java.sql.Date;
import javax.swing.JOptionPane;
import modelo.ValidacionException;

public class ControladorLibro implements ActionListener {
    LibroDAO dao = new LibroDAO();
    Libro l = new Libro();
    Libros vistaLibros = new Libros();
    DefaultTableModel modelo = new DefaultTableModel();
    
    public ControladorLibro(Libros l) {
        this.vistaLibros = l;
        // Agregar listener a botón listar/refrescar
        this.vistaLibros.btnListar.addActionListener(this);
        // Agregar listener a botón crear
        this.vistaLibros.btnCrear.addActionListener(this);
        // Agregar listener a botón modificar
        this.vistaLibros.btnModificar.addActionListener(this);
        // Agregar listener a botón modificar Ok
        this.vistaLibros.btnModificarOk.addActionListener(this);
        // Agregar listener a botón eliminar
        this.vistaLibros.btnEliminar.addActionListener(this);
        // Agregar listener a botón buscar
        this.vistaLibros.btnBuscar.addActionListener(this);
        // Lista la tabla automáticamente
        listar(vistaLibros.tablaLibros);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // Si botón listar es accionado, ejecuta método listar()
        if (e.getSource() == vistaLibros.btnListar) {
            listar(vistaLibros.tablaLibros);
        }
        // Si botón crear es accionado, ejecuta método crear()
        if (e.getSource() == vistaLibros.btnCrear) {
            crear();
            limpiarTabla();
            listar(vistaLibros.tablaLibros);
        }
        //  Si botón modificar es accionado y una fila de la tabla ha sido..
        // seleccionada, entonces poblamos las entradas de texto para poder modificar
        if (e.getSource() == vistaLibros.btnModificar) {
            recuperarDatosTabla();
        }
        // Confirma modificaciones y envía datos a la BD
        if (e.getSource() == vistaLibros.btnModificarOk) {
            modificar();
            limpiarTabla();
            listar(vistaLibros.tablaLibros);
        }
        // Si botón eliminar es accionado, elimina la fila
        if (e.getSource() == vistaLibros.btnEliminar) {
            eliminar();
            limpiarTabla();
            listar(vistaLibros.tablaLibros);
        }
        // Si botón buscar es accionado, busca el dato ingresado
        if (e.getSource() == vistaLibros.btnBuscar) {
            buscar(vistaLibros.tablaLibros);
        }
    }
    
    public void listar(JTable tabla) {
        modelo = (DefaultTableModel)tabla.getModel();
        
        // Limpiar el modelo de la tabla para evitar duplicación
        modelo.setRowCount(0);
        
        List<Libro> lista = dao.listar();
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        
        // Creamos un array de objetos con 7 llaves
        Object[]object = new Object[7];
        
        // Iteramos la lista obtenida e insertamos en el objeto anteriormente creado
        for (int i = 0; i < lista.size(); i++) {
            object[0] = lista.get(i).getId();
            object[1] = lista.get(i).getIsbn();
            object[2] = lista.get(i).getTitulo();
            object[3] = lista.get(i).getEditorial();
            object[4] = lista.get(i).getEjemplares_disponibles();
            
            java.sql.Date fechaSql = lista.get(i).getFecha_publicacion();
            if (fechaSql != null) {
                object[5] = sdf.format(fechaSql);
            } else {
                object[5] = "";
            }
            
            object[6] = lista.get(i).getGenero();
            modelo.addRow(object);
        }
        
        // Insertamos el modelo en la tabla de la vista
        vistaLibros.tablaLibros.setModel(modelo);
        
        vistaLibros.txtBuscar.setText("");
    }
    
    public void crear() {
        // Obtener valores desde la vista
        int isbn = (Integer) vistaLibros.txtIsbn.getValue();
        String titulo = vistaLibros.txtTitulo.getText();
        String editorial = vistaLibros.txtEditorial.getText();
        int ejemplaresDisponibles = (Integer) vistaLibros.txtEjemplaresDisponibles.getValue();
        String fechaTexto = vistaLibros.txtFechaPublicacion.getText();
        String genero = vistaLibros.txtGenero.getText();
        try {
            // Validar datos y establecerlos en el modelo
            l.setIsbn(isbn);
            l.setTitulo(titulo);
            l.setEditorial(editorial);
            l.setEjemplares_disponibles(ejemplaresDisponibles);
            java.sql.Date fechaSql = validarFormatoFecha(fechaTexto);
            l.setFecha_publicacion(fechaSql);
            l.setGenero(genero);
            
            // Crear libro en la base de datos
            boolean esExitoso = dao.crear(l);
            if (esExitoso) {
                JOptionPane.showMessageDialog(vistaLibros, "Libro agregado con éxito.");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(vistaLibros, "Ha fallado la inserción en la base de datos.", "Error base de datos", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ValidacionException e) {
            JOptionPane.showMessageDialog(vistaLibros, e.getMessage(), "Error de validación", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(vistaLibros, "El formato de fecha es incorrecto y/o vacío. Asegúrese de usar dd-MM-yyyy.", "Error de validación", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaLibros, "Ha ocurrido un error inesperado. Contacte al administrador.", "Error desconocido", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void modificar() {
        int id = (Integer) vistaLibros.txtId.getValue();
        int isbn = (Integer) vistaLibros.txtIsbn.getValue();
        String titulo = vistaLibros.txtTitulo.getText();
        String editorial = vistaLibros.txtEditorial.getText();
        int ejemplaresDisponibles = (Integer) vistaLibros.txtEjemplaresDisponibles.getValue();
        String fechaTexto = vistaLibros.txtFechaPublicacion.getText();
        String genero = vistaLibros.txtGenero.getText();

        try {
            l.setId(id);
            l.setIsbn(isbn);
            l.setTitulo(titulo);
            l.setEditorial(editorial);
            l.setEjemplares_disponibles(ejemplaresDisponibles);
            java.sql.Date fechaSql = validarFormatoFecha(fechaTexto);
            l.setFecha_publicacion(fechaSql);
            l.setGenero(genero);

            boolean esExitoso = dao.modificar(l);
            if (esExitoso) {
                JOptionPane.showMessageDialog(vistaLibros, "Libro actualizado con éxito.");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(vistaLibros, "Ha fallado la modificación en la base de datos.", "Error base de datos", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ValidacionException ex) {
            JOptionPane.showMessageDialog(vistaLibros, ex.getMessage(), "Error de validación", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(vistaLibros, "El formato de fecha es incorrecto y/o vacío. Asegúrese de usar dd-MM-yyyy.", "Error de validación", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vistaLibros, "Ha ocurrido un error inesperado. Contacte al administrador.", "Error desconocido", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void eliminar() {
        int fila = vistaLibros.tablaLibros.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vistaLibros, "Debe seleccionar una fila.", "Alerta", JOptionPane.WARNING_MESSAGE);
        } else {
            int id = Integer.parseInt((String)vistaLibros.tablaLibros.getValueAt(fila, 0).toString());
            boolean esExitoso = dao.eliminar(id);
            if (esExitoso) {
                JOptionPane.showMessageDialog(vistaLibros, "Libro eliminado con éxito.");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(vistaLibros, "Ha fallado la eliminación en la base de datos.", "Error base de datos", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void buscar(JTable tabla) {
        modelo = (DefaultTableModel)tabla.getModel();
        
        // Limpiar el modelo de la tabla para evitar duplicación
        modelo.setRowCount(0);
        
        String terminoBusqueda = vistaLibros.txtBuscar.getText();
        List<Libro> lista = dao.buscar(terminoBusqueda);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        
        // Creamos un array de objetos con 7 llaves
        Object[]object = new Object[7];
        
        // Iteramos la lista obtenida e insertamos en el objeto anteriormente creado
        for (int i = 0; i < lista.size(); i++) {
            object[0] = lista.get(i).getId();
            object[1] = lista.get(i).getIsbn();
            object[2] = lista.get(i).getTitulo();
            object[3] = lista.get(i).getEditorial();
            object[4] = lista.get(i).getEjemplares_disponibles();
            
            java.sql.Date fechaSql = lista.get(i).getFecha_publicacion();
            if (fechaSql != null) {
                object[5] = sdf.format(fechaSql);
            } else {
                object[5] = "";
            }
            
            object[6] = lista.get(i).getGenero();
            modelo.addRow(object);
        }
        
        // Insertamos el modelo en la tabla de la vista
        vistaLibros.tablaLibros.setModel(modelo);
    }
    
    private java.sql.Date validarFormatoFecha(String fechaTexto) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        java.util.Date fechaUtil = sdf.parse(fechaTexto);
        java.sql.Date fechaSql = new java.sql.Date(fechaUtil.getTime());
        return fechaSql;
    }
    
    private void limpiarCampos() {
        vistaLibros.txtId.setValue(0);
        vistaLibros.txtIsbn.setValue(0);
        vistaLibros.txtTitulo.setText("");
        vistaLibros.txtEditorial.setText("");
        vistaLibros.txtEjemplaresDisponibles.setValue(0);
        vistaLibros.txtFechaPublicacion.setText("");
        vistaLibros.txtGenero.setText("");
    }
    
    private void recuperarDatosTabla() {
        int fila = vistaLibros.tablaLibros.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vistaLibros, "Debe seleccionar una fila.", "Alerta", JOptionPane.WARNING_MESSAGE);
        } else {
            int id = Integer.parseInt((String)vistaLibros.tablaLibros.getValueAt(fila, 0).toString());
            int isbn = Integer.parseInt((String)vistaLibros.tablaLibros.getValueAt(fila, 1).toString());
            String titulo = (String)vistaLibros.tablaLibros.getValueAt(fila, 2).toString();
            String editorial = (String)vistaLibros.tablaLibros.getValueAt(fila, 3).toString();
            int ejemplaresDisponibles = Integer.parseInt((String)vistaLibros.tablaLibros.getValueAt(fila, 4).toString());
            String fechaPublicacion = (String)vistaLibros.tablaLibros.getValueAt(fila, 5).toString();
            String genero = (String)vistaLibros.tablaLibros.getValueAt(fila, 6).toString();
            vistaLibros.txtId.setValue(id);
            vistaLibros.txtIsbn.setValue(isbn);
            vistaLibros.txtTitulo.setText(titulo);
            vistaLibros.txtEditorial.setText(editorial);
            vistaLibros.txtEjemplaresDisponibles.setValue(ejemplaresDisponibles);
            vistaLibros.txtFechaPublicacion.setText(fechaPublicacion);
            vistaLibros.txtGenero.setText(genero);
        }
    }
    
    private void limpiarTabla() {
        for (int i = 0; i < vistaLibros.tablaLibros.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }
}
