package es.uco.pw.classes.reserva;

import java.util.Date;

public abstract class Reserva {
    // Atributos comunes a todas las reservas
    private int idUsuario;
    private Date fechaHora;
    private int duracionMinutos;
    private int idPista;
    private float precio;
    private float descuento;

    // Constructor vacío
    public Reserva() {
    }

    // Constructor parametrizado
    public Reserva(int idUsuario, Date fechaHora, int duracionMinutos, int idPista) {
        this.idUsuario = idUsuario;
        this.fechaHora = fechaHora;
        this.duracionMinutos = duracionMinutos;
        this.idPista = idPista;
        this.precio = calcularPrecio(duracionMinutos); // Calcula el precio automáticamente
        this.descuento = 0; // El descuento se aplicará después si es necesario
    }
    
    // Cálculo de precios basado en la duración
    private float calcularPrecio(int duracionMinutos) {
        switch (duracionMinutos) {
            case 60: return 20.0f;
            case 90: return 30.0f;
            case 120: return 40.0f;
            default: throw new IllegalArgumentException("Duración no válida");
        }
    }

    // Métodos get y set para cada atributo
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public int getIdPista() {
        return idPista;
    }

    public void setIdPista(int idPista) {
        this.idPista = idPista;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    // Método toString para imprimir la información de la reserva
    @Override
    public String toString() {
        return "Reserva [idUsuario=" + idUsuario + ", fechaHora=" + fechaHora + ", duracionMinutos=" + duracionMinutos
                + ", idPista=" + idPista + ", precio=" + precio + ", descuento=" + descuento + "]";
    }
}

