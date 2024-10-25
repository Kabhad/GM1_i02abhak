package es.uco.pw.gestores.jugadores;

import es.uco.pw.classes.jugador.Jugador;
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
    private static GestorJugadores instancia; // Singleton

    // Constructor privado
    private GestorJugadores() {
        listaJugadores = new ArrayList<>();
        cargarRutaFicheros();
    }

    // Obtener la única instancia del gestor (patrón Singleton)
    public static synchronized GestorJugadores getInstance() {
        if (instancia == null) {
            instancia = new GestorJugadores();
        }
        return instancia;
    }

 // Alta de un jugador
    public String altaJugador(Jugador nuevoJugador) {
        for (Jugador jugador : listaJugadores) {
            if (jugador.getCorreoElectronico().equalsIgnoreCase(nuevoJugador.getCorreoElectronico())) {
                if (jugador.isCuentaActiva()) {
                    // Si la cuenta ya está activa
                    return "Error: el correo ya está registrado y en uso.";
                } else {
                    // Si la cuenta estaba inactiva, se reactiva
                    jugador.setCuentaActiva(true);
                    jugador.setNombreApellidos(nuevoJugador.getNombreApellidos());
                    jugador.setFechaNacimiento(nuevoJugador.getFechaNacimiento());
                    jugador.setFechaInscripcion(new Date()); // Actualizar la fecha de inscripción a la actual
                    return "Cuenta reactivada y datos actualizados con éxito.";
                }
            }
        }

        // Si el correo no existía, se registra el nuevo jugador
        nuevoJugador.setIdJugador(listaJugadores.size() + 1001);
        nuevoJugador.setCuentaActiva(true); // La cuenta está activa por defecto al crearla
        nuevoJugador.setFechaInscripcion(new Date()); // Fecha de inscripción a la actual
        listaJugadores.add(nuevoJugador);
        return "Jugador registrado con éxito.";
    }

    
    // Dar de baja a un jugador
    public String bajaJugador(String correoElectronico) {
        for (Jugador jugador : listaJugadores) {
            if (jugador.getCorreoElectronico().equalsIgnoreCase(correoElectronico)) {
                if (!jugador.isCuentaActiva()) {
                    return "Error: El jugador ya está dado de baja.";
                }
                jugador.setCuentaActiva(false); // Desactivar la cuenta
                return "Jugador dado de baja correctamente.";
            }
        }
        return "Error: No se encontró el jugador.";
    }

    public String modificarJugador(String correoElectronico, String nuevoNombre, Date nuevaFechaNacimiento, String nuevoCorreo) {
        for (Jugador jugador : listaJugadores) {
            if (jugador.getCorreoElectronico().equalsIgnoreCase(correoElectronico)) {

                // Verificación de si la cuenta está activa
                if (!jugador.isCuentaActiva()) {
                    return "Error: La cuenta del jugador no está activa.";
                }

                // Comprobación de que el nuevo correo no esté en uso por otro jugador
                for (Jugador usuario : listaJugadores) {
                    if (!usuario.getCorreoElectronico().equalsIgnoreCase(correoElectronico) &&
                        usuario.getCorreoElectronico().equalsIgnoreCase(nuevoCorreo)) {
                        return "Error: El nuevo correo ya está en uso.";
                    }
                }

                // Realización de la modificación
                jugador.setNombreApellidos(nuevoNombre);
                jugador.setFechaNacimiento(nuevaFechaNacimiento);
                jugador.setCorreoElectronico(nuevoCorreo);
                return "Modificación realizada con éxito.";
            }
        }
        return "Error: No se encontró el jugador.";
    }


 // Listar jugadores activos
    public String listarJugadores() {
        if (listaJugadores.isEmpty()) {
            return "La lista de jugadores está vacía.";
        }

        StringBuilder resultado = new StringBuilder("Listando jugadores activos:\n");
        boolean hayJugadoresActivos = false;  // Variable para verificar si existen jugadores activos

        for (Jugador jugador : listaJugadores) {
            if (jugador.isCuentaActiva()) {
                hayJugadoresActivos = true; // Se encontró al menos un jugador activo
                resultado.append("ID: ").append(jugador.getIdJugador()).append("\n")
                         .append("Nombre: ").append(jugador.getNombreApellidos()).append("\n")
                         .append("Fecha de Nacimiento: ").append(new SimpleDateFormat("dd/MM/yyyy").format(jugador.getFechaNacimiento())).append("\n")
                         .append("Fecha de Inscripción: ").append(jugador.getFechaInscripcion() != null ?
                             new SimpleDateFormat("dd/MM/yyyy").format(jugador.getFechaInscripcion()) : "No inscrito").append("\n")
                         .append("Correo Electrónico: ").append(jugador.getCorreoElectronico()).append("\n")
                         .append("----------------------------------\n");
            }
        }

        // Si no se encontraron jugadores activos, cambiar el mensaje a uno específico
        if (!hayJugadoresActivos) {
            return "No hay jugadores activos en la lista.";
        }

        return resultado.toString();
    }


    // Cargar la ruta de ficheros desde un archivo properties
    private void cargarRutaFicheros() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/Ficheros/properties.txt")) {
            properties.load(fis);
            this.ficheroJugadoresPath = properties.getProperty("jugadoresFile");
        } catch (IOException e) {
            // Devolver un error en vez de imprimirlo
            throw new RuntimeException("Error al leer el fichero de propiedades: " + e.getMessage());
        }
    }

    // Cargar jugadores desde un fichero CSV
    public String cargarJugadoresDesdeFichero() {
        try (BufferedReader br = new BufferedReader(new FileReader(ficheroJugadoresPath))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                int idJugador = Integer.parseInt(datos[0]);
                String nombreApellidos = datos[1];
                Date fechaNacimiento = new SimpleDateFormat("yyyy-MM-dd").parse(datos[2]);
                String fechaInscripcionStr = datos[3];
                String correoElectronico = datos[4];
                boolean cuentaActiva = Boolean.parseBoolean(datos[5]); // Leer el valor de cuentaActiva

                Jugador jugador = new Jugador(nombreApellidos, fechaNacimiento, correoElectronico);
                jugador.setIdJugador(idJugador);
                jugador.setFechaInscripcion("null".equals(fechaInscripcionStr) ? null :
                    new SimpleDateFormat("yyyy-MM-dd").parse(fechaInscripcionStr));
                jugador.setCuentaActiva(cuentaActiva); // Asignar el estado de la cuenta

                listaJugadores.add(jugador);
            }
            return "Jugadores cargados desde el fichero CSV.";
        } catch (IOException | ParseException e) {
            return "Error al cargar los jugadores: " + e.getMessage();
        }
    }

    // Guardar jugadores en un fichero CSV
    public String guardarJugadoresEnFichero() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroJugadoresPath))) {
            for (Jugador jugador : listaJugadores) {
                StringBuilder sb = new StringBuilder();
                sb.append(jugador.getIdJugador()).append(";")
                  .append(jugador.getNombreApellidos()).append(";")
                  .append(new SimpleDateFormat("yyyy-MM-dd").format(jugador.getFechaNacimiento())).append(";")
                  .append(jugador.getFechaInscripcion() != null ? 
                      new SimpleDateFormat("yyyy-MM-dd").format(jugador.getFechaInscripcion()) : "null").append(";")
                  .append(jugador.getCorreoElectronico()).append(";")
                  .append(jugador.isCuentaActiva()); // Guardar el estado de la cuenta (true/false)
                
                bw.write(sb.toString());
                bw.newLine();
            }
            return "Jugadores guardados en el fichero CSV.";
        } catch (IOException e) {
            return "Error al guardar los jugadores: " + e.getMessage();
        }
    }
    
	// Método para buscar un jugador por su correo electrónico
	public Jugador buscarJugadorPorCorreo(String correoElectronico) {
	    for (Jugador jugador : listaJugadores) {
	        if (jugador.getCorreoElectronico().equalsIgnoreCase(correoElectronico)) {
	            return jugador; // Jugador encontrado
	        }
	    }
	    return null; // Jugador no encontrado
	}

    public Jugador buscarJugadorPorId(int idJugador) {
        for (Jugador jugador : listaJugadores) {
            if (jugador.getIdJugador() == idJugador) {
                return jugador;
            }
        }
        return null; // Jugador no encontrado
    }
}
