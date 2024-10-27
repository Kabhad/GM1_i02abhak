package es.uco.pw.gestores.jugadores;

import es.uco.pw.classes.jugador.Jugador;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 * Clase que gestiona los jugadores registrados en el sistema. Permite operaciones como
 * alta, baja, modificación, y almacenamiento de jugadores en un archivo CSV.
 * Implementa el patrón Singleton para garantizar una única instancia del gestor.
 */
public class GestorJugadores {
    
    private List<Jugador> listaJugadores;
    private String ficheroJugadoresPath;
    private static GestorJugadores instancia; // Singleton

    /**
     * Constructor privado para implementar el patrón Singleton.
     */
    private GestorJugadores() {
        listaJugadores = new ArrayList<>();
        cargarRutaFicheros();
    }

    /**
     * Obtiene la única instancia de GestorJugadores.
     *
     * @return La instancia única del gestor de jugadores.
     */
    public static synchronized GestorJugadores getInstance() {
        if (instancia == null) {
            instancia = new GestorJugadores();
        }
        return instancia;
    }

    /**
     * Da de alta a un nuevo jugador en el sistema. Si el correo ya existe, reactiva la cuenta y actualiza los datos.
     *
     * @param nuevoJugador El nuevo jugador a registrar.
     * @return Mensaje indicando el resultado de la operación.
     */
    public String altaJugador(Jugador nuevoJugador) {
        for (Jugador jugador : listaJugadores) {
            if (jugador.getCorreoElectronico().equalsIgnoreCase(nuevoJugador.getCorreoElectronico())) {
                if (jugador.isCuentaActiva()) {
                    return "Error: el correo ya está registrado y en uso.";
                } else {
                    jugador.setCuentaActiva(true);
                    jugador.setNombreApellidos(nuevoJugador.getNombreApellidos());
                    jugador.setFechaNacimiento(nuevoJugador.getFechaNacimiento());
                    jugador.setFechaInscripcion(new Date());
                    return "Cuenta reactivada y datos actualizados con éxito.";
                }
            }
        }
        nuevoJugador.setIdJugador(listaJugadores.size() + 1001);
        nuevoJugador.setCuentaActiva(true);
        nuevoJugador.setFechaInscripcion(new Date());
        listaJugadores.add(nuevoJugador);
        return "Jugador registrado con éxito.";
    }

    /**
     * Da de baja a un jugador desactivando su cuenta.
     *
     * @param correoElectronico El correo del jugador a dar de baja.
     * @return Mensaje indicando el resultado de la operación.
     */
    public String bajaJugador(String correoElectronico) {
        for (Jugador jugador : listaJugadores) {
            if (jugador.getCorreoElectronico().equalsIgnoreCase(correoElectronico)) {
                if (!jugador.isCuentaActiva()) {
                    return "Error: El jugador ya está dado de baja.";
                }
                jugador.setCuentaActiva(false);
                return "Jugador dado de baja correctamente.";
            }
        }
        return "Error: No se encontró el jugador.";
    }

    /**
     * Modifica los datos de un jugador en el sistema.
     *
     * @param correoElectronico     El correo del jugador a modificar.
     * @param nuevoNombre           Nuevo nombre del jugador.
     * @param nuevaFechaNacimiento  Nueva fecha de nacimiento del jugador.
     * @param nuevoCorreo           Nuevo correo del jugador.
     * @return Mensaje indicando el resultado de la operación.
     */
    public String modificarJugador(String correoElectronico, String nuevoNombre, Date nuevaFechaNacimiento, String nuevoCorreo) {
        for (Jugador jugador : listaJugadores) {
            if (jugador.getCorreoElectronico().equalsIgnoreCase(correoElectronico)) {
                if (!jugador.isCuentaActiva()) {
                    return "Error: La cuenta del jugador no está activa.";
                }
                for (Jugador usuario : listaJugadores) {
                    if (!usuario.getCorreoElectronico().equalsIgnoreCase(correoElectronico) &&
                        usuario.getCorreoElectronico().equalsIgnoreCase(nuevoCorreo)) {
                        return "Error: El nuevo correo ya está en uso.";
                    }
                }
                jugador.setNombreApellidos(nuevoNombre);
                jugador.setFechaNacimiento(nuevaFechaNacimiento);
                jugador.setCorreoElectronico(nuevoCorreo);
                return "Modificación realizada con éxito.";
            }
        }
        return "Error: No se encontró el jugador.";
    }

    /**
     * Lista todos los jugadores activos en el sistema.
     *
     * @return Cadena con la lista de jugadores activos o un mensaje si no hay jugadores activos.
     */
    public String listarJugadores() {
        if (listaJugadores.isEmpty()) {
            return "La lista de jugadores está vacía.";
        }

        StringBuilder resultado = new StringBuilder("Listando jugadores activos:\n");
        boolean hayJugadoresActivos = false;

        for (Jugador jugador : listaJugadores) {
            if (jugador.isCuentaActiva()) {
                hayJugadoresActivos = true;
                resultado.append("ID: ").append(jugador.getIdJugador()).append("\n")
                         .append("Nombre: ").append(jugador.getNombreApellidos()).append("\n")
                         .append("Fecha de Nacimiento: ").append(new SimpleDateFormat("dd/MM/yyyy").format(jugador.getFechaNacimiento())).append("\n")
                         .append("Fecha de Inscripción: ").append(jugador.getFechaInscripcion() != null ?
                             new SimpleDateFormat("dd/MM/yyyy").format(jugador.getFechaInscripcion()) : "No inscrito").append("\n")
                         .append("Correo Electrónico: ").append(jugador.getCorreoElectronico()).append("\n")
                         .append("----------------------------------\n");
            }
        }
        if (!hayJugadoresActivos) {
            return "No hay jugadores activos en la lista.";
        }
        return resultado.toString();
    }

    /**
     * Carga la ruta de los archivos desde un archivo de propiedades.
     */
    private void cargarRutaFicheros() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/Ficheros/properties.txt")) {
            properties.load(fis);
            this.ficheroJugadoresPath = properties.getProperty("jugadoresFile");
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el fichero de propiedades: " + e.getMessage());
        }
    }

    /**
     * Carga jugadores desde un archivo CSV.
     *
     * @return Mensaje indicando el resultado de la carga.
     */
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
                boolean cuentaActiva = Boolean.parseBoolean(datos[5]);

                Jugador jugador = new Jugador(nombreApellidos, fechaNacimiento, correoElectronico);
                jugador.setIdJugador(idJugador);
                jugador.setFechaInscripcion("null".equals(fechaInscripcionStr) ? null :
                    new SimpleDateFormat("yyyy-MM-dd").parse(fechaInscripcionStr));
                jugador.setCuentaActiva(cuentaActiva);

                listaJugadores.add(jugador);
            }
            return "Jugadores cargados desde el fichero CSV.";
        } catch (IOException | ParseException e) {
            return "Error al cargar los jugadores: " + e.getMessage();
        }
    }

    /**
     * Guarda los jugadores en un archivo CSV.
     *
     * @return Mensaje indicando el resultado del guardado.
     */
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
                  .append(jugador.isCuentaActiva());
                
                bw.write(sb.toString());
                bw.newLine();
            }
            return "Jugadores guardados en el fichero CSV.";
        } catch (IOException e) {
            return "Error al guardar los jugadores: " + e.getMessage();
        }
    }

    /**
     * Busca un jugador por su correo electrónico.
     *
     * @param correoElectronico El correo del jugador a buscar.
     * @return El jugador encontrado o null si no existe.
     */
    public Jugador buscarJugadorPorCorreo(String correoElectronico) {
        for (Jugador jugador : listaJugadores) {
            if (jugador.getCorreoElectronico().equalsIgnoreCase(correoElectronico)) {
                return jugador;
            }
        }
        return null;
    }

    /**
     * Busca un jugador por su ID.
     *
     * @param idJugador El ID del jugador a buscar.
     * @return El jugador encontrado o null si no existe.
     */
    public Jugador buscarJugadorPorId(int idJugador) {
        for (Jugador jugador : listaJugadores) {
            if (jugador.getIdJugador() == idJugador) {
                return jugador;
            }
        }
        return null;
    }
}
