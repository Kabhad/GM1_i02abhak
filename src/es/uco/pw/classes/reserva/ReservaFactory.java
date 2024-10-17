package es.uco.pw.classes.reserva;

import java.util.Date;

public abstract class ReservaFactory {
    // Método para crear reservas individuales
    public abstract Reserva crearReserva(String tipoUsuario, int idUsuario, Date fechaHora, int duracionMinutos, int idPista, int numeroAdultos, int numeroNinos, boolean tieneAntiguedad);

    // Método para crear reservas de bono
    public abstract Reserva crearReservaBono(String tipoUsuario, int idUsuario, Date fechaHora, int duracionMinutos, int idPista, int numeroAdultos, int numeroNinos, Bono bono, int numeroSesion);
}
