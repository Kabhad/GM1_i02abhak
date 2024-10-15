package es.uco.pw.gestores.reservas;

import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import es.uco.pw.classes.reserva.*;

public class GestorReservas {
    private List<Reserva> reservas;
    private static GestorReservas instancia;  // Instancia única (Singleton)

    // Constructor privado para evitar instanciación directa
    private GestorReservas() {
        this.reservas = new ArrayList<>();
    }

    // Método estático para obtener la única instancia del gestor
    public static synchronized GestorReservas getInstance() {
        if (instancia == null) {
            instancia = new GestorReservas();
        }
        return instancia;
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

    // 1. Hacer una reserva individual
    public void hacerReservaIndividual(String tipoUsuario, int idUsuario, Date fechaHora, int duracionMinutos, int idPista, int numeroAdultos, int numeroNinos, boolean tieneAntiguedad) {
        Reserva reserva = ReservaGeneralFactory.crearReserva("individual", tipoUsuario, idUsuario, fechaHora, duracionMinutos, idPista, numeroAdultos, numeroNinos, tieneAntiguedad, null, 0);
        this.reservas.add(reserva);
        System.out.println("Reserva individual creada: " + reserva);
    }

    // 2. Hacer una reserva dentro de un bono
    public void hacerReservaBono(String tipoUsuario, int idUsuario, Date fechaHora, int duracionMinutos, int idPista, int numeroAdultos, int numeroNinos, Bono bono, int numeroSesion) {
        Reserva reserva = ReservaGeneralFactory.crearReserva("bono", tipoUsuario, idUsuario, fechaHora, duracionMinutos, idPista, numeroAdultos, numeroNinos, false, bono, numeroSesion);
        this.reservas.add(reserva);
        System.out.println("Reserva de bono creada: " + reserva);
    }

    // 3. Modificar una reserva (si se realiza antes de las 24h de su inicio)
    public void modificarReserva(int idUsuario, int idPista, Date fechaHora, Date nuevaFechaHora, int nuevaDuracionMinutos) {
        Reserva reserva = encontrarReserva(idUsuario, idPista, fechaHora);

        if (reserva == null) {
            System.out.println("Reserva no encontrada.");
            return;
        }

        if (!puedeModificarseOCancelarse(reserva)) {
            System.out.println("No se puede modificar la reserva, ya está dentro de las 24h antes de la hora de inicio.");
            return;
        }

        reserva.setFechaHora(nuevaFechaHora);
        reserva.setDuracionMinutos(nuevaDuracionMinutos);
        reserva.setIdPista(idPista);
        System.out.println("Reserva modificada: " + reserva);
    }

    // 4. Cancelar una reserva (si se realiza antes de las 24h de su inicio)
    public void cancelarReserva(int idUsuario, int idPista, Date fechaHora) {
        Reserva reserva = encontrarReserva(idUsuario, idPista, fechaHora);

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

    // 5. Consultar el número de reservas futuras
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

    // Métodos auxiliares
    // Verificar si una reserva puede modificarse o cancelarse
    private boolean puedeModificarseOCancelarse(Reserva reserva) {
        long MILISEGUNDOS_EN_24_HORAS = 24 * 60 * 60 * 1000;
        long diferenciaTiempo = reserva.getFechaHora().getTime() - new Date().getTime();
        return diferenciaTiempo > MILISEGUNDOS_EN_24_HORAS;
    }

    // Comprobar si dos fechas corresponden al mismo día usando LocalDate
    private boolean esMismaFecha(Date fecha1, Date fecha2) {
        LocalDate localDate1 = convertirADia(fecha1);
        LocalDate localDate2 = convertirADia(fecha2);
        return localDate1.equals(localDate2);
    }

    // Convertir una instancia de Date a LocalDate
    private LocalDate convertirADia(Date fecha) {
        return fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
