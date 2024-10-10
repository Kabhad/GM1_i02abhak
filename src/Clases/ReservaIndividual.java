package Clases;

import java.util.Date;

public class ReservaIndividual extends abstractReserva {
    // Constructor vacío
    public ReservaIndividual() {
        super();
    }

    // Constructor parametrizado
    public ReservaIndividual(int idUsuario, Date fechaHora, int duracionMinutos, int idPista, float precio, float descuento) {
        super(idUsuario, fechaHora, duracionMinutos, idPista, precio, descuento);
    }

    // Método toString específico para ReservaIndividual
    @Override
    public String toString() {
        return "ReservaIndividual: " + super.toString();
    }
}
