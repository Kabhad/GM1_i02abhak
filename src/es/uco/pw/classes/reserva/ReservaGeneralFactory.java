package es.uco.pw.classes.reserva;

import java.util.Date;

public class ReservaGeneralFactory {

    public static Reserva crearReserva(String tipoUsuario, int idUsuario, Date fechaHora, int duracionMinutos, int idPista, int numeroAdultos, int numeroNinos, boolean tieneAntiguedad, Bono bono, int numeroSesion) {
        ReservaFactory factory;

        if (bono == null) {  // Si no hay bono, es una reserva individual
            factory = new ReservaIndividualFactory();
            return factory.crearReserva(tipoUsuario, idUsuario, fechaHora, duracionMinutos, idPista, numeroAdultos, numeroNinos, tieneAntiguedad);
        } else {  // Si hay bono, es una reserva de bono
            factory = new ReservaBonoFactory();
            return factory.crearReservaBono(tipoUsuario, idUsuario, fechaHora, duracionMinutos, idPista, numeroAdultos, numeroNinos, bono, numeroSesion);
        }
    }
}
