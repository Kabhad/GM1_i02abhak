package Clases;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Jugador 
{
	//Atributos
	private String nombreApellidos;
    private Date fechaNacimiento;
    private Date fechaInscripcion;
    private String correoElectronico;
    
    // Constructor vacío (sin parámetros)
    public Jugador() 
    {
       this.fechaInscripcion = new Date(); // Fecha de inscripción será la actual
    }
    
    // Constructor parametrizado (sin la fecha de inscripción)
    public Jugador(String nombreApellidos, Date fechaNacimiento, String correoElectronico) {
        this.nombreApellidos = nombreApellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.correoElectronico = correoElectronico;
        this.fechaInscripcion = new Date(); // Fecha de inscripción será la actual
    }

    //Métodos getter y setter para todos los atributos
	public String getNombreApellidos() 
	{
		return nombreApellidos;
	}

	public void setNombreApellidos(String nombreApellidos) 
	{
		this.nombreApellidos = nombreApellidos;
	}

	public Date getFechaNacimiento() 
	{
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) 
	{
		this.fechaNacimiento = fechaNacimiento;
	}

	public Date getFechaInscripcion() 
	{
		return fechaInscripcion;
	}

	public void setFechaInscripcion(Date fechaInscripcion) 
	{
		this.fechaInscripcion = fechaInscripcion;
	}

	public String getCorreoElectronico() 
	{
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) 
	{
		this.correoElectronico = correoElectronico;
	}
    
    //Método toString para imprimir la información del usuario
	@Override
	public String toString()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		return "Nombre: " + nombreApellidos +
	               "\nFecha de Nacimiento: " + sdf.format(fechaNacimiento) +
	               "\nFecha de Inscripción: " + sdf.format(fechaInscripcion) +
	               "\nCorreo Electrónico: " + correoElectronico;
	}
	
	//Metodo calcularAntiguedad que indica cuantos años lleva registrado
	public int calcularAntiguedad()
	{
		Calendar fechaActual = Calendar.getInstance();
		Calendar fechaInscripcionCal = Calendar.getInstance();
		fechaInscripcionCal.setTime(fechaInscripcion);
		
		int aniosAntiguedad = fechaActual.get(Calendar.YEAR) - fechaInscripcionCal.get(Calendar.YEAR);
		
		//Si aun no ha pasado a fecha de inscripcion en este año, restamos 1
		if (fechaActual.get(Calendar.DAY_OF_YEAR) < fechaInscripcionCal.get(Calendar.DAY_OF_YEAR)) 
		{
            aniosAntiguedad--;
        }
		
		return aniosAntiguedad;
	} 
}
