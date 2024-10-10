package Clases;

import java.util.Date;

public class ReservaFactory {

    // Método para crear una reserva individual
    public static abstractReserva crearReservaIndividual(String tipo, int idUsuario, Date fechaHora, int duracionMinutos, int idPista, float precio, float descuento, int numeroParticipantes) {
        switch (tipo.toLowerCase()) {
            case "infantil":
                return new ReservaInfantil(idUsuario, fechaHora, duracionMinutos, idPista, precio, descuento, numeroParticipantes);
            case "familiar":
                int numeroAdultos = Math.max(1, numeroParticipantes);
                int numeroNinos = numeroParticipantes - numeroAdultos;
                return new ReservaFamiliar(idUsuario, fechaHora, duracionMinutos, idPista, precio, descuento, numeroAdultos, numeroNinos);
            case "adulto":
                return new ReservaAdulto(idUsuario, fechaHora, duracionMinutos, idPista, precio, descuento, numeroParticipantes);
            default:
                throw new IllegalArgumentException("Tipo de reserva no válido: " + tipo);
        }
    }

    // Método para crear una reserva de bono
    public static abstractReserva crearReservaBono(String tipo, int idUsuario, Date fechaHora, int duracionMinutos, int idPista, float precio, float descuento, int idBono, int numeroSesion) {
        switch (tipo.toLowerCase()) {
            case "infantil":
                return new ReservaBono(idUsuario, fechaHora, duracionMinutos, idPista, precio, descuento, idBono, numeroSesion, 0, numeroSesion); // número de niños es igual a numeroSesion
            case "familiar":
                int numeroAdultos = Math.max(1, numeroSesion); // Al menos un adulto
                int numeroNinos = numeroSesion - numeroAdultos;
                return new ReservaBono(idUsuario, fechaHora, duracionMinutos, idPista, precio, descuento, idBono, numeroSesion, numeroAdultos, numeroNinos);
            case "adulto":
                return new ReservaBono(idUsuario, fechaHora, duracionMinutos, idPista, precio, descuento, idBono, numeroSesion, numeroSesion, 0); // todos son adultos
            default:
                throw new IllegalArgumentException("Tipo de reserva no válido: " + tipo);
        }
    }

}

