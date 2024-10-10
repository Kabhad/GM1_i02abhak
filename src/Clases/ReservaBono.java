package Clases;
import java.util.Date;

public class ReservaBono extends abstractReserva {
    // Atributos específicos de ReservaBono
    private int idBono;
    private int numeroSesion;
    private int numeroAdultos;
    private int numeroNinos;

    // Constructor vacío
    public ReservaBono() {
        super();
    }

    // Constructor parametrizado con información específica para bonos familiares
    public ReservaBono(int idUsuario, Date fechaHora, int duracionMinutos, int idPista, float precio, float descuento, int idBono, int numeroSesion, int numeroAdultos, int numeroNinos) {
        super(idUsuario, fechaHora, duracionMinutos, idPista, precio, descuento);
        this.idBono = idBono;
        this.numeroSesion = numeroSesion;
        this.numeroAdultos = numeroAdultos;
        this.numeroNinos = numeroNinos;
    }

    // Métodos get y set para los nuevos atributos
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

    public int getNumeroAdultos() {
        return numeroAdultos;
    }

    public void setNumeroAdultos(int numeroAdultos) {
        this.numeroAdultos = numeroAdultos;
    }

    public int getNumeroNinos() {
        return numeroNinos;
    }

    public void setNumeroNinos(int numeroNinos) {
        this.numeroNinos = numeroNinos;
    }

    @Override
    public String toString() {
        return "ReservaBono: " + super.toString() + ", idBono=" + idBono + ", numeroSesion=" + numeroSesion +
               ", numeroAdultos=" + numeroAdultos + ", numeroNinos=" + numeroNinos + "]";
    }
}
