package es.uco.pw.classes.reserva;

import java.util.Date;

public class ReservaIndividual extends Reserva {
    private Reserva reservaEspecifica; // Contendrá la reserva específica (infantil, adulto, familiar)

    // Constructor que incluye la reserva específica
    public ReservaIndividual(int idUsuario, Date fechaHora, int duracionMinutos, int idPista, Reserva reservaEspecifica) {
        super(idUsuario, fechaHora, duracionMinutos, idPista);
        this.reservaEspecifica = reservaEspecifica; // Guardar la reserva específica
    }

    // Getter para la reserva específica
    public Reserva getReservaEspecifica() {
        return reservaEspecifica;
    }

    // Método toString modificado para mostrar el tipo de reserva específica@Override
    @Override
    public String toString() {
        String detallesEspecificos = "";

        if (reservaEspecifica instanceof ReservaInfantil) {
            detallesEspecificos = ((ReservaInfantil) reservaEspecifica).toStringEspecifica();
        } else if (reservaEspecifica instanceof ReservaAdulto) {
            detallesEspecificos = ((ReservaAdulto) reservaEspecifica).toStringEspecifica();
        } else if (reservaEspecifica instanceof ReservaFamiliar) {
            detallesEspecificos = ((ReservaFamiliar) reservaEspecifica).toStringEspecifica();
        }

        return "ReservaIndividual [" + super.toString() + 
               ", reservaEspecifica=" + detallesEspecificos + "]";
    }
}

