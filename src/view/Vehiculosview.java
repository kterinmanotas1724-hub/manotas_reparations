package view;

import javax.swing.JOptionPane;
import dao.VehiculoDAO;
import model.Vehiculos;
import java.util.List;

public class Vehiculosview
{
    // 🔗 Instancia del DAO para la base de datos
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
                            "3. Salir\n\n" +
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

    // ==================== MÉTODO MAIN (EJECUCIÓN DIRECTA) ====================
    public static void main(String[] args)
    {
        Vehiculosview vehiculosview = new Vehiculosview();
        vehiculosview.menu();
    }
}