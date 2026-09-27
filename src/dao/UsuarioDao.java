package dao;

import connection.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase DAO para gestionar las consultas de autenticación de usuarios
 * Proyecto: manotas_reparations
 */
public class UsuarioDao {

    /**
     * Método para validar el inicio de sesión del usuario
     * @param usuario El nombre de usuario ingresado en la vista
     * @param contrasena La contraseña ingresada
     * @return boolean true si los datos son correctos, false en caso contrario
     */
    public boolean validarLogin(String usuario, String contrasena) {
        // Consulta SQL para verificar si existe el usuario y la contraseña
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND contrasena = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Asignar los parámetros a la consulta segura
            ps.setString(1, usuario);
            ps.setString(2, contrasena);

            try (ResultSet rs = ps.executeQuery()) {
                // Si encuentra un registro, las credenciales son correctas
                if (rs.next()) {
                    System.out.println("¡Login exitoso para el usuario: " + rs.getString("nombre_completo") + "!");
                    return true;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al validar el login: " + e.getMessage());
        }

        return false; // Retorna falso si no coincide o hay error
    }
}