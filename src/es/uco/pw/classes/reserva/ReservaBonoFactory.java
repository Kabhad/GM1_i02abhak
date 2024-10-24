package es.uco.pw.classes.reserva;

import java.util.Date;

public class ReservaBonoFactory extends ReservaFactory {

    @Override
    public Reserva crearReserva(String tipoUsuario, int idUsuario, Date fechaHora, int duracionMinutos, int idPista, int numeroAdultos, int numeroNinos, boolean tieneAntiguedad) {
        throw new UnsupportedOperationException("ReservaBonoFactory solo soporta reservas de bono.");
    }

    @Override
    public Reserva crearReservaBono(String tipoUsuario, int idUsuario, Date fechaHora, int duracionMinutos, int idPista, int numeroAdultos, int numeroNinos, Bono bono, int numeroSesion) {
        Reserva reservaEspecifica;

        switch (tipoUsuario.toLowerCase()) {
            case "infantil":
                reservaEspecifica = new ReservaInfantil(idUsuario, fechaHora, duracionMinutos, idPista, numeroNinos);
                break;
            case "familiar":
                reservaEspecifica = new ReservaFamiliar(idUsuario, fechaHora, duracionMinutos, idPista, numeroAdultos, numeroNinos);
                break;
            case "adulto":
                reservaEspecifica = new ReservaAdulto(idUsuario, fechaHora, duracionMinutos, idPista, numeroAdultos);
                break;
            default:
                throw new IllegalArgumentException("Tipo de usuario no válido: " + tipoUsuario);
        }
        
        reservaEspecifica.setDescuento(0.05f);  // Sincroniza el descuento en la reserva específica

        // Crear la reserva de bono con la reserva específica
        return new ReservaBono(idUsuario, fechaHora, duracionMinutos, idPista, bono, numeroSesion, reservaEspecifica);
    }
}
