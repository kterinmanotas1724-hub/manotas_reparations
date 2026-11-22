package dao;

import connection.Conexion;
import model.Clientes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public List<Clientes> listar() {
        List<Clientes> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes ORDER BY id_cliente DESC";

        try (Connection con = Conexion.getConexion()) {
            if (con == null) return lista;
            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Clientes c = new Clientes();
                    c.setId_cliente(rs.getInt("id_cliente"));
                    c.setNombres(rs.getString("nombres"));
                    c.setApellidos(rs.getString("apellidos"));
                    c.setTelefono(rs.getString("telefono"));
                    c.setCorreo(rs.getString("correo"));
                    c.setDireccion(rs.getString("direccion"));
                    lista.add(c);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar clientes: " + e.getMessage());
        }
        return lista;
    }

    public boolean insertar(Clientes cliente) {
        String sql = "INSERT INTO clientes (nombres, apellidos, telefono, correo, direccion) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion()) {
            if (con == null) return false;
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, cliente.getNombres());
                ps.setString(2, cliente.getApellidos());
                ps.setString(3, cliente.getTelefono());
                ps.setString(4, cliente.getCorreo());
                ps.setString(5, cliente.getDireccion());
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar cliente: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(Clientes cliente) {
        String sql = "UPDATE clientes SET nombres = ?, apellidos = ?, telefono = ?, correo = ?, direccion = ? WHERE id_cliente = ?";

        try (Connection con = Conexion.getConexion()) {
            if (con == null) return false;
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, cliente.getNombres());
                ps.setString(2, cliente.getApellidos());
                ps.setString(3, cliente.getTelefono());
                ps.setString(4, cliente.getCorreo());
                ps.setString(5, cliente.getDireccion());
                ps.setInt(6, cliente.getId_cliente());
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int idCliente) {
        String sql = "DELETE FROM clientes WHERE id_cliente = ?";

        try (Connection con = Conexion.getConexion()) {
            if (con == null) return false;
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, idCliente);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }
}