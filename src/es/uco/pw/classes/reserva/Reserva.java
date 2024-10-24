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
        this.precio = calcularPrecio(duracionMinutos, 0); // Calcula el precio automáticamente sin descuento inicialmente
        this.descuento = 0; // El descuento se aplicará después si es necesario
    }
    
    // Cálculo de precios basado en la duración, aplicando el descuento si existe
    public static float calcularPrecio(int duracionMinutos, float descuento) {
        float precioBase;
        switch (duracionMinutos) {
            case 60: precioBase = 20.0f; break;
            case 90: precioBase = 30.0f; break;
            case 120: precioBase = 40.0f; break;
            default: throw new IllegalArgumentException("Duración no válida");
        }
        // Aplica el descuento si existe
        return precioBase * (1 - descuento);
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

    // Setter de descuento que recalcula el precio
    public void setDescuento(float descuento) {
        this.descuento = descuento;
        this.precio = calcularPrecio(this.duracionMinutos, descuento); // Recalcular el precio al aplicar descuento
    }
    
    // Método toString para imprimir la información de la reserva
    @Override
    public String toString() {
        return "Reserva [idUsuario=" + idUsuario + ", fechaHora=" + fechaHora + ", duracionMinutos=" + duracionMinutos
                + ", idPista=" + idPista + ", precio=" + precio + ", descuento=" + descuento + "]";
    }
}

