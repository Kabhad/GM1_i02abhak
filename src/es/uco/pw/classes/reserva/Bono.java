package es.uco.pw.classes.reserva;

import java.util.Date;
import java.util.Calendar;

public class Bono {
    private int idBono;
    private int idUsuario;
    private int sesionesRestantes = 5; // Comienza con 5 sesiones
    private Date fechaCaducidad; // Un año desde la primera reserva

    // Constructor vacío
    public Bono() {
    }

    // Constructor parametrizado
    public Bono(int idBono, int idUsuario, Date fechaPrimeraReserva) {
        this.idBono = idBono;
        this.idUsuario = idUsuario;
        this.fechaCaducidad = calcularFechaCaducidad(fechaPrimeraReserva);
    }

    private Date calcularFechaCaducidad(Date fechaPrimeraReserva) {
        // Lógica para calcular la fecha de caducidad a partir de la primera reserva (un año)
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaPrimeraReserva);
        cal.add(Calendar.YEAR, 1); // Añade un año
        return cal.getTime();
    }

    public boolean estaCaducado() {
        return new Date().after(fechaCaducidad);
    }

    public void consumirSesion() {
        if (sesionesRestantes > 0) {
            sesionesRestantes--;
        } else {
            throw new IllegalStateException("No quedan sesiones en el bono");
        }
    }

    // Getters y Setters
    public int getIdBono() {
        return idBono;
    }

    public void setIdBono(int idBono) {
        this.idBono = idBono;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getSesionesRestantes() {
        return sesionesRestantes;
    }

    public void setSesionesRestantes(int sesionesRestantes) {
        this.sesionesRestantes = sesionesRestantes;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    @Override
    public String toString() {
        return "Bono [idBono=" + idBono + ", idUsuario=" + idUsuario + ", sesionesRestantes=" + sesionesRestantes
                + ", fechaCaducidad=" + fechaCaducidad + "]";
    }
}
