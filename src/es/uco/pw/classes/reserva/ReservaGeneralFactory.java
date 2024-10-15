package es.uco.pw.classes.reserva;

import java.util.Date;

public class ReservaGeneralFactory {

    public static Reserva crearReserva(String tipoReserva, String tipoUsuario, int idUsuario, Date fechaHora, int duracionMinutos, int idPista, int numeroAdultos, int numeroNinos, boolean tieneAntiguedad, Bono bono, int numeroSesion) {
        ReservaBonoFactory factory;

        if (tipoReserva.equalsIgnoreCase("individual")) {
            factory = new ReservaIndividualFactory();
        } else if (tipoReserva.equalsIgnoreCase("bono")) {
            factory = new ReservaBonoFactory();
        } else {
            throw new IllegalArgumentException("Tipo de reserva no válido: " + tipoReserva);
        }

        return factory.crearReserva(tipoUsuario, idUsuario, fechaHora, duracionMinutos, idPista, numeroAdultos, numeroNinos, tieneAntiguedad, bono, numeroSesion);
    }
}
