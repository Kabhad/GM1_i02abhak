package Displays;

import Gestores.GestorReservas;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class mainReservas {

    public static void imprimirMenu() {
        System.out.println("=====================================");
        System.out.println("        GESTOR DE RESERVAS");
        System.out.println("=====================================");
        System.out.println("1. Hacer reserva individual");
        System.out.println("2. Hacer reserva con bono");
        System.out.println("3. Modificar reserva");
        System.out.println("4. Cancelar reserva");
        System.out.println("5. Consultar reservas futuras");
        System.out.println("6. Consultar reservas por día y pista");
        System.out.println("7. Volver al menú principal");
        System.out.println("=====================================");
        System.out.print("Seleccione una opción: ");
    }

    public static void main(String[] args) {
        GestorReservas gestorReservas = new GestorReservas();  // Crear instancia del gestor de reservas
        Scanner sc = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        int opcion;
        boolean continuar = true;

        while (continuar) {
            imprimirMenu();  // Mostrar el menú de reservas
            opcion = sc.nextInt();  // Leer la opción del usuario
            sc.nextLine();  // Limpiar el buffer

            switch (opcion) {
                case 1:
                    // Hacer una reserva individual
                    System.out.println("Iniciando proceso para hacer una reserva individual...");
                    try {
                        System.out.print("Ingrese el tipo de reserva: ");
                        String tipo = sc.nextLine();
                        System.out.print("Ingrese ID de usuario: ");
                        int idUsuario = sc.nextInt();
                        System.out.print("Ingrese fecha y hora (yyyy-MM-dd HH:mm): ");
                        Date fechaHora = dateFormat.parse(sc.next() + " " + sc.next());
                        System.out.print("Ingrese duración en minutos (60, 90, 120): ");
                        int duracionMinutos = sc.nextInt();
                        System.out.print("Ingrese ID de la pista: ");
                        int idPista = sc.nextInt();
                        System.out.print("Ingrese número de participantes: ");
                        int numeroParticipantes = sc.nextInt();

                        boolean reservaExitosa = gestorReservas.hacerReservaIndividual(tipo, idUsuario, fechaHora, duracionMinutos, idPista, numeroParticipantes);
                        if (reservaExitosa) {
                            System.out.println("Reserva individual creada con éxito.");
                        } else {
                            System.out.println("Error al crear la reserva.");
                        }
                    } catch (ParseException e) {
                        System.out.println("Formato de fecha incorrecto.");
                    }
                    break;

                case 2:
                    // Hacer una reserva con bono
                    System.out.println("Iniciando proceso para hacer una reserva con bono...");
                    try {
                        System.out.print("Ingrese el tipo de bono: ");
                        String tipoBono = sc.nextLine();
                        System.out.print("Ingrese ID de usuario: ");
                        int idUsuario = sc.nextInt();
                        System.out.print("Ingrese ID del bono: ");
                        int idBono = sc.nextInt();
                        System.out.print("Ingrese número de la sesión del bono (1 a 5): ");
                        int numeroSesion = sc.nextInt();
                        System.out.print("Ingrese fecha y hora de la reserva (yyyy-MM-dd HH:mm): ");
                        Date fechaHoraBono = dateFormat.parse(sc.next() + " " + sc.next());
                        System.out.print("Ingrese duración en minutos (60, 90, 120): ");
                        int duracionMinutosBono = sc.nextInt();
                        System.out.print("Ingrese ID de la pista: ");
                        int idPistaBono = sc.nextInt();

                        boolean reservaBonoExitosa = gestorReservas.hacerReservaBono(tipoBono, idUsuario, fechaHoraBono, duracionMinutosBono, idPistaBono, idBono, numeroSesion);
                        if (reservaBonoExitosa) {
                            System.out.println("Reserva con bono creada con éxito.");
                        } else {
                            System.out.println("Error al crear la reserva con bono.");
                        }
                    } catch (ParseException e) {
                        System.out.println("Formato de fecha incorrecto.");
                    }
                    break;

                case 3:
                    // Modificar reserva (implementación pendiente)
                    System.out.println("Funcionalidad de modificar reserva no implementada aún.");
                    break;

                case 4:
                    // Cancelar reserva (implementación pendiente)
                    System.out.println("Funcionalidad de cancelar reserva no implementada aún.");
                    break;

                case 5:
                    // Consultar reservas futuras
                    System.out.println("Consultando reservas futuras...");
                    gestorReservas.consultarReservasFuturas().forEach(reserva -> {
                        System.out.println(reserva);
                    });
                    break;

                case 6:
                    // Consultar reservas por fecha y pista
                    System.out.println("Consultando reservas por día y pista...");
                    try {
                        System.out.print("Ingrese fecha de la reserva (yyyy-MM-dd): ");
                        Date fechaConsulta = dateFormat.parse(sc.next());
                        System.out.print("Ingrese ID de la pista: ");
                        int idPistaConsulta = sc.nextInt();

                        gestorReservas.consultarReservasPorFechaYPista(fechaConsulta, idPistaConsulta).forEach(reserva -> {
                            System.out.println(reserva);
                        });
                    } catch (ParseException e) {
                        System.out.println("Formato de fecha incorrecto.");
                    }
                    break;

                case 7:
                    // Volver al menú principal
                    System.out.println("Volviendo al menú principal...");
                    continuar = false;
                    break;

                default:
                    System.out.println("Opción no válida. Por favor, intente nuevamente.");
            }
        }

        sc.close();  // Cerrar el Scanner al final
    }
}
