package Gestores;

import Clases.abstractReserva;
import Clases.ReservaFactory;
import Clases.ReservaBono;
import Clases.ReservaInfantil;
import Clases.ReservaAdulto;
import Clases.ReservaFamiliar;
import Clases.ReservaIndividual;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GestorReservas {
    private List<abstractReserva> reservas;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public GestorReservas() {
        this.reservas = new ArrayList<>();
    }

    // Método para hacer una reserva individual
    public boolean hacerReservaIndividual(String tipo, int idUsuario, Date fechaHora, int duracionMinutos, int idPista, int numeroParticipantes) {
        if (!esUsuarioRegistrado(idUsuario) || !cumpleCondicionesReservaIndividual(tipo, idPista, numeroParticipantes)) {
            return false; // Validar que el usuario esté registrado y que la pista cumpla con las condiciones
        }

        float precio = calcularPrecio(duracionMinutos);
        float descuento = calcularDescuento(idUsuario);

        abstractReserva nuevaReserva = ReservaFactory.crearReservaIndividual(tipo, idUsuario, fechaHora, duracionMinutos, idPista, precio, descuento, numeroParticipantes);
        if (nuevaReserva != null && validarReserva(nuevaReserva)) {
            reservas.add(nuevaReserva);
            return true;
        }
        return false;
    }

 // Método para hacer una reserva de bono
    public boolean hacerReservaBono(String tipo, int idUsuario, Date fechaHora, int duracionMinutos, int idPista, int idBono, int numeroSesion) {
        if (!esUsuarioRegistrado(idUsuario)) {
            return false; // Validar que el usuario esté registrado
        }

        // Verificar si el bono ya tiene 5 sesiones
        long sesionesExistentes = reservas.stream()
            .filter(reserva -> reserva instanceof ReservaBono)
            .map(reserva -> (ReservaBono) reserva)
            .filter(bono -> bono.getIdBono() == idBono)
            .count();

        if (sesionesExistentes >= 5) {
            System.out.println("El bono ya ha alcanzado el máximo de 5 sesiones.");
            return false;
        }

        // Verificar la caducidad del bono (1 año desde la primera reserva)
        Date primeraReserva = reservas.stream()
            .filter(reserva -> reserva instanceof ReservaBono)
            .map(reserva -> (ReservaBono) reserva)
            .filter(bono -> bono.getIdBono() == idBono)
            .map(ReservaBono::getFechaHora)
            .min(Date::compareTo)
            .orElse(fechaHora); // Si no hay reservas, usar la fecha actual

        Date fechaLimite = new Date(primeraReserva.getTime() + 365L * 24 * 60 * 60 * 1000); // 1 año después de la primera reserva
        if (fechaHora.after(fechaLimite)) {
            System.out.println("La fecha de la reserva está fuera del periodo de validez del bono.");
            return false;
        }

        float precio = calcularPrecio(duracionMinutos) * 0.95f; // 5% de descuento para reservas de bono
        float descuento = 0; // No se aplica descuento adicional por antigüedad

        abstractReserva nuevaReserva = ReservaFactory.crearReservaBono(tipo, idUsuario, fechaHora, duracionMinutos, idPista, precio, descuento, idBono, numeroSesion);
        if (nuevaReserva != null && validarReserva(nuevaReserva)) {
            reservas.add(nuevaReserva);
            return true;
        }
        return false;
    }


    // Método para calcular el precio en función de la duración
    private float calcularPrecio(int duracionMinutos) {
        switch (duracionMinutos) {
            case 60:
                return 20.0f;
            case 90:
                return 30.0f;
            case 120:
                return 40.0f;
            default:
                throw new IllegalArgumentException("Duración de la reserva no válida");
        }
    }

    // Método para calcular el descuento basado en la antigüedad del usuario
    private float calcularDescuento(int idUsuario) {
        //Date fechaInscripcion = obtenerFechaInscripcion(idUsuario);
    	Date fechaInscripcion = null;
        if (fechaInscripcion == null) {
            return 0.0f;
        }
        long añosDeAntiguedad = (new Date().getTime() - fechaInscripcion.getTime()) / (1000L * 60 * 60 * 24 * 365);
        return añosDeAntiguedad > 2 ? 0.10f : 0.0f;
    }

    // Método que verifica si el usuario está registrado
    private boolean esUsuarioRegistrado(int idUsuario) {
        // Aquí deberíamos verificar en una lista de usuarios registrados
        // Implementación simulada
        return true; // Suponer que el usuario está registrado
    }

    // Método que valida si la pista cumple con las condiciones para la reserva individual
    private boolean cumpleCondicionesReservaIndividual(String tipo, int idPista, int numeroParticipantes) {
        // Validar que el tipo de pista y el número de participantes sean adecuados
        // Implementación simulada
        return true; // Simulando que cumple las condiciones
    }

    // Método para validar una reserva
    private boolean validarReserva(abstractReserva reserva) {
        // Validar si la reserva puede ser realizada (por ejemplo, si la pista está disponible)
        // Implementación simulada
        return true;
    }

    // Método para consultar las reservas futuras
    public List<abstractReserva> consultarReservasFuturas() {
        Date fechaActual = new Date();
        List<abstractReserva> reservasFuturas = new ArrayList<>();
        for (abstractReserva reserva : reservas) {
            if (reserva.getFechaHora().after(fechaActual)) {
                reservasFuturas.add(reserva);
            }
        }
        return reservasFuturas;
    }

    // Método para consultar reservas por fecha y pista
    public List<abstractReserva> consultarReservasPorFechaYPista(Date fecha, int idPista) {
        List<abstractReserva> reservasFiltradas = new ArrayList<>();
        for (abstractReserva reserva : reservas) {
            if (reserva.getFechaHora().equals(fecha) && reserva.getIdPista() == idPista) {
                reservasFiltradas.add(reserva);
            }
        }
        return reservasFiltradas;
    }

    // Método para cargar reservas desde un archivo (reservas.txt)
    public void cargarReservas(String rutaFichero) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaFichero))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 8) {
                    String tipoReserva = partes[0];
                    int idUsuario = Integer.parseInt(partes[1]);
                    Date fechaHora = dateFormat.parse(partes[2]);
                    int duracionMinutos = Integer.parseInt(partes[3]);
                    int idPista = Integer.parseInt(partes[4]);
                    float precio = Float.parseFloat(partes[5]);
                    float descuento = Float.parseFloat(partes[6]);
                    int numeroParticipantes = Integer.parseInt(partes[7]);

                    // Verificar si es una reserva de bono
                    int idBono = partes.length > 8 ? Integer.parseInt(partes[8]) : -1;
                    int numeroSesion = partes.length > 9 ? Integer.parseInt(partes[9]) : -1;

                    abstractReserva reserva;
                    if (idBono != -1 && numeroSesion != -1) {
                        // Crear reserva de bono
                        reserva = ReservaFactory.crearReservaBono(tipoReserva, idUsuario, fechaHora, duracionMinutos, idPista, precio, descuento, idBono, numeroSesion);
                    } else {
                        // Crear reserva individual
                        reserva = ReservaFactory.crearReservaIndividual(tipoReserva, idUsuario, fechaHora, duracionMinutos, idPista, precio, descuento, numeroParticipantes);
                    }

                    if (reserva != null) {
                        reservas.add(reserva);
                    }
                }
            }
        } catch (IOException | ParseException e) {
            System.err.println("Error al cargar las reservas: " + e.getMessage());
        }
    }

 // Método para guardar reservas en un archivo (reservas.txt)
    public void guardarReservas(String rutaFichero) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaFichero))) {
            for (abstractReserva reserva : reservas) {
                StringBuilder sb = new StringBuilder();
                sb.append(reserva.getClass().getSimpleName()).append(",");
                sb.append(reserva.getIdUsuario()).append(",");
                sb.append(dateFormat.format(reserva.getFechaHora())).append(",");
                sb.append(reserva.getDuracionMinutos()).append(",");
                sb.append(reserva.getIdPista()).append(",");
                sb.append(reserva.getPrecio()).append(",");
                sb.append(reserva.getDescuento()).append(",");

                // Verificar el tipo de reserva y agregar la información específica
                if (reserva instanceof ReservaInfantil) {
                    ReservaInfantil infantil = (ReservaInfantil) reserva;
                    sb.append(infantil.getNumeroNinos());
                } else if (reserva instanceof ReservaFamiliar) {
                    ReservaFamiliar familiar = (ReservaFamiliar) reserva;
                    sb.append(familiar.getNumeroAdultos()).append(",");
                    sb.append(familiar.getNumeroNinos());
                } else if (reserva instanceof ReservaAdulto) {
                    ReservaAdulto adulto = (ReservaAdulto) reserva;
                    sb.append(adulto.getNumeroAdultos());
                } else if (reserva instanceof ReservaBono) {
                    ReservaBono bono = (ReservaBono) reserva;
                    sb.append(bono.getIdBono()).append(",");
                    sb.append(bono.getNumeroSesion()).append(",");
                    sb.append(bono.getNumeroAdultos()).append(",");
                    sb.append(bono.getNumeroNinos());
                } else if (reserva instanceof ReservaIndividual) {
                    // No hay atributos adicionales para ReservaIndividual, solo guardar los comunes
                }

                // Escribir la línea en el archivo
                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar las reservas: " + e.getMessage());
        }
    }


}


