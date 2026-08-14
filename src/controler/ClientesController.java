package controler;

import connection.Conexion;
import model.Clientes;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ClientesController {

    private Conexion conexion = new Conexion();


    // ==================== MÉTODO AGREGAR ====================
    public void agregar(Clientes clientes) {
        Connection con = conexion.getConexion();
        String query = "INSERT INTO clientes (nombres, apellidos, telefono, correo, direccion) VALUES (?,?,?,?,?)";

        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, clientes.getNombres());
            pst.setString(2, clientes.getApellidos());
            pst.setString(3, clientes.getTelefono());
            pst.setString(4, clientes.getCorreo());
            pst.setString(5, clientes.getDireccion());

            int resultado = pst.executeUpdate();

            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Cliente agregado exitosamente");
            } else {
                JOptionPane.showMessageDialog(null, "Error al agregar Cliente");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // ==================== MÉTODO ELIMINAR ====================
    public void eliminar(int id) {
        Connection con = conexion.getConexion();
        String query = "DELETE FROM clientes WHERE id_cliente = ?";

        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id);

            // CORREGIDO: Se añade la ejecución de la consulta
            int resultado = pst.executeUpdate();

            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Cliente eliminado exitosamente");
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar Cliente");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // ==================== MÉTODO EDITAR ====================
    public void editar(Clientes clientes) {
        Connection con = conexion.getConexion();
        String query = "UPDATE clientes SET nombres = ?, apellidos = ?, telefono = ?, correo = ?, direccion = ? WHERE id_cliente = ?";

        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, clientes.getNombres());
            pst.setString(2, clientes.getApellidos());
            pst.setString(3, clientes.getTelefono());
            pst.setString(4, clientes.getCorreo());
            pst.setString(5, clientes.getDireccion());
            pst.setInt(6, clientes.getId_cliente());

            int resultado = pst.executeUpdate();

            // CORREGIDO: Textos de los JOptionPane
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Cliente actualizado exitosamente");
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar Cliente");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // ==================== MÉTODO MOSTRAR ====================
    public ArrayList<Clientes> mostrar() {
        // CORREGIDO: 'Connection' con C mayúscula
        Connection con = conexion.getConexion();
        String query = "SELECT * FROM clientes";
        ArrayList<Clientes> listaClientes = new ArrayList<>();

        try {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet resultado = pst.executeQuery();

            while (resultado.next())
            {
                Clientes clientes = new Clientes();

                // CORREGIDO: Mapeo de datos desde MySQL hacia el objeto Java
                // (Asegúrate de que los nombres de las columnas coincidan con phpMyAdmin)
                clientes.setId_cliente(resultado.getInt("id_cliente"));
                clientes.setNombres(resultado.getString("nombres"));
                clientes.setApellidos(resultado.getString("apellidos"));
                clientes.setTelefono(resultado.getString("telefono"));
                clientes.setCorreo(resultado.getString("correo"));
                clientes.setDireccion(resultado.getString("direccion"));

                // Guardamos el objeto en la lista
                listaClientes.add(clientes);
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        // CORREGIDO: Retornar la lista
        return listaClientes;
    }

}