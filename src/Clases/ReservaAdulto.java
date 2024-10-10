package Clases;

import java.util.Date;

public class ReservaAdulto extends abstractReserva {
    // Atributo específico de ReservaAdulto
    private int numeroAdultos;

    // Constructor vacío, llama al constructor vacio de abstractReserva, clase padre
    public ReservaAdulto() {
        super();
    }

    // Constructor parametrizado
    public ReservaAdulto(int idUsuario, Date fechaHora, int duracionMinutos, int idPista, float precio, float descuento, int numeroAdultos) {
        super(idUsuario, fechaHora, duracionMinutos, idPista, precio, descuento);
        this.numeroAdultos = numeroAdultos;
    }

    // Métodos get y set para numeroAdultos
    public int getNumeroAdultos() {
        return numeroAdultos;
    }

    public void setNumeroAdultos(int numeroAdultos) {
        this.numeroAdultos = numeroAdultos;
    }

    // Método toString específico para ReservaAdulto
    @Override
    public String toString() {
        return super.toString() + ", numeroAdultos=" + numeroAdultos + "]";
    }
}
