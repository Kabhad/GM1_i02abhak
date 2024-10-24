package es.uco.pw.gestores.reservas;

import es.uco.pw.classes.jugador.Jugador;
import es.uco.pw.classes.pista.Pista;
import es.uco.pw.classes.reserva.*;
import es.uco.pw.gestores.jugadores.*;
import es.uco.pw.gestores.pistas.*;

import es.uco.pw.classes.pista.TamanoPista;
import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
            throw new RuntimeException("Error al leer el fichero de propiedades: " + e.getMessage());
        }
    }
    
    // Método para cargar las reservas desde un fichero CSV
    public void cargarReservasDesdeFichero() throws IOException, ParseException {
        try (BufferedReader br = new BufferedReader(new FileReader(ficheroReservasPath))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");

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
                    reserva = ReservaGeneralFactory.crearReserva(
                        tipoUsuario, 
                        idUsuario, 
                        fechaHora, 
                        duracionMinutos, 
                        idPista, 
                        numeroAdultos, 
                        numeroNinos, 
                        false, 
                        null, 
                        0
                    );
                } else if (tipoReserva.equalsIgnoreCase("bono")) {
                    int idBono = Integer.parseInt(datos[8]);
                    int numeroSesion = Integer.parseInt(datos[9]);

                    Bono bono = new Bono(idBono, idUsuario, numeroSesion, fechaHora);
                    reserva = ReservaGeneralFactory.crearReserva(
                        tipoUsuario, 
                        idUsuario, 
                        fechaHora, 
                        duracionMinutos, 
                        idPista, 
                        numeroAdultos, 
                        numeroNinos, 
                        false, 
                        bono, 
                        numeroSesion
                    );
                }

                if (reserva != null) {
                    reservas.add(reserva);
                }
            }
        } 
    }

    // Método para guardar las reservas en un fichero CSV
    public void guardarReservasEnFichero() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroReservasPath))) {
            for (Reserva reserva : reservas) {
                StringBuilder sb = new StringBuilder();

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

                    sb.append("bono;").append(tipoUsuario).append(";")
                        .append(reservaBono.getIdUsuario()).append(";")
                        .append(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(reservaBono.getFechaHora())).append(";")
                        .append(reservaBono.getDuracionMinutos()).append(";")
                        .append(reservaBono.getIdPista()).append(";")
                        .append(numeroAdultos).append(";").append(numeroNinos).append(";")
                        .append(reservaBono.getBono().getIdBono()).append(";")
                        .append(reservaBono.getNumeroSesion()).append(";");

                } else if (reserva instanceof ReservaIndividual) {
                    ReservaIndividual reservaIndividual = (ReservaIndividual) reserva;
                    Reserva reservaEspecifica = reservaIndividual.getReservaEspecifica();
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

                    sb.append("individual;").append(tipoUsuario).append(";")
                        .append(reservaIndividual.getIdUsuario()).append(";")
                        .append(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(reservaIndividual.getFechaHora())).append(";")
                        .append(reservaIndividual.getDuracionMinutos()).append(";")
                        .append(reservaIndividual.getIdPista()).append(";")
                        .append(numeroAdultos).append(";").append(numeroNinos).append(";");
                }

                bw.write(sb.toString());
                bw.newLine();
            }
        } 
    }

    public static Jugador buscarJugadorPorId(int idJugador) {
        return GestorJugadores.getInstance().buscarJugadorPorId(idJugador);
    }

    public void registrarJugador(Jugador jugador) {
        jugadores.put(jugador.getCorreoElectronico(), jugador);
    }

    public void registrarPista(Pista pista) {
        pistas.put(pista.getIdPista(), pista);
    }

    public void hacerReservaIndividual(Jugador jugador, Date fechaHora, int duracionMinutos, Pista pista, int numeroAdultos, int numeroNinos) {
        String tipoReserva = determinarTipoReserva(numeroAdultos, numeroNinos);
        if (!cumpleCondicionesTipoReserva(pista, tipoReserva)) {
            throw new IllegalArgumentException("La pista seleccionada no es válida para el tipo de reserva '" + tipoReserva + "'.");
        }

        boolean tieneAntiguedad = jugador.calcularAntiguedad() > 2;

        Reserva reserva = ReservaGeneralFactory.crearReserva(
                tipoReserva,  
                jugador.getIdJugador(),  
                fechaHora,  
                duracionMinutos,  
                pista.getIdPista(),  
                numeroAdultos,  
                numeroNinos,  
                tieneAntiguedad,  
                null,  
                0 
        );

        this.reservas.add(reserva);
    }

    public void hacerReservaBono(Jugador jugador, Date fechaHora, int duracionMinutos, Pista pista, int numeroAdultos, int numeroNinos, Bono bono, int numeroSesion) {
        String tipoReserva = determinarTipoReserva(numeroAdultos, numeroNinos);

        if (!cumpleCondicionesTipoReserva(pista, tipoReserva)) {
            throw new IllegalArgumentException("La pista seleccionada no es válida para el tipo de reserva '" + tipoReserva + "'.");
        }

        Reserva reserva = ReservaGeneralFactory.crearReserva(
            tipoReserva,  
            jugador.getIdJugador(),
            fechaHora,
            duracionMinutos,
            pista.getIdPista(),
            numeroAdultos,
            numeroNinos,
            false,  
            bono,
            numeroSesion
        );

        this.reservas.add(reserva);
    }

    public void modificarReserva(Reserva reserva, Pista pista, Date fechaHoraOriginal, Date nuevaFechaHora, int nuevaDuracionMinutos, int numeroAdultos, int numeroNinos, Bono bono, int numeroSesion) {
        GestorJugadores gestorJugadores = GestorJugadores.getInstance();
        GestorPistas gestorPistas = GestorPistas.getInstance();

        Jugador jugador = gestorJugadores.buscarJugadorPorId(reserva.getIdUsuario());
        Pista pistaOriginal = gestorPistas.buscarPistaPorId(reserva.getIdPista());

        if (jugador == null || pistaOriginal == null) {
            throw new IllegalArgumentException("No se pudo encontrar el jugador o la pista asociados a la reserva.");
        }

        if (!puedeModificarseOCancelarse(reserva)) {
            throw new IllegalArgumentException("No se puede modificar la reserva, ya está dentro de las 24h antes de la hora de inicio.");
        }

        String tipoReserva = determinarTipoReserva(numeroAdultos, numeroNinos);
        if (!cumpleCondicionesTipoReserva(pista, tipoReserva)) {
            throw new IllegalArgumentException("La pista seleccionada no es válida para el tipo de reserva '" + tipoReserva + "'.");
        }

        cancelarReserva(jugador, pistaOriginal, fechaHoraOriginal);

        Reserva nuevaReserva;

        if (bono == null) {  
            nuevaReserva = ReservaGeneralFactory.crearReserva(
                tipoReserva,  
                jugador.getIdJugador(),  
                nuevaFechaHora,  
                nuevaDuracionMinutos,  
                pista.getIdPista(),  
                numeroAdultos,  
                numeroNinos,  
                jugador.calcularAntiguedad() > 2,  
                null,  
                0  
            );
        } else {  
            nuevaReserva = ReservaGeneralFactory.crearReserva(
                tipoReserva,  
                jugador.getIdJugador(),  
                nuevaFechaHora,  
                nuevaDuracionMinutos,  
                pista.getIdPista(),  
                numeroAdultos,  
                numeroNinos,  
                false,  
                bono,  
                numeroSesion  
            );
        }

        this.reservas.add(nuevaReserva);
    }

    public void cancelarReserva(Jugador jugador, Pista pista, Date fechaHora) {
        Reserva reserva = encontrarReserva(jugador.getIdJugador(), pista.getIdPista(), fechaHora);

        if (reserva == null) {
            throw new IllegalArgumentException("Reserva no encontrada.");
        }

        if (!puedeModificarseOCancelarse(reserva)) {
            throw new IllegalArgumentException("No se puede cancelar la reserva, ya está dentro de las 24h antes de la hora de inicio.");
        }

        this.reservas.remove(reserva);
    }

    public List<Reserva> consultarReservasFuturas() {
        Date fechaActual = new Date();
        return this.reservas.stream()
                .filter(reserva -> reserva.getFechaHora().after(fechaActual))
                .collect(Collectors.toList());
    }

    public List<Reserva> consultarReservasPorDiaYPista(Date dia, int idPista) {
        return this.reservas.stream()
                .filter(reserva -> esMismaFechaSinHora(reserva.getFechaHora(), dia) && reserva.getIdPista() == idPista)
                .collect(Collectors.toList());
    }

    public Reserva encontrarReserva(int idUsuario, int idPista, Date fechaHora) {
        for (Reserva reserva : reservas) {
            if (reserva.getIdUsuario() == idUsuario && 
                reserva.getIdPista() == idPista && 
                esMismaFecha(reserva.getFechaHora(), fechaHora)) {
                return reserva;
            }
        }
        return null;
    }

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

    private String determinarTipoReserva(int numeroAdultos, int numeroNinos) {
        if (numeroAdultos > 0 && numeroNinos > 0) {
            return "familiar";
        } else if (numeroAdultos > 0) {
            return "adulto";
        } else if (numeroNinos > 0) {
            return "infantil";
        } else {
            throw new IllegalArgumentException("No se ha proporcionado un número válido de adultos o niños.");
        }
    }

    public Jugador buscarJugadorPorCorreo(String correoElectronico) {
        return GestorJugadores.getInstance().buscarJugadorPorCorreo(correoElectronico);
    }

    public List<Pista> listarPistasDisponibles() {
        return GestorPistas.getInstance().buscarPistasDisponibles();
    }

    public static Pista buscarPistaPorId(int idPista) {
        return GestorPistas.getInstance().buscarPistaPorId(idPista);
    }

    private boolean puedeModificarseOCancelarse(Reserva reserva) {
        long MILISEGUNDOS_EN_24_HORAS = 24 * 60 * 60 * 1000;
        long diferenciaTiempo = reserva.getFechaHora().getTime() - new Date().getTime();
        return diferenciaTiempo > MILISEGUNDOS_EN_24_HORAS;
    }

    private boolean esMismaFechaSinHora(Date fecha1, Date fecha2) {
        LocalDate localDate1 = convertirADia(fecha1);  
        LocalDate localDate2 = convertirADia(fecha2);
        return localDate1.equals(localDate2);  
    }

    private LocalDate convertirADia(Date fecha) {
        return fecha.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
    }

    private boolean esMismaFecha(Date fecha1, Date fecha2) {
        LocalDateTime localDateTime1 = convertirAFechaYHora(fecha1);
        LocalDateTime localDateTime2 = convertirAFechaYHora(fecha2);
        return localDateTime1.equals(localDateTime2);
    }

    private LocalDateTime convertirAFechaYHora(Date fecha) {
        return fecha.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
    }
}
