import server.WebServer;
import java.awt.Desktop;
import java.net.URI;

public class Main {
    public static void main(String[] args) {
        try {
            int puerto = 8000;
            WebServer.iniciar(puerto);

            // Abrir automáticamente el navegador en el Login
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI("http://localhost:" + puerto + "/login.html"));
            }
        } catch (Exception e) {
            System.err.println("Error al iniciar la aplicación web: " + e.getMessage());
            e.printStackTrace();
        }
    }
}