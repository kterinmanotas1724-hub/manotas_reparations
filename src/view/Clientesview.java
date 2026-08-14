package view;

import controler.ClientesController;
import model.Clientes;

import javax.swing.*;
import java.util.ArrayList;

public class Clientesview {

    // Controlador para gestionar los datos
    private ClientesController clientesController = new ClientesController();


    // ==================== MÉTODO MOSTRAR ====================
    public void mostrar() {
        ArrayList<Clientes> listaClientes = clientesController.mostrar();

        System.out.println("\n=== LISTADO DE CLIENTES ===");
        for (Clientes cliente : listaClientes) {
            System.out.println("ID: " + cliente.getId_cliente());
            System.out.println("NOMBRES: " + cliente.getNombres());
            System.out.println("APELLIDOS: " + cliente.getApellidos());
            System.out.println("TELÉFONO: " + cliente.getTelefono());
            System.out.println("CORREO: " + cliente.getCorreo());
            System.out.println("DIRECCIÓN: " + cliente.getDireccion());
            System.out.println("__________________________________");
        }
    }


    // ==================== MÉTODO ELIMINAR ====================
    public void eliminar() {
        String inputId = JOptionPane.showInputDialog("Ingrese el ID del cliente a eliminar:");

        if (inputId != null && !inputId.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(inputId);
                clientesController.eliminar(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "El ID debe ser un número entero válido.");
            }
        }
    }


    // ==================== MÉTODO AGREGAR ====================
    public void agregar() {
        String nombres = JOptionPane.showInputDialog("Ingresar los nombres del cliente:");
        String apellidos = JOptionPane.showInputDialog("Ingresar los apellidos del cliente:");
        String telefono = JOptionPane.showInputDialog("Ingresar el teléfono del cliente:");
        String correo = JOptionPane.showInputDialog("Ingresar el correo del cliente:");
        String direccion = JOptionPane.showInputDialog("Ingresar la dirección del cliente:");

        if (nombres != null && !nombres.trim().isEmpty()) {
            Clientes cliente = new Clientes();
            cliente.setNombres(nombres);
            cliente.setApellidos(apellidos);
            cliente.setTelefono(telefono);
            cliente.setCorreo(correo);
            cliente.setDireccion(direccion);

            clientesController.agregar(cliente);
        } else {
            JOptionPane.showMessageDialog(null, "Operación cancelada o datos incompletos.");
        }
    }


    // ==================== MÉTODO ACTUALIZAR ====================
    public void actualizar() {
        String inputId = JOptionPane.showInputDialog("Ingrese el ID del cliente a actualizar:");

        if (inputId != null && !inputId.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(inputId);

                String nombres = JOptionPane.showInputDialog("Ingresar los nuevos nombres:");
                String apellidos = JOptionPane.showInputDialog("Ingresar los nuevos apellidos:");
                String telefono = JOptionPane.showInputDialog("Ingresar el nuevo teléfono:");
                String correo = JOptionPane.showInputDialog("Ingresar el nuevo correo:");
                String direccion = JOptionPane.showInputDialog("Ingresar la nueva dirección:");

                Clientes cliente = new Clientes();
                cliente.setId_cliente(id);
                cliente.setNombres(nombres);
                cliente.setApellidos(apellidos);
                cliente.setTelefono(telefono);
                cliente.setCorreo(correo);
                cliente.setDireccion(direccion);

                clientesController.editar(cliente);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "El ID ingresado no es válido.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Operación cancelada.");
        }
    }


    // ==================== MENÚ PRINCIPAL ====================
    public void menu() {
        boolean continuar = true;

        while (continuar) {
            String inputOp = JOptionPane.showInputDialog(
                    "=== MENÚ CLIENTES ===\n" +
                            "1. Mostrar clientes\n" +
                            "2. Agregar cliente\n" +
                            "3. Actualizar cliente\n" +
                            "4. Eliminar cliente\n" +
                            "5. Salir\n\n" +
                            "Seleccione una opción:"
            );

            // Si el usuario presiona Cancelar o cierra la ventana
            if (inputOp == null) {
                break;
            }

            try {
                int op = Integer.parseInt(inputOp);

                switch (op) {
                    case 1:
                        mostrar();
                        break;
                    case 2:
                        agregar();
                        break;
                    case 3:
                        actualizar();
                        break;
                    case 4:
                        eliminar();
                        break;
                    case 5:
                        JOptionPane.showMessageDialog(null, "¡Aplicación finalizada!");
                        continuar = false;
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opción no válida. Intente de nuevo.");
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese un número entero.");
            }
        }
    }


    // ==================== MÉTODO MAIN ====================
    public static void main(String[] args) {
        Clientesview clientesview = new Clientesview();
        clientesview.menu();
    }
}