import dao.VehiculoDAO;
import model.Vehiculos;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        VehiculoDAO dao = new VehiculoDAO();

        System.out.println("=== 🚗 REGISTRO DE VEHÍCULO ===");

        System.out.print("Ingrese la placa: ");
        String placa = scanner.nextLine();

        System.out.print("Ingrese la marca: ");
        String marca = scanner.nextLine();

        System.out.print("Ingrese el modelo: ");
        String modelo = scanner.nextLine();

        System.out.print("Ingrese el ID del cliente: ");
        int idCliente = scanner.nextInt();

        // Creamos el objeto con los datos ingresados por el usuario
        Vehiculos nuevoVehiculo = new Vehiculos(0, placa, marca, modelo, idCliente);

        // Guardamos en la base de datos
        boolean insertado = dao.insertar(nuevoVehiculo);

        if (insertado)
        {
            System.out.println("\n✅ ¡Vehículo registrado exitosamente en MySQL!");
        }
        else
        {
            System.out.println("\n❌ Error al registrar el vehículo.");
        }

        scanner.close();
    }
}