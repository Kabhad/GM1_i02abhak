package es.uco.pw.classes.reserva;

import java.util.Date;

public class ReservaAdulto extends Reserva {
    // Atributo específico de ReservaAdulto
    private int numeroAdultos;

    // Constructor vacío, llama al constructor vacio de abstractReserva, clase padre
    public ReservaAdulto() {
        super();
    }

    // Constructor parametrizado
    public ReservaAdulto(int idUsuario, Date fechaHora, int duracionMinutos, int idPista, int numeroAdultos) {
        super(idUsuario, fechaHora, duracionMinutos, idPista);
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
