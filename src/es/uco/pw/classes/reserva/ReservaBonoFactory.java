package es.uco.pw.classes.reserva;

import java.util.Date;

public class ReservaBonoFactory extends ReservaFactory {

    @Override
    public Reserva crearReserva(String tipoUsuario, int idUsuario, Date fechaHora, int duracionMinutos, int idPista, int numeroAdultos, int numeroNinos, boolean tieneAntiguedad, Bono bono, int numeroSesion) {
        ReservaBono reservaBono = new ReservaBono(idUsuario, fechaHora, duracionMinutos, idPista, bono, numeroSesion);

        switch (tipoUsuario.toLowerCase()) {
            case "infantil":
                reservaBono.crearReservaInfantil(idUsuario, fechaHora, duracionMinutos, idPista, numeroNinos);
                break;
            case "familiar":
                reservaBono.crearReservaFamiliar(idUsuario, fechaHora, duracionMinutos, idPista, numeroAdultos, numeroNinos);
                break;
            case "adulto":
                reservaBono.crearReservaAdulto(idUsuario, fechaHora, duracionMinutos, idPista, numeroAdultos);
                break;
            default:
                throw new IllegalArgumentException("Tipo de usuario no válido: " + tipoUsuario);
        }

        return reservaBono;
    }
}
