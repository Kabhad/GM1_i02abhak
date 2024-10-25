package es.uco.pw.classes.jugador;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Jugador 
{
	// Atributos
	private static int idCounter = 1; // Contador estático para generar IDs únicos
	private int idJugador;
	private String nombreApellidos;
    private Date fechaNacimiento;
    private Date fechaInscripcion;
    private String correoElectronico;
    private boolean cuentaActiva = true; // Campo para indicar si la cuenta está activa
    
    // Constructor vacío (sin parámetros)
    public Jugador() 
    {
       this.idJugador = idCounter++; // Asignar un ID único y aumentar el contador
       this.cuentaActiva = true; // La cuenta es activa por defecto
    }
    
	// Constructor parametrizado (sin la fecha de inscripción)
    public Jugador(String nombreApellidos, Date fechaNacimiento, String correoElectronico) {
        this();
        this.nombreApellidos = nombreApellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.correoElectronico = correoElectronico;
    }
    

    // Métodos getter y setter 
	public int getIdJugador() {
		return idJugador;
	}
	
    public void setIdJugador(int idJugador) {
		this.idJugador = idJugador;
	}
    
    public boolean isCuentaActiva() {
        return cuentaActiva;
    }

    public void setCuentaActiva(boolean cuentaActiva) {
        this.cuentaActiva = cuentaActiva;
    }
    
	public String getNombreApellidos() {
		return nombreApellidos;
	}

	public void setNombreApellidos(String nombreApellidos) {
		this.nombreApellidos = nombreApellidos;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Date getFechaInscripcion() {
		return fechaInscripcion;
	}

	public void setFechaInscripcion(Date fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
    
    // Método toString para imprimir la información del usuario
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return "ID: " + idJugador + 
		       "\nNombre: " + nombreApellidos +
		       "\nFecha de Nacimiento: " + sdf.format(fechaNacimiento) +
		       "\nFecha de Inscripción: " + sdf.format(fechaInscripcion) +
		       "\nCorreo Electrónico: " + correoElectronico;
	}
	
	// Método calcularAntiguedad que indica cuántos años lleva registrado
	public int calcularAntiguedad() {
		if (fechaInscripcion == null) {
			return 0; // Si no está inscrito, no tiene antigüedad
		}
		Calendar fechaActual = Calendar.getInstance();
		Calendar fechaInscripcionCal = Calendar.getInstance();
		fechaInscripcionCal.setTime(fechaInscripcion);
		
		int aniosAntiguedad = fechaActual.get(Calendar.YEAR) - fechaInscripcionCal.get(Calendar.YEAR);
		
		// Si aún no ha pasado la fecha de inscripción en este año, restamos 1
		if (fechaActual.get(Calendar.DAY_OF_YEAR) < fechaInscripcionCal.get(Calendar.DAY_OF_YEAR)) {
            aniosAntiguedad--;
        }
		
		return aniosAntiguedad;
	} 
}
