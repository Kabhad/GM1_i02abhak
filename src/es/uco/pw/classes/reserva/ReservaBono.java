package es.uco.pw.classes.reserva;

import java.util.Date;

public class ReservaBono extends Reserva {
    private int idBono;
    private int numeroSesion;
    private Bono bono;
    private Reserva reservaEspecifica; // Contiene la reserva específica (infantil, adulto, familiar)
    private boolean confirmada = false;

    // Constructor que incluye la reserva específica
    public ReservaBono(int idUsuario, Date fechaHora, int duracionMinutos, int idPista, Bono bono, int numeroSesion, Reserva reservaEspecifica) {
        super(idUsuario, fechaHora, duracionMinutos, idPista);
        this.bono = bono;
        this.idBono = bono.getIdBono();
        this.numeroSesion = numeroSesion;
        this.reservaEspecifica = reservaEspecifica; // Guardar la reserva específica (infantil, adulto, familiar)
        this.setDescuento(0.05f); // Descuento del 5% para todas las reservas de bono
    }

    // Getter para la reserva específica
    public Reserva getReservaEspecifica() {
        return reservaEspecifica;
    }

    //Método toString() para reservas de bono
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

        return "ReservaBono [" + super.toString() + 
               ", idBono=" + idBono +
               ", numeroSesion=" + numeroSesion +
               ", bono=" + bono +
               ", reservaEspecifica=" + detallesEspecificos + "]";
    }


    public void confirmarReserva() {
        if (!confirmada) {
            bono.consumirSesion(); // Consume la sesión solo si la reserva se confirma
            confirmada = true; // Cambia el estado a confirmado
        } else {
            throw new IllegalStateException("La reserva ya ha sido confirmada.");
        }
    }

    // Método para crear una reserva de bono de tipo infantil
    public ReservaInfantil crearReservaInfantil(int idUsuario, Date fechaHora, int duracionMinutos, int idPista, int numeroNinos) {
        ReservaInfantil reserva = new ReservaInfantil(idUsuario, fechaHora, duracionMinutos, idPista, numeroNinos);
        reserva.setDescuento(0.05f); // Descuento para bono
        this.consumirSesion(); // Consume una sesión del bono
        return reserva;
    }

    // Método para crear una reserva de bono de tipo familiar
    public ReservaFamiliar crearReservaFamiliar(int idUsuario, Date fechaHora, int duracionMinutos, int idPista, int numeroAdultos, int numeroNinos) {
        ReservaFamiliar reserva = new ReservaFamiliar(idUsuario, fechaHora, duracionMinutos, idPista, numeroAdultos, numeroNinos);
        reserva.setDescuento(0.05f); // Descuento para bono
        this.consumirSesion(); // Consume una sesión del bono
        return reserva;
    }

    // Método para crear una reserva de bono de tipo adulto
    public ReservaAdulto crearReservaAdulto(int idUsuario, Date fechaHora, int duracionMinutos, int idPista, int numeroAdultos) {
        ReservaAdulto reserva = new ReservaAdulto(idUsuario, fechaHora, duracionMinutos, idPista, numeroAdultos);
        reserva.setDescuento(0.05f); // Descuento para bono
        this.consumirSesion(); // Consume una sesión del bono
        return reserva;
    }

    // Otros métodos de la clase
    public int getIdBono() {
        return idBono;
    }

    public void setIdBono(int idBono) {
        this.idBono = idBono;
    }

    public int getNumeroSesion() {
        return numeroSesion;
    }

    public void setNumeroSesion(int numeroSesion) {
        this.numeroSesion = numeroSesion;
    }

    public Bono getBono() {
        return bono;
    }

    public void setBono(Bono bono) {
        this.bono = bono;
    }

    // Método para consumir una sesión del bono
    public void consumirSesion() {
        if (bono.getSesionesRestantes() > 0) {
            bono.consumirSesion();
        } else {
            throw new IllegalStateException("No quedan sesiones en el bono");
        }
    }
}
