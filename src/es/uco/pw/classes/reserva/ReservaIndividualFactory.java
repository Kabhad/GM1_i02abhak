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

	    // Crear la reserva según el tipo de usuario
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
	            System.out.println("Descuento del 10% aplicado para el jugador con ID: " + idUsuario);
	        } else {
	            System.out.println("El jugador con ID " + idUsuario + " no tiene antigüedad suficiente para el descuento.");
	        }
	    } else {
	        System.out.println("Jugador no encontrado para el ID: " + idUsuario);
	    }

	    // Retornar la reserva específica en lugar de crear una nueva ReservaIndividual
	    return reservaEspecifica;  // Retorna la reservaEspecifica con el descuento aplicado
	}



    @Override
    public Reserva crearReservaBono(String tipoUsuario, int idUsuario, Date fechaHora, int duracionMinutos, int idPista, int numeroAdultos, int numeroNinos, Bono bono, int numeroSesion) {
        throw new UnsupportedOperationException("ReservaIndividualFactory no puede crear reservas de bono.");
    }
}
