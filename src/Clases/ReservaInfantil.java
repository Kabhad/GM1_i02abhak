package Clases;

import java.util.Date;

public class ReservaInfantil extends abstractReserva {
    // Atributo específico de ReservaInfantil
    private int numeroNinos;

    // Constructor vacío, llama al constructor vacio de abstractReserva, clase padre
    public ReservaInfantil() {
        super();
    }

    // Constructor parametrizado
    public ReservaInfantil(int idUsuario, Date fechaHora, int duracionMinutos, int idPista, float precio, float descuento, int numeroNinos) {
        super(idUsuario, fechaHora, duracionMinutos, idPista, precio, descuento);
        this.numeroNinos = numeroNinos;
    }

    // Métodos get y set para numeroNinos
    public int getNumeroNinos() {
        return numeroNinos;
    }

    public void setNumeroNinos(int numeroNinos) {
        this.numeroNinos = numeroNinos;
    }

    // Método toString específico para ReservaInfantil
    @Override
    public String toString() {
        return super.toString() + ", numeroNinos=" + numeroNinos + "]";
    }
}

