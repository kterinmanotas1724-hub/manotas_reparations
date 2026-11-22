import dao.ClienteDAO;
import dao.VehiculoDAO;
import model.Clientes;
import model.Vehiculos;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VehiculoDAOTest {

    @Test
    void testListarVehiculos() {
        VehiculoDAO dao = new VehiculoDAO();
        List<Vehiculos> lista = dao.listar();
        assertNotNull(lista, "La lista de vehículos no debe ser nula");
    }

    @Test
    void testCicloCrudVehiculo() {
        ClienteDAO clienteDAO = new ClienteDAO();
        VehiculoDAO vehiculoDAO = new VehiculoDAO();

        // Crear un cliente temporal para respetar la llave foránea
        Clientes cliTemp = new Clientes(0, "Propietario", "Prueba", "3001112233", "prop_test@mail.com", "Cra 10");
        assertTrue(clienteDAO.insertar(cliTemp));
        int idClienteTemp = clienteDAO.listar().stream()
                .filter(c -> "prop_test@mail.com".equals(c.getCorreo()))
                .findFirst()
                .orElseThrow()
                .getId_cliente();

        // 1. Insertar vehículo
        Vehiculos v = new Vehiculos(0, "TST-999", "Toyota", "Corolla", idClienteTemp);
        assertTrue(vehiculoDAO.insertar(v), "Debe insertar el vehículo de prueba");

        // 2. Buscar vehículo insertado
        Vehiculos encontrado = vehiculoDAO.listar().stream()
                .filter(veh -> "TST-999".equals(veh.getPlaca()))
                .findFirst()
                .orElse(null);
        assertNotNull(encontrado, "El vehículo insertado debe aparecer en el listado");

        // 3. Actualizar vehículo
        encontrado.setModelo("Hilux");
        assertTrue(vehiculoDAO.actualizar(encontrado), "Debe actualizar el vehículo");

        // 4. Limpiar datos de prueba (vehículo y cliente temporal)
        assertTrue(vehiculoDAO.eliminar(encontrado.getIdVehiculo()), "Debe eliminar el vehículo de prueba");
        assertTrue(clienteDAO.eliminar(idClienteTemp), "Debe eliminar el cliente temporal");
    }
}