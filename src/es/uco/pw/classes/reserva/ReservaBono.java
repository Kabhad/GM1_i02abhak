package es.uco.pw.classes.reserva;

import java.util.Date;

public class ReservaBono extends Reserva {
    private int idBono;
    private int numeroSesion;
    private Bono bono;

    // Constructor vacío
    public ReservaBono() {
        super();
    }

    // Constructor parametrizado
    public ReservaBono(int idUsuario, Date fechaHora, int duracionMinutos, int idPista, Bono bono, int numeroSesion) {
        super(idUsuario, fechaHora, duracionMinutos, idPista);
        this.bono = bono;
        this.idBono = bono.getIdBono();
        this.numeroSesion = numeroSesion;
        this.setDescuento(0.05f); // Descuento del 5% para todas las reservas de bono
        bono.consumirSesion(); // Consume una sesión del bono
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

    @Override
    public String toString() {
        return "ReservaBono [idBono=" + idBono + ", numeroSesion=" + numeroSesion + ", bono=" + bono + "]";
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
