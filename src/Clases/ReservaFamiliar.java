package Clases;

import java.util.Date;

public class ReservaFamiliar extends abstractReserva {
    // Atributos específicos de ReservaFamiliar
    private int numeroAdultos;
    private int numeroNinos;

    // Constructor vacío, llama al constructor vacio de abstractReserva, clase padre
    public ReservaFamiliar() {
        super();
    }

    // Constructor parametrizado
    public ReservaFamiliar(int idUsuario, Date fechaHora, int duracionMinutos, int idPista, float precio, float descuento, int numeroAdultos, int numeroNinos) {
        super(idUsuario, fechaHora, duracionMinutos, idPista, precio, descuento);
        this.numeroAdultos = numeroAdultos;
        this.numeroNinos = numeroNinos;
    }

    // Métodos get y set para numeroAdultos y numeroNinos
    public int getNumeroAdultos() {
        return numeroAdultos;
    }

    public void setNumeroAdultos(int numeroAdultos) {
        this.numeroAdultos = numeroAdultos;
    }

    public int getNumeroNinos() {
        return numeroNinos;
    }

    public void setNumeroNinos(int numeroNinos) {
        this.numeroNinos = numeroNinos;
    }

    // Método toString específico para ReservaFamiliar
    @Override
    public String toString() {
        return super.toString() + ", numeroAdultos=" + numeroAdultos + ", numeroNinos=" + numeroNinos + "]";
    }
}

