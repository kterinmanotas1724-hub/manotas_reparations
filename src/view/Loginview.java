package view;

import dao.UsuarioDao;
import javax.swing.*;

public class Loginview {

    private UsuarioDao usuarioDao = new UsuarioDao();

    // ==================== MÉTODO DE INICIO DE SESIÓN ====================
    public void iniciarSesion() {
        JTextField campoUsuario = new JTextField();
        JPasswordField campoContrasena = new JPasswordField();

        Object[] mensaje = {
                "Usuario:", campoUsuario,
                "Contraseña:", campoContrasena
        };

        int opcion = JOptionPane.showConfirmDialog(
                null,
                mensaje,
                "=== LOGIN - MANOTAS REPARATIONS ===",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (opcion == JOptionPane.OK_OPTION) {
            String usuario = campoUsuario.getText().trim();
            String contrasena = new String(campoContrasena.getPassword()).trim();

            if (usuario.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                iniciarSesion(); // Reintentar
                return;
            }

            // Validar contra la base de datos usando tu UsuarioDao
            boolean esValido = usuarioDao.validarLogin(usuario, contrasena);

            if (esValido) {
                JOptionPane.showMessageDialog(null, "¡Bienvenido al sistema, " + usuario + "!", "Login Exitoso", JOptionPane.INFORMATION_MESSAGE);
                mostrarMenuPrincipal();
            } else {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.", "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
                iniciarSesion(); // Reintentar o permitir salir
            }
        } else {
            JOptionPane.showMessageDialog(null, "Aplicación finalizada.");
        }
    }

    // ==================== MENÚ PRINCIPAL GLOBAL ====================
    public void mostrarMenuPrincipal() {
        boolean continuar = true;

        while (continuar) {
            String inputOp = JOptionPane.showInputDialog(
                    null,
                    "=== 🛠️ MENÚ PRINCIPAL - MANOTAS REPARATIONS ===\n" +
                            "1. Gestionar Clientes\n" +
                            "2. Gestionar Vehículos\n" +
                            "3. Cerrar Sesión / Salir\n\n" +
                            "Seleccione una opción:",
                    "Menú Principal",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (inputOp == null) {
                break;
            }

            try {
                int op = Integer.parseInt(inputOp);

                switch (op) {
                    case 1:
                        Clientesview clientesView = new Clientesview();
                        clientesView.menu();
                        break;
                    case 2:
                        Vehiculosview vehiculosView = new Vehiculosview();
                        vehiculosView.menu();
                        break;
                    case 3:
                        JOptionPane.showMessageDialog(null, "¡Sesión cerrada con éxito!");
                        continuar = false;
                        // Opcional: volver a mostrar el login al cerrar sesión
                        iniciarSesion();
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
        Loginview loginView = new Loginview();
        loginView.iniciarSesion();
    }
}