package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import dao.ClienteDAO;
import dao.UsuarioDao;
import dao.VehiculoDAO;
import model.Clientes;
import model.Vehiculos;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebServer {

    private static final UsuarioDao usuarioDao = new UsuarioDao();
    private static final ClienteDAO clienteDAO = new ClienteDAO();
    private static final VehiculoDAO vehiculoDAO = new VehiculoDAO();

    public static void iniciar(int puerto) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(puerto), 0);

        // Rutas de páginas HTML
        server.createContext("/", exchange -> servirArchivo(exchange, "login.html"));
        server.createContext("/login.html", exchange -> servirArchivo(exchange, "login.html"));
        server.createContext("/dashboard.html", exchange -> servirArchivo(exchange, "dashboard.html"));
        server.createContext("/clientes.html", exchange -> servirArchivo(exchange, "clientes.html"));
        server.createContext("/vehiculos.html", exchange -> servirArchivo(exchange, "vehiculos.html"));

        // Rutas API (Conexión con Java y MySQL)
        server.createContext("/api/login", WebServer::manejarLogin);
        server.createContext("/api/resumen", WebServer::manejarResumen);
        server.createContext("/api/clientes", WebServer::manejarClientes);
        server.createContext("/api/vehiculos", WebServer::manejarVehiculos);

        server.setExecutor(null);
        server.start();
        System.out.println("====================================================");
        System.out.println(" Servidor Web iniciado en: http://localhost:" + puerto);
        System.out.println("====================================================");
    }

    private static void servirArchivo(HttpExchange exchange, String nombreArchivo) throws IOException {
        Path path = Paths.get(nombreArchivo);
        if (!Files.exists(path)) {
            enviarRespuesta(exchange, 404, "text/plain; charset=UTF-8", "Archivo no encontrado: " + nombreArchivo);
            return;
        }
        byte[] bytes = Files.readAllBytes(path);
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(200, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private static void manejarLogin(HttpExchange exchange) throws IOException {
        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            enviarRespuesta(exchange, 405, "application/json; charset=UTF-8", "{\"ok\":false,\"mensaje\":\"Método no permitido\"}");
            return;
        }
        Map<String, String> params = leerParametrosBody(exchange);
        String usuario = params.getOrDefault("usuario", "").trim();
        String password = params.getOrDefault("password", "").trim();

        boolean valido = usuarioDao.validarLogin(usuario, password);
        if (valido) {
            enviarRespuesta(exchange, 200, "application/json; charset=UTF-8",
                    "{\"ok\":true,\"usuario\":\"" + escaparJson(usuario) + "\"}");
        } else {
            enviarRespuesta(exchange, 401, "application/json; charset=UTF-8",
                    "{\"ok\":false,\"mensaje\":\"Usuario o contraseña incorrectos\"}");
        }
    }

    private static void manejarResumen(HttpExchange exchange) throws IOException {
        int totalClientes = clienteDAO.listar().size();
        int totalVehiculos = vehiculoDAO.listar().size();
        String json = "{\"clientes\":" + totalClientes + ",\"vehiculos\":" + totalVehiculos + "}";
        enviarRespuesta(exchange, 200, "application/json; charset=UTF-8", json);
    }

    private static void manejarClientes(HttpExchange exchange) throws IOException {
        String metodo = exchange.getRequestMethod().toUpperCase();

        switch (metodo) {
            case "GET": {
                List<Clientes> lista = clienteDAO.listar();
                StringBuilder sb = new StringBuilder("[");
                for (int i = 0; i < lista.size(); i++) {
                    Clientes c = lista.get(i);
                    if (i > 0) sb.append(",");
                    sb.append("{")
                            .append("\"id_cliente\":").append(c.getId_cliente()).append(",")
                            .append("\"nombres\":\"").append(escaparJson(c.getNombres())).append("\",")
                            .append("\"apellidos\":\"").append(escaparJson(c.getApellidos())).append("\",")
                            .append("\"telefono\":\"").append(escaparJson(c.getTelefono())).append("\",")
                            .append("\"correo\":\"").append(escaparJson(c.getCorreo())).append("\",")
                            .append("\"direccion\":\"").append(escaparJson(c.getDireccion())).append("\"")
                            .append("}");
                }
                sb.append("]");
                enviarRespuesta(exchange, 200, "application/json; charset=UTF-8", sb.toString());
                break;
            }
            case "POST": {
                Map<String, String> params = leerParametrosBody(exchange);
                Clientes c = new Clientes();
                c.setNombres(params.getOrDefault("nombres", ""));
                c.setApellidos(params.getOrDefault("apellidos", ""));
                c.setTelefono(params.getOrDefault("telefono", ""));
                c.setCorreo(params.getOrDefault("correo", ""));
                c.setDireccion(params.getOrDefault("direccion", ""));

                boolean ok = clienteDAO.insertar(c);
                enviarRespuesta(exchange, ok ? 200 : 400, "application/json; charset=UTF-8",
                        "{\"ok\":" + ok + "}");
                break;
            }
            case "PUT": {
                Map<String, String> params = leerParametrosBody(exchange);
                Clientes c = new Clientes();
                c.setId_cliente(Integer.parseInt(params.getOrDefault("id_cliente", "0")));
                c.setNombres(params.getOrDefault("nombres", ""));
                c.setApellidos(params.getOrDefault("apellidos", ""));
                c.setTelefono(params.getOrDefault("telefono", ""));
                c.setCorreo(params.getOrDefault("correo", ""));
                c.setDireccion(params.getOrDefault("direccion", ""));

                boolean ok = clienteDAO.actualizar(c);
                enviarRespuesta(exchange, ok ? 200 : 400, "application/json; charset=UTF-8",
                        "{\"ok\":" + ok + "}");
                break;
            }
            case "DELETE": {
                Map<String, String> query = parsearQuery(exchange.getRequestURI().getRawQuery());
                int id = Integer.parseInt(query.getOrDefault("id", "0"));
                boolean ok = clienteDAO.eliminar(id);
                enviarRespuesta(exchange, ok ? 200 : 400, "application/json; charset=UTF-8",
                        "{\"ok\":" + ok + "}");
                break;
            }
            default:
                enviarRespuesta(exchange, 405, "application/json; charset=UTF-8", "{\"ok\":false}");
        }
    }

    private static void manejarVehiculos(HttpExchange exchange) throws IOException {
        String metodo = exchange.getRequestMethod().toUpperCase();

        switch (metodo) {
            case "GET": {
                List<Vehiculos> lista = vehiculoDAO.listar();
                StringBuilder sb = new StringBuilder("[");
                for (int i = 0; i < lista.size(); i++) {
                    Vehiculos v = lista.get(i);
                    if (i > 0) sb.append(",");
                    sb.append("{")
                            .append("\"idVehiculo\":").append(v.getIdVehiculo()).append(",")
                            .append("\"placa\":\"").append(escaparJson(v.getPlaca())).append("\",")
                            .append("\"marca\":\"").append(escaparJson(v.getMarca())).append("\",")
                            .append("\"modelo\":\"").append(escaparJson(v.getModelo())).append("\",")
                            .append("\"idCliente\":").append(v.getIdCliente())
                            .append("}");
                }
                sb.append("]");
                enviarRespuesta(exchange, 200, "application/json; charset=UTF-8", sb.toString());
                break;
            }
            case "POST": {
                Map<String, String> params = leerParametrosBody(exchange);
                Vehiculos v = new Vehiculos(
                        0,
                        params.getOrDefault("placa", ""),
                        params.getOrDefault("marca", ""),
                        params.getOrDefault("modelo", ""),
                        Integer.parseInt(params.getOrDefault("idCliente", "0"))
                );
                boolean ok = vehiculoDAO.insertar(v);
                enviarRespuesta(exchange, ok ? 200 : 400, "application/json; charset=UTF-8",
                        "{\"ok\":" + ok + "}");
                break;
            }
            case "PUT": {
                Map<String, String> params = leerParametrosBody(exchange);
                Vehiculos v = new Vehiculos(
                        Integer.parseInt(params.getOrDefault("idVehiculo", "0")),
                        params.getOrDefault("placa", ""),
                        params.getOrDefault("marca", ""),
                        params.getOrDefault("modelo", ""),
                        Integer.parseInt(params.getOrDefault("idCliente", "0"))
                );
                boolean ok = vehiculoDAO.actualizar(v);
                enviarRespuesta(exchange, ok ? 200 : 400, "application/json; charset=UTF-8",
                        "{\"ok\":" + ok + "}");
                break;
            }
            case "DELETE": {
                Map<String, String> query = parsearQuery(exchange.getRequestURI().getRawQuery());
                int id = Integer.parseInt(query.getOrDefault("id", "0"));
                boolean ok = vehiculoDAO.eliminar(id);
                enviarRespuesta(exchange, ok ? 200 : 400, "application/json; charset=UTF-8",
                        "{\"ok\":" + ok + "}");
                break;
            }
            default:
                enviarRespuesta(exchange, 405, "application/json; charset=UTF-8", "{\"ok\":false}");
        }
    }

    private static Map<String, String> leerParametrosBody(HttpExchange exchange) throws IOException {
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        return parsearQuery(body);
    }

    private static Map<String, String> parsearQuery(String query) {
        Map<String, String> map = new HashMap<>();
        if (query == null || query.isEmpty()) return map;
        for (String pair : query.split("&")) {
            String[] kv = pair.split("=", 2);
            String key = URLDecoder.decode(kv[0], StandardCharsets.UTF_8);
            String value = kv.length > 1 ? URLDecoder.decode(kv[1], StandardCharsets.UTF_8) : "";
            map.put(key, value);
        }
        return map;
    }

    private static void enviarRespuesta(HttpExchange exchange, int codigo, String contentType, String contenido) throws IOException {
        byte[] bytes = contenido.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.sendResponseHeaders(codigo, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private static String escaparJson(String texto) {
        if (texto == null) return "";
        return texto.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }
}