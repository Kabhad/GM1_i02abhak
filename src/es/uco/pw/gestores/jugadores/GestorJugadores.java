package es.uco.pw.gestores.jugadores;

import es.uco.pw.classes.jugador.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class GestorJugadores {
    private List<Jugador> listaJugadores;
    private String ficheroJugadoresPath;
    private static GestorJugadores instancia; // Variable estática para la instancia del Singleton

    // Constructor privado
    private GestorJugadores() {
        listaJugadores = new ArrayList<>();
        cargarRutaFicheros(); // Cargar la ruta del fichero desde properties.txt
    }

    // Método estático para obtener la única instancia del gestor
    public static synchronized GestorJugadores getInstance() {
        if (instancia == null) {
            instancia = new GestorJugadores();
        }
        return instancia;
    }
    
 // Método para dar de alta a un usuario
    public boolean altaJugador(Jugador nuevoJugador) {
        // Verificar si el correo electrónico ya está registrado
        for (Jugador jugador : listaJugadores) {
            if (jugador.getCorreoElectronico().equalsIgnoreCase(nuevoJugador.getCorreoElectronico())) {
                return false; // Jugador ya está registrado, retorna false
            }
        }

        // Asignar ID si no está asignado
        nuevoJugador.setIdJugador(listaJugadores.size() + 1001); // Asegúrate de que el ID se asigne correctamente

        // Si no está registrado, agregar a la lista de jugadores
        listaJugadores.add(nuevoJugador);
        return true; // Registro exitoso, retorna true
    }

    
    // Método para modificar la información de un usuario existente
    public int modificarJugador(String correoElectronico, String nuevoNombre, Date nuevaFechaNacimiento, String nuevoCorreo) {
        // Buscar el usuario por correo electrónico
        for (Jugador jugador : listaJugadores) {
            if (jugador.getCorreoElectronico().equalsIgnoreCase(correoElectronico)) {
                // Verificar si el nuevo correo ya está en uso (excepto el del propio usuario)
                for (Jugador usuario : listaJugadores) {
                    if (!usuario.getCorreoElectronico().equalsIgnoreCase(correoElectronico) &&
                        usuario.getCorreoElectronico().equalsIgnoreCase(nuevoCorreo)) {
                        return 0; // El nuevo correo ya está en uso
                    }
                }

                // Actualizar los datos del usuario
                jugador.setNombreApellidos(nuevoNombre);
                jugador.setFechaNacimiento(nuevaFechaNacimiento);
                jugador.setCorreoElectronico(nuevoCorreo);
                return 1; // Modificación exitosa
            }
        }

        // Si no se encuentra el usuario
        return -1; // Usuario no encontrado
    }
    
    // Método para listar a los usuarios registrados
    public String listarJugadores() {
        StringBuilder resultado = new StringBuilder();
        
        for (Jugador jugador : listaJugadores) {
            resultado.append("ID: ").append(jugador.getIdJugador()).append("\n")
                     .append("Nombre: ").append(jugador.getNombreApellidos()).append("\n")
                     .append("Fecha de Nacimiento: ").append(new SimpleDateFormat("dd/MM/yyyy").format(jugador.getFechaNacimiento())).append("\n")
                     .append("Fecha de Inscripción: ").append(jugador.getFechaInscripcion() != null ? 
                         new SimpleDateFormat("dd/MM/yyyy").format(jugador.getFechaInscripcion()) : "No inscrito").append("\n")
                     .append("Correo Electrónico: ").append(jugador.getCorreoElectronico()).append("\n")
                     .append("----------------------------------\n");
        }
        
        return resultado.toString();
    }

    // Método para comprobar si la lista está vacía
    public int listaVacia()
	{
        if (listaJugadores.isEmpty()) 
        {
            return 1;  // Lista vacía
        } 
        else 
        {
            return 0;  // Lista con jugadores
        }
    }

	//Método para cargar la ruta del fichero desde properties.txt
	private void cargarRutaFicheros() {
	    Properties properties = new Properties();
	    try (FileInputStream fis = new FileInputStream("src/Ficheros/properties.txt")) {
	        properties.load(fis);
	        this.ficheroJugadoresPath = properties.getProperty("jugadoresFile"); // Carga la ruta para jugadores
	    } catch (IOException e) {
	        System.out.println("Error al leer el fichero de propiedades: " + e.getMessage());
	    }
	}
	
	// Método para cargar jugadores desde un fichero CSV
	public void cargarJugadoresDesdeFichero() {
	    try (BufferedReader br = new BufferedReader(new FileReader(ficheroJugadoresPath))) {
	        String linea;
	        while ((linea = br.readLine()) != null) {
	            String[] datos = linea.split(";");

	            // Se espera que el ID del jugador sea el primer dato en el CSV
	            int idJugador = Integer.parseInt(datos[0]);
	            String nombreApellidos = datos[1];
	            Date fechaNacimiento = new SimpleDateFormat("yyyy-MM-dd").parse(datos[2]);
	            String fechaInscripcionStr = datos[3]; // Obtener fecha de inscripción
	            String correoElectronico = datos[4]; // Obtener correo electrónico

	            // Crear un nuevo objeto Jugador
	            Jugador jugador = new Jugador(nombreApellidos, fechaNacimiento, correoElectronico);
	            jugador.setIdJugador(idJugador); // Establecer el ID del jugador

	            // Establecer la fecha de inscripción, manejando el caso de "null"
	            if ("null".equals(fechaInscripcionStr)) {
	                jugador.setFechaInscripcion(null); // Si es "null", establecer a null
	            } else {
	                jugador.setFechaInscripcion(new SimpleDateFormat("yyyy-MM-dd").parse(fechaInscripcionStr)); // Convertir y establecer la fecha
	            }

	            listaJugadores.add(jugador); // Agregar a la lista de jugadores
	        }
	        System.out.println("Jugadores cargados desde el fichero CSV.");
	    } catch (IOException | ParseException e) {
	        System.out.println("Error al cargar los jugadores: " + e.getMessage());
	    }
	}


	// Método para guardar los jugadores en un fichero CSV
	public void guardarJugadoresEnFichero() {
	    try (BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroJugadoresPath))) {
	        for (Jugador jugador : listaJugadores) {
	            StringBuilder sb = new StringBuilder();
	            sb.append(jugador.getIdJugador()).append(";") // Guardar el ID del jugador
	              .append(jugador.getNombreApellidos()).append(";")
	              .append(new SimpleDateFormat("yyyy-MM-dd").format(jugador.getFechaNacimiento())).append(";")
	              .append(jugador.getFechaInscripcion() != null ? new SimpleDateFormat("yyyy-MM-dd").format(jugador.getFechaInscripcion()) : "null").append(";") // Fecha de inscripción
	              .append(jugador.getCorreoElectronico());

	            // Escribir la línea en el fichero
	            bw.write(sb.toString());
	            bw.newLine();
	        }
	        System.out.println("Jugadores guardados en el fichero CSV.");
	    } catch (IOException e) {
	        System.out.println("Error al guardar los jugadores: " + e.getMessage());
	    }
	}
	   
	// Método para buscar un jugador por su ID
    public Jugador buscarJugadorPorId(int idJugador) {
        for (Jugador jugador : listaJugadores) {
            if (jugador.getIdJugador() == idJugador) {
                return jugador;
            }
        }
        return null; // Retorna null si no encuentra el jugador
    }


}
