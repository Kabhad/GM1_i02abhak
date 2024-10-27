package es.uco.pw.classes.reserva;

import java.util.Date;
import es.uco.pw.gestores.jugadores.GestorJugadores;
import es.uco.pw.classes.jugador.Jugador;

public class ReservaIndividualFactory extends ReservaFactory {

    @Override
    public Reserva crearReserva(String tipoUsuario, int idUsuario, Date fechaHora, int duracionMinutos, int idPista, int numeroAdultos, int numeroNinos, boolean tieneAntiguedad) {
        Reserva reservaEspecifica;
        GestorJugadores gestorJugadores = GestorJugadores.getInstance();
        Jugador jugador = gestorJugadores.buscarJugadorPorId(idUsuario);

        // Crea la reserva específica según el tipo de usuario (infantil, familiar, adulto)
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

        // Verificar si el jugador fue encontrado antes de continuar
        if (jugador != null) {
            // Calculamos si el jugador tiene más de 2 años de antigüedad
            if (jugador.calcularAntiguedad() > 2) {
                reservaEspecifica.setDescuento(0.10f);  // Aplicar 10% de descuento
            }
        }

        // Retornar una instancia de ReservaIndividual con la reserva específica y posible descuento aplicado
        return new ReservaIndividual(idUsuario, fechaHora, duracionMinutos, idPista, reservaEspecifica);
    }
    
    @Override
    public Reserva crearReservaBono(String tipoUsuario, int idUsuario, Date fechaHora, int duracionMinutos, int idPista, int numeroAdultos, int numeroNinos, Bono bono, int numeroSesion) {
        throw new UnsupportedOperationException("ReservaIndividualFactory no puede crear reservas de bono.");
    }
}
