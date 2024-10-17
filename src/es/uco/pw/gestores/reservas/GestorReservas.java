package es.uco.pw.gestores.reservas;

import es.uco.pw.classes.jugador.Jugador;
import es.uco.pw.classes.pista.Pista;
import es.uco.pw.classes.reserva.*;

import es.uco.pw.classes.pista.TamanoPista;
import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.stream.Collectors;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class GestorReservas {
    private List<Reserva> reservas;
    private Map<String, Jugador> jugadores;
    private Map<Integer, Pista> pistas;
    private static GestorReservas instancia;
    private String ficheroReservasPath;

    // Constructor privado para evitar instanciación directa
    private GestorReservas() {
        this.reservas = new ArrayList<>();
        this.jugadores = new HashMap<>();
        this.pistas = new HashMap<>();
        this.cargarRutaFicheros();  // Cargar la ruta del fichero desde properties.txt
    }

    // Método estático para obtener la única instancia del gestor
    public static synchronized GestorReservas getInstance() {
        if (instancia == null) {
            instancia = new GestorReservas();
        }
        return instancia;
    }

    // Método para cargar la ruta del fichero desde properties.txt
    private void cargarRutaFicheros() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/Ficheros/properties.txt")) {
            properties.load(fis);
            this.ficheroReservasPath = properties.getProperty("reservasFile");
        } catch (IOException e) {
            System.out.println("Error al leer el fichero de propiedades: " + e.getMessage());
        }
    }
    
 // Método para cargar las reservas desde un fichero CSV
    public void cargarReservasDesdeFichero() {
        try (BufferedReader br = new BufferedReader(new FileReader(ficheroReservasPath))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");

                // Identificar si es una reserva individual o de bono
                String tipoReserva = datos[0];
                String tipoUsuario = datos[1];
                int idUsuario = Integer.parseInt(datos[2]);
                Date fechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(datos[3]);
                int duracionMinutos = Integer.parseInt(datos[4]);
                int idPista = Integer.parseInt(datos[5]);
                int numeroAdultos = Integer.parseInt(datos[6]);
                int numeroNinos = Integer.parseInt(datos[7]);

                Reserva reserva = null;

                if (tipoReserva.equalsIgnoreCase("individual")) {
                    // Crear una reserva individual sin recibir el valor de antigüedad desde el fichero
                    reserva = ReservaGeneralFactory.crearReserva(
                        tipoUsuario, 
                        idUsuario, 
                        fechaHora, 
                        duracionMinutos, 
                        idPista, 
                        numeroAdultos, 
                        numeroNinos, 
                        false,  // No necesitamos pasar "tieneAntiguedad", se calculará en la factoría
                        null, 
                        0
                    );
                } else if (tipoReserva.equalsIgnoreCase("bono")) {
                    // Crear una reserva de bono
                    int idBono = Integer.parseInt(datos[8]); // ID del bono
                    int numeroSesion = Integer.parseInt(datos[9]); // Número de sesiones

                    Bono bono = new Bono(idBono, idUsuario, numeroSesion, fechaHora);

                    // Crear la reserva específica dentro de la reserva bono (infantil, adulto o familiar)
                    reserva = ReservaGeneralFactory.crearReserva(
                        tipoUsuario, 
                        idUsuario, 
                        fechaHora, 
                        duracionMinutos, 
                        idPista, 
                        numeroAdultos, 
                        numeroNinos, 
                        false, // Se calculará la antigüedad en la factoría
                        bono, 
                        numeroSesion
                    );
                }

                // Añadir la reserva a la lista
                if (reserva != null) {
                    reservas.add(reserva);
                }
            }
            System.out.println("Reservas cargadas desde el fichero CSV.");
        } catch (IOException | ParseException e) {
            System.out.println("Error al cargar las reservas: " + e.getMessage());
        }
    }

 // Método para guardar las reservas en un fichero CSV
    public void guardarReservasEnFichero() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroReservasPath))) {
            for (Reserva reserva : reservas) {
                StringBuilder sb = new StringBuilder();

                // Comprobar si es una reserva de bono
                if (reserva instanceof ReservaBono) {
                    ReservaBono reservaBono = (ReservaBono) reserva;
                    Reserva reservaEspecifica = reservaBono.getReservaEspecifica();
                    String tipoUsuario = "";
                    int numeroAdultos = 0;
                    int numeroNinos = 0;

                    if (reservaEspecifica instanceof ReservaInfantil) {
                        tipoUsuario = "infantil";
                        numeroNinos = ((ReservaInfantil) reservaEspecifica).getNumeroNinos();
                    } else if (reservaEspecifica instanceof ReservaAdulto) {
                        tipoUsuario = "adulto";
                        numeroAdultos = ((ReservaAdulto) reservaEspecifica).getNumeroAdultos();
                    } else if (reservaEspecifica instanceof ReservaFamiliar) {
                        tipoUsuario = "familiar";
                        numeroAdultos = ((ReservaFamiliar) reservaEspecifica).getNumeroAdultos();
                        numeroNinos = ((ReservaFamiliar) reservaEspecifica).getNumeroNinos();
                    }

                    // Guardar los datos comunes de la reserva de bono sin el atributo "tieneAntiguedad"
                    sb.append("bono;").append(tipoUsuario).append(";")
                        .append(reservaBono.getIdUsuario()).append(";")
                        .append(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(reservaBono.getFechaHora())).append(";")
                        .append(reservaBono.getDuracionMinutos()).append(";")
                        .append(reservaBono.getIdPista()).append(";")
                        .append(numeroAdultos).append(";").append(numeroNinos).append(";")
                        .append(reservaBono.getBono().getIdBono()).append(";")
                        .append(reservaBono.getNumeroSesion()).append(";");

                } else {
                    sb.append("individual;");
                    if (reserva instanceof ReservaInfantil) {
                        sb.append("infantil;");
                        sb.append(reserva.getIdUsuario()).append(";")
                            .append(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(reserva.getFechaHora())).append(";")
                            .append(reserva.getDuracionMinutos()).append(";")
                            .append(reserva.getIdPista()).append(";")
                            .append("0;").append(((ReservaInfantil) reserva).getNumeroNinos()).append(";");
                    } else if (reserva instanceof ReservaAdulto) {
                        sb.append("adulto;");
                        sb.append(reserva.getIdUsuario()).append(";")
                            .append(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(reserva.getFechaHora())).append(";")
                            .append(reserva.getDuracionMinutos()).append(";")
                            .append(reserva.getIdPista()).append(";")
                            .append(((ReservaAdulto) reserva).getNumeroAdultos()).append(";0;");
                    } else if (reserva instanceof ReservaFamiliar) {
                        sb.append("familiar;");
                        sb.append(reserva.getIdUsuario()).append(";")
                            .append(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(reserva.getFechaHora())).append(";")
                            .append(reserva.getDuracionMinutos()).append(";")
                            .append(reserva.getIdPista()).append(";")
                            .append(((ReservaFamiliar) reserva).getNumeroAdultos()).append(";")
                            .append(((ReservaFamiliar) reserva).getNumeroNinos()).append(";");
                    }
                }

                // Escribir la línea en el fichero
                bw.write(sb.toString());
                bw.newLine();
            }
            System.out.println("Reservas guardadas desde el fichero CSV.");
        } catch (IOException e) {
            System.out.println("Error al guardar las reservas: " + e.getMessage());
        }
    }


    // Método para buscar jugador por ID (a implementar en GestorReservas)
    public Jugador buscarJugadorPorId(int idUsuario) {
        return jugadores.values().stream()
                .filter(jugador -> jugador.getIdJugador() == idUsuario)
                .findFirst()
                .orElse(null);
    }


    // Método para registrar un jugador
    public void registrarJugador(Jugador jugador) {
        jugadores.put(jugador.getCorreoElectronico(), jugador);  // Almacena al jugador en el mapa usando su correo
    }

    // Método para registrar una pista
    public void registrarPista(Pista pista) {
        pistas.put(pista.getIdPista(), pista);  // Almacena la pista en el mapa usando su ID
    }

 // 1. Hacer una reserva individual
    public void hacerReservaIndividual(Jugador jugador, Date fechaHora, int duracionMinutos, Pista pista, int numeroAdultos, int numeroNinos) {
        // Verificar que la pista es acorde al tipo de reserva (infantil, familiar o adultos)
        String tipoReserva = determinarTipoReserva(numeroAdultos, numeroNinos);
        if (!cumpleCondicionesTipoReserva(pista, tipoReserva)) {
            System.out.println("Error: La pista seleccionada no es válida para el tipo de reserva '" + tipoReserva + "'.");
            return;
        }

        // Verificar antigüedad para aplicar descuento
        boolean tieneAntiguedad = jugador.calcularAntiguedad() > 2;

        // Crear la reserva individual
        Reserva reserva = ReservaGeneralFactory.crearReserva(
                tipoReserva,  // Tipo de usuario: infantil, familiar o adulto
                jugador.getIdJugador(),  // ID del jugador
                fechaHora,  // Fecha y hora de la reserva
                duracionMinutos,  // Duración en minutos
                pista.getIdPista(),  // ID de la pista
                numeroAdultos,  // Número de adultos
                numeroNinos,  // Número de niños
                tieneAntiguedad,  // Descuento por antigüedad
                null,  // No hay bono para reservas individuales
                0  // No hay número de sesión para reservas individuales
        );

        this.reservas.add(reserva);
        System.out.println("Reserva individual creada: " + reserva);
    }

    // 2. Hacer una reserva dentro de un bono
    public void hacerReservaBono(Jugador jugador, Date fechaHora, int duracionMinutos, Pista pista, int numeroAdultos, int numeroNinos, Bono bono, int numeroSesion) {
        // Verificar que la pista es acorde al tipo de reserva (infantil, familiar o adultos)
        String tipoReserva = determinarTipoReserva(numeroAdultos, numeroNinos);
        if (!cumpleCondicionesTipoReserva(pista, tipoReserva)) {
            System.out.println("Error: La pista seleccionada no es válida para el tipo de reserva '" + tipoReserva + "'.");
            return;
        }

        // Crear la reserva de bono
        Reserva reserva = ReservaGeneralFactory.crearReserva(
                "bono",  // Tipo de reserva: bono
                jugador.getIdJugador(),  // ID del jugador
                fechaHora,  // Fecha y hora de la reserva
                duracionMinutos,  // Duración en minutos
                pista.getIdPista(),  // ID de la pista
                numeroAdultos,  // Número de adultos
                numeroNinos,  // Número de niños
                false,  // No hay descuento por antigüedad en reservas con bono
                bono,  // Bono asociado a la reserva
                numeroSesion  // Número de sesión dentro del bono
        );

        this.reservas.add(reserva);
        System.out.println("Reserva de bono creada: " + reserva);
    }

    // 3. Modificar una reserva
    public void modificarReserva(Jugador jugador, Pista pista, Date fechaHora, Date nuevaFechaHora, int nuevaDuracionMinutos, int numeroAdultos, int numeroNinos) {
        Reserva reserva = encontrarReserva(jugador.getIdJugador(), pista.getIdPista(), fechaHora);

        if (reserva == null) {
            System.out.println("Reserva no encontrada.");
            return;
        }

        if (!puedeModificarseOCancelarse(reserva)) {
            System.out.println("No se puede modificar la reserva, ya está dentro de las 24h antes de la hora de inicio.");
            return;
        }

        // Verificar que la pista sigue siendo válida según el tipo de reserva
        String tipoReserva = determinarTipoReserva(numeroAdultos, numeroNinos);
        if (!cumpleCondicionesTipoReserva(pista, tipoReserva)) {
            System.out.println("Error: La pista seleccionada no es válida para el tipo de reserva '" + tipoReserva + "'.");
            return;
        }

        reserva.setFechaHora(nuevaFechaHora);
        reserva.setDuracionMinutos(nuevaDuracionMinutos);
        reserva.setIdPista(pista.getIdPista());
        System.out.println("Reserva modificada: " + reserva);
    }

    // 4. Cancelar una reserva
    public void cancelarReserva(Jugador jugador, Pista pista, Date fechaHora) {
        Reserva reserva = encontrarReserva(jugador.getIdJugador(), pista.getIdPista(), fechaHora);

        if (reserva == null) {
            System.out.println("Reserva no encontrada.");
            return;
        }

        if (!puedeModificarseOCancelarse(reserva)) {
            System.out.println("No se puede cancelar la reserva, ya está dentro de las 24h antes de la hora de inicio.");
            return;
        }

        this.reservas.remove(reserva);
        System.out.println("Reserva cancelada: " + reserva);
    }

    // 5. Consultar reservas futuras
    public List<Reserva> consultarReservasFuturas() {
        Date fechaActual = new Date();
        return this.reservas.stream()
                .filter(reserva -> reserva.getFechaHora().after(fechaActual))
                .collect(Collectors.toList());
    }

    // 6. Consultar reservas para un día y una pista concretos
    public List<Reserva> consultarReservasPorDiaYPista(Date dia, int idPista) {
        return this.reservas.stream()
                .filter(reserva -> esMismaFecha(reserva.getFechaHora(), dia) && reserva.getIdPista() == idPista)
                .collect(Collectors.toList());
    }

    // Método para encontrar una reserva por ID de usuario, ID de pista y fecha de la reserva
    public Reserva encontrarReserva(int idUsuario, int idPista, Date fechaHora) {
        for (Reserva reserva : reservas) {
            if (reserva.getIdUsuario() == idUsuario && reserva.getIdPista() == idPista && esMismaFecha(reserva.getFechaHora(), fechaHora)) {
                return reserva;
            }
        }
        return null;
    }

    // Verificar si una pista cumple con las condiciones del tipo de reserva
    private boolean cumpleCondicionesTipoReserva(Pista pista, String tipoReserva) {
        switch (tipoReserva.toLowerCase()) {
            case "infantil":
                return pista.getPista() == TamanoPista.MINIBASKET;
            case "familiar":
                return pista.getPista() == TamanoPista.MINIBASKET || pista.getPista() == TamanoPista._3VS3;
            case "adulto":
                return pista.getPista() == TamanoPista.ADULTOS;
            default:
                return false;
        }
    }

    // Método para determinar el tipo de reserva basado en el número de adultos y niños
    private String determinarTipoReserva(int numeroAdultos, int numeroNinos) {
        if (numeroNinos > 0 && numeroAdultos == 0) {
            return "infantil";
        } else if (numeroAdultos > 0 && numeroNinos > 0) {
            return "familiar";
        } else if (numeroAdultos > 0 && numeroNinos == 0) {
            return "adulto";
        }
        throw new IllegalArgumentException("Combinación de adultos y niños no válida.");
    }

    // Métodos auxiliares de búsqueda
    public Jugador buscarJugadorPorCorreo(String correoElectronico) {
        return jugadores.get(correoElectronico);
    }

    public List<Pista> listarPistasDisponibles() {
        return pistas.values().stream()
                .filter(Pista::isDisponible)
                .collect(Collectors.toList());
    }

    // Métodos auxiliares para gestionar fechas
    private boolean puedeModificarseOCancelarse(Reserva reserva) {
        long MILISEGUNDOS_EN_24_HORAS = 24 * 60 * 60 * 1000;
        long diferenciaTiempo = reserva.getFechaHora().getTime() - new Date().getTime();
        return diferenciaTiempo > MILISEGUNDOS_EN_24_HORAS;
    }

    private boolean esMismaFecha(Date fecha1, Date fecha2) {
        LocalDate localDate1 = convertirADia(fecha1);
        LocalDate localDate2 = convertirADia(fecha2);
        return localDate1.equals(localDate2);
    }

    private LocalDate convertirADia(Date fecha) {
        return fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
