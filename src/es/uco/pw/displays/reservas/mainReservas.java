package es.uco.pw.displays.reservas;

import es.uco.pw.classes.reserva.Bono;
import es.uco.pw.gestores.reservas.GestorReservas;

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
        System.out.println("0. Volver al menú principal");
        System.out.println("=====================================");
        System.out.print("Seleccione una opción: ");
    }

    // El método main ahora acepta un Scanner como parámetro
    public static void main(Scanner sc) {
        GestorReservas gestorReservas = GestorReservas.getInstance();  // Obtener la instancia del gestor (Singleton)
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
                        System.out.print("Ingrese el tipo de reserva (infantil, familiar, adulto): ");
                        String tipo = sc.nextLine();
                        System.out.print("Ingrese ID de usuario: ");
                        int idUsuario = sc.nextInt();
                        sc.nextLine(); // Limpiar buffer
                        System.out.print("Ingrese fecha y hora (yyyy-MM-dd HH:mm): ");
                        Date fechaHora = dateFormat.parse(sc.nextLine());
                        System.out.print("Ingrese duración en minutos (60, 90, 120): ");
                        int duracionMinutos = sc.nextInt();
                        sc.nextLine(); // Limpiar buffer
                        System.out.print("Ingrese ID de la pista: ");
                        int idPista = sc.nextInt();
                        sc.nextLine(); // Limpiar buffer
                        System.out.print("Ingrese número de adultos: ");
                        int numeroAdultos = sc.nextInt();
                        sc.nextLine(); // Limpiar buffer
                        System.out.print("Ingrese número de niños: ");
                        int numeroNinos = sc.nextInt();
                        sc.nextLine(); // Limpiar buffer
                        System.out.print("¿Tiene más de 2 años de antigüedad en el sistema? (true/false): ");
                        boolean tieneAntiguedad = sc.nextBoolean();
                        sc.nextLine(); // Limpiar buffer

                        gestorReservas.hacerReservaIndividual(tipo, idUsuario, fechaHora, duracionMinutos, idPista, numeroAdultos, numeroNinos, tieneAntiguedad);
                    } catch (ParseException e) {
                        System.out.println("Formato de fecha incorrecto.");
                    }
                    break;

                case 2:
                    // Hacer una reserva con bono
                    System.out.println("Iniciando proceso para hacer una reserva con bono...");
                    try {
                        System.out.print("Ingrese el tipo de bono (infantil, familiar, adulto): ");
                        String tipoBono = sc.nextLine();
                        System.out.print("Ingrese ID de usuario: ");
                        int idUsuario = sc.nextInt();
                        sc.nextLine(); // Limpiar buffer
                        System.out.print("Ingrese ID del bono: ");
                        int idBono = sc.nextInt();
                        sc.nextLine(); // Limpiar buffer
                        System.out.print("Ingrese número de la sesión del bono (1 a 5): ");
                        int numeroSesion = sc.nextInt();
                        sc.nextLine(); // Limpiar buffer
                        System.out.print("Ingrese fecha y hora de la reserva (yyyy-MM-dd HH:mm): ");
                        Date fechaHoraBono = dateFormat.parse(sc.nextLine());
                        System.out.print("Ingrese duración en minutos (60, 90, 120): ");
                        int duracionMinutosBono = sc.nextInt();
                        sc.nextLine(); // Limpiar buffer
                        System.out.print("Ingrese ID de la pista: ");
                        int idPistaBono = sc.nextInt();
                        sc.nextLine(); // Limpiar buffer
                        System.out.print("Ingrese número de adultos: ");
                        int numeroAdultosBono = sc.nextInt();
                        sc.nextLine(); // Limpiar buffer
                        System.out.print("Ingrese número de niños: ");
                        int numeroNinosBono = sc.nextInt();
                        sc.nextLine(); // Limpiar buffer

                        Bono bono = new Bono(idBono, idUsuario, fechaHoraBono);  // Crear un bono
                        gestorReservas.hacerReservaBono(tipoBono, idUsuario, fechaHoraBono, duracionMinutosBono, idPistaBono, numeroAdultosBono, numeroNinosBono, bono, numeroSesion);
                    } catch (ParseException e) {
                        System.out.println("Formato de fecha incorrecto.");
                    }
                    break;

                case 0:
                    // Volver al menú principal
                    System.out.println("Volviendo al menú principal...");
                    continuar = false;  // Salir del menú de reservas
                    break;

                default:
                    System.out.println("Opción no válida. Por favor, intente nuevamente.");
            }
        }
    }
}
