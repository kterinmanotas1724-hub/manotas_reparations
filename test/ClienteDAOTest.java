import dao.ClienteDAO;
import model.Clientes;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteDAOTest {

    @Test
    void testListarClientes() {
        ClienteDAO dao = new ClienteDAO();
        List<Clientes> lista = dao.listar();
        assertNotNull(lista, "La lista de clientes no debe ser nula");
    }

    @Test
    void testCicloCrudCliente() {
        ClienteDAO dao = new ClienteDAO();

        // 1. Insertar cliente de prueba
        Clientes nuevo = new Clientes(0, "Test", "Unitario", "3000000000", "test@mail.com", "Calle 1");
        assertTrue(dao.insertar(nuevo), "Debe insertar el cliente de prueba");

        // 2. Buscar el ID recién insertado
        Clientes encontrado = dao.listar().stream()
                .filter(c -> "test@mail.com".equals(c.getCorreo()) && "Test".equals(c.getNombres()))
                .findFirst()
                .orElse(null);
        assertNotNull(encontrado, "El cliente insertado debe aparecer al listar");

        // 3. Actualizar cliente
        encontrado.setTelefono("3111111111");
        assertTrue(dao.actualizar(encontrado), "Debe actualizar el cliente correctamente");

        // 4. Eliminar cliente de prueba para no dejar basura en la BD
        assertTrue(dao.eliminar(encontrado.getId_cliente()), "Debe eliminar el cliente de prueba");
    }
}