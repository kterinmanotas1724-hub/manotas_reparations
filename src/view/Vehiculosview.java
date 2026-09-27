package view;

import javax.swing.JOptionPane;
import dao.VehiculoDAO;
import model.Vehiculos;
import java.util.List;

public class Vehiculosview
{
    private VehiculoDAO vehiculoDAO = new VehiculoDAO();

    // ==================== MÉTODO MOSTRAR ====================
    public void mostrarVehiculos()
    {
        List<Vehiculos> lista = vehiculoDAO.listar();

        if (lista.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "No hay vehículos registrados.");
        }
        else
        {
            StringBuilder sb = new StringBuilder("=== 🚗 LISTA DE VEHÍCULOS ===\n\n");
            for (Vehiculos v : lista)
            {
                sb.append("ID: ").append(v.getIdVehiculo())
                        .append(" | Placa: ").append(v.getPlaca())
                        .append(" | Marca: ").append(v.getMarca())
                        .append(" | Modelo: ").append(v.getModelo())
                        .append(" | ID Cliente: ").append(v.getIdCliente())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        }
    }

    // ==================== MÉTODO AGREGAR ====================
    public void agregarVehiculo()
    {
        try
        {
            String placa = JOptionPane.showInputDialog("Ingrese la placa del vehículo:");
            if (placa == null || placa.trim().isEmpty()) return;

            String marca = JOptionPane.showInputDialog("Ingrese la marca del vehículo:");
            if (marca == null || marca.trim().isEmpty()) return;

            String modelo = JOptionPane.showInputDialog("Ingrese el modelo del vehículo:");
            if (modelo == null || modelo.trim().isEmpty()) return;

            String idClienteStr = JOptionPane.showInputDialog("Ingrese el ID del cliente propietario:");
            if (idClienteStr == null || idClienteStr.trim().isEmpty()) return;

            int idCliente = Integer.parseInt(idClienteStr);

            Vehiculos vehiculo = new Vehiculos(0, placa, marca, modelo, idCliente);
            boolean exito = vehiculoDAO.insertar(vehiculo);

            if (exito)
            {
                JOptionPane.showMessageDialog(null, "✅ ¡Vehículo registrado exitosamente!");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "❌ Error al registrar el vehículo.");
            }
        }
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null, "⚠️ El ID del cliente debe ser un número entero válido.");
        }
    }

    // ==================== MÉTODO ACTUALIZAR ====================
    public void actualizarVehiculo()
    {
        try
        {
            String idStr = JOptionPane.showInputDialog("Ingrese el ID del vehículo a actualizar:");
            if (idStr == null || idStr.trim().isEmpty()) return;
            int idVehiculo = Integer.parseInt(idStr);

            String placa = JOptionPane.showInputDialog("Ingrese la nueva placa:");
            if (placa == null || placa.trim().isEmpty()) return;

            String marca = JOptionPane.showInputDialog("Ingrese la nueva marca:");
            if (marca == null || marca.trim().isEmpty()) return;

            String modelo = JOptionPane.showInputDialog("Ingrese el nuevo modelo:");
            if (modelo == null || modelo.trim().isEmpty()) return;

            String idClienteStr = JOptionPane.showInputDialog("Ingrese el nuevo ID del cliente propietario:");
            if (idClienteStr == null || idClienteStr.trim().isEmpty()) return;
            int idCliente = Integer.parseInt(idClienteStr);

            Vehiculos vehiculo = new Vehiculos(idVehiculo, placa, marca, modelo, idCliente);
            boolean exito = vehiculoDAO.actualizar(vehiculo);

            if (exito)
            {
                JOptionPane.showMessageDialog(null, "✅ ¡Vehículo actualizado exitosamente!");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "❌ Error al actualizar el vehículo.");
            }
        }
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null, "⚠️ Los campos de ID deben ser números enteros válidos.");
        }
    }

    // ==================== MÉTODO ELIMINAR ====================
    public void eliminarVehiculo()
    {
        try
        {
            String idStr = JOptionPane.showInputDialog("Ingrese el ID del vehículo a eliminar:");
            if (idStr == null || idStr.trim().isEmpty()) return;
            int idVehiculo = Integer.parseInt(idStr);

            int confirmacion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Está seguro de eliminar el vehículo con ID: " + idVehiculo + "?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmacion == JOptionPane.YES_OPTION)
            {
                boolean exito = vehiculoDAO.eliminar(idVehiculo);
                if (exito)
                {
                    JOptionPane.showMessageDialog(null, "🗑️ ¡Vehículo eliminado exitosamente!");
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "❌ Error al eliminar el vehículo.");
                }
            }
        }
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null, "⚠️ El ID debe ser un número entero válido.");
        }
    }

    // ==================== MENÚ PRINCIPAL ====================
    public void menu()
    {
        boolean salir = false;

        while (!salir)
        {
            String opcionStr = JOptionPane.showInputDialog(
                    null,
                    "=== 🚗 MENÚ VEHÍCULOS ===\n" +
                            "1. Mostrar vehículos\n" +
                            "2. Agregar vehículo\n" +
                            "3. Actualizar vehículo\n" +
                            "4. Eliminar vehículo\n" +
                            "5. Volver al menú principal\n\n" +
                            "Seleccione una opción:",
                    "Menú Vehículos",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (opcionStr == null)
            {
                break;
            }

            try
            {
                int opcion = Integer.parseInt(opcionStr);

                switch (opcion)
                {
                    case 1:
                        mostrarVehiculos();
                        break;
                    case 2:
                        agregarVehiculo();
                        break;
                    case 3:
                        actualizarVehiculo();
                        break;
                    case 4:
                        eliminarVehiculo();
                        break;
                    case 5:
                        salir = true;
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opción no válida.");
                }
            }
            catch (NumberFormatException e)
            {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese un número entero.");
            }
        }
    }

    public static void main(String[] args)
    {
        Vehiculosview vehiculosview = new Vehiculosview();
        vehiculosview.menu();
    }
}