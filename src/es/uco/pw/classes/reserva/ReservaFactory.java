package es.uco.pw.classes.reserva;

import java.util.Date;

public abstract class ReservaFactory {
    public abstract Reserva crearReserva(String tipoUsuario, int idUsuario, Date fechaHora, int duracionMinutos, int idPista, int numeroAdultos, int numeroNinos, boolean tieneAntiguedad, Bono bono, int numeroSesion);
}
