package dao;

import connection.Conexion;
import model.Vehiculos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO
{
    // Método para insertar/guardar un nuevo vehículo
    public boolean insertar(Vehiculos vehiculo)
    {
        String sql = "INSERT INTO vehiculos (placa, marca, modelo, id_cliente) VALUES (?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setString(1, vehiculo.getPlaca());
            ps.setString(2, vehiculo.getMarca());
            ps.setString(3, vehiculo.getModelo());
            ps.setInt(4, vehiculo.getIdCliente());

            return ps.executeUpdate() > 0;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    // Método para consultar/listar todos los vehículos
    public List<Vehiculos> listar()
    {
        List<Vehiculos> lista = new ArrayList<>();
        String sql = "SELECT * FROM vehiculos";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
            {
                Vehiculos v = new Vehiculos();
                v.setIdVehiculo(rs.getInt("id_vehiculo"));
                v.setPlaca(rs.getString("placa"));
                v.setMarca(rs.getString("marca"));
                v.setModelo(rs.getString("modelo"));
                v.setIdCliente(rs.getInt("id_cliente"));

                lista.add(v);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return lista;
    } // <-- Aquí cierra correctamente el método listar()

    // ==================== MÉTODO ACTUALIZAR ====================
    public boolean actualizar(Vehiculos vehiculo)
    {
        String sql = "UPDATE vehiculos SET placa = ?, marca = ?, modelo = ?, id_cliente = ? WHERE id_vehiculo = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setString(1, vehiculo.getPlaca());
            ps.setString(2, vehiculo.getMarca());
            ps.setString(3, vehiculo.getModelo());
            ps.setInt(4, vehiculo.getIdCliente());
            ps.setInt(5, vehiculo.getIdVehiculo());

            return ps.executeUpdate() > 0;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    // ==================== MÉTODO ELIMINAR ====================
    public boolean eliminar(int idVehiculo)
    {
        String sql = "DELETE FROM vehiculos WHERE id_vehiculo = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt(1, idVehiculo);

            return ps.executeUpdate() > 0;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
}