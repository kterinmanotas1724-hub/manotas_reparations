package model;

public class Vehiculos
{
    private int idVehiculo;
    private String placa;
    private String marca;
    private String modelo;
    private int idCliente;

    // Constructor vacío
    public Vehiculos()
    {
    }

    // Constructor con parámetros
    public Vehiculos(int idVehiculo, String placa, String marca, String modelo, int idCliente)
    {
        this.idVehiculo = idVehiculo;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.idCliente = idCliente;
    }

    // Getters y Setters
    public int getIdVehiculo()
    {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo)
    {
        this.idVehiculo = idVehiculo;
    }

    public String getPlaca()
    {
        return placa;
    }

    public void setPlaca(String placa)
    {
        this.placa = placa;
    }

    public String getMarca()
    {
        return marca;
    }

    public void setMarca(String marca)
    {
        this.marca = marca;
    }

    public String getModelo()
    {
        return modelo;
    }

    public void setModelo(String modelo)
    {
        this.modelo = modelo;
    }

    public int getIdCliente()
    {
        return idCliente;
    }

    public void setIdCliente(int idCliente)
    {
        this.idCliente = idCliente;
    }
}
