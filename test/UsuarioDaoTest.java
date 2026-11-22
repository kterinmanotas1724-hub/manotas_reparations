import connection.Conexion;
import dao.UsuarioDao;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioDaoTest {

    @Test
    void testConexionBaseDeDatos() throws SQLException {
        try (Connection con = Conexion.getConexion()) {
            assertNotNull(con, "La conexión a MySQL no debe ser nula");
            assertFalse(con.isClosed(), "La conexión debe estar abierta");
        }
    }

    @Test
    void testLoginCredencialesInvalidas() {
        UsuarioDao usuarioDao = new UsuarioDao();
        boolean resultado = usuarioDao.validarLogin("usuario_falso_999", "clave_incorrecta");
        assertFalse(resultado, "Un usuario inexistente no debe poder iniciar sesión");
    }

    @Test
    void testLoginCamposVacios() {
        UsuarioDao usuarioDao = new UsuarioDao();
        boolean resultado = usuarioDao.validarLogin("", "");
        assertFalse(resultado, "Campos vacíos deben retornar false");
    }
}