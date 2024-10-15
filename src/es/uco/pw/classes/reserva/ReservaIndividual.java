package es.uco.pw.classes.reserva;

import java.util.Date;

public class ReservaIndividual extends Reserva {
    // No hay atributos adicionales específicos para ReservaIndividual

    // Constructor vacío
    public ReservaIndividual() {
        super();
    }

    // Constructor parametrizado
    public ReservaIndividual(int idUsuario, Date fechaHora, int duracionMinutos, int idPista) {
        super(idUsuario, fechaHora, duracionMinutos, idPista);
    }

    // Método toString específico para ReservaIndividual
    @Override
    public String toString() {
        return "ReservaIndividual: " + super.toString();
    }
}
