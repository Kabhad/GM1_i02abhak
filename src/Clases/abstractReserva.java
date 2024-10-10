package Clases;

import java.util.Date;

public abstract class abstractReserva {
    // Atributos comunes a todas las reservas
    private int idUsuario;
    private Date fechaHora;
    private int duracionMinutos;
    private int idPista;
    private float precio;
    private float descuento;

    // Constructor vacío
    public abstractReserva() {
    }

    // Constructor parametrizado
    public abstractReserva(int idUsuario, Date fechaHora, int duracionMinutos, int idPista, float precio, float descuento) {
        this.idUsuario = idUsuario;
        this.fechaHora = fechaHora;
        this.duracionMinutos = duracionMinutos;
        this.idPista = idPista;
        this.precio = precio;
        this.descuento = descuento;
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

