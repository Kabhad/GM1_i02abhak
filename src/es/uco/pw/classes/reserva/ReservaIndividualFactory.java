package es.uco.pw.classes.reserva;

import java.util.Date;

public class ReservaIndividualFactory extends ReservaBonoFactory {

    @Override
    public Reserva crearReserva(String tipoUsuario, int idUsuario, Date fechaHora, int duracionMinutos, int idPista, int numeroAdultos, int numeroNinos, boolean tieneAntiguedad, Bono bono, int numeroSesion) {
        Reserva reserva;

        switch (tipoUsuario.toLowerCase()) {
            case "infantil":
                reserva = new ReservaInfantil(idUsuario, fechaHora, duracionMinutos, idPista, numeroNinos);
                break;
            case "familiar":
                reserva = new ReservaFamiliar(idUsuario, fechaHora, duracionMinutos, idPista, numeroAdultos, numeroNinos);
                break;
            case "adulto":
                reserva = new ReservaAdulto(idUsuario, fechaHora, duracionMinutos, idPista, numeroAdultos);
                break;
            default:
                throw new IllegalArgumentException("Tipo de usuario no válido: " + tipoUsuario);
        }

        if (tieneAntiguedad) {
            reserva.setDescuento(0.10f); // Aplica el 10% de descuento si corresponde
        }

        return reserva;
    }
}
