package es.uco.pw.displays.principal;

import java.util.Scanner;

import es.uco.pw.displays.jugadores.mainJugadores;
import es.uco.pw.displays.pistas.mainPistas;
import es.uco.pw.displays.reservas.mainReservas;

public class mainPrincipal {

    public static void imprimirMenu() {
        System.out.println("=====================================");
        System.out.println("      Bienvenido al Sistema de Gestión");
        System.out.println("=====================================");
        System.out.println("1. Menú de Pistas");
        System.out.println("2. Menú de Reservas");
        System.out.println("3. Menú de Usuarios");
        System.out.println("4. Salir del programa");
        System.out.println("=====================================");
        System.out.print("Seleccione una opción: ");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);  // Crear una sola instancia de Scanner
        int opcion;
        boolean continuar = true;

        while (continuar) {
            imprimirMenu(); // Mostrar el menú principal
            opcion = sc.nextInt(); // Leer la opción del usuario
            sc.nextLine(); // Limpiar el buffer después de leer enteros

            switch (opcion) {
                case 1:
                    System.out.println("Accediendo al Menú de Pistas...");
                    mainPistas.main(sc); // Pasar el Scanner al menú de Pistas
                    break;
                case 2:
                    System.out.println("Accediendo al Menú de Reservas...");
                    mainReservas.main(sc); // Pasar el Scanner al menú de Reservas
                    break;
                case 3:
                    System.out.println("Accediendo al Menú de Usuarios...");
                    mainJugadores.main(sc); // Pasar el Scanner al menú de Usuarios
                    break;
                case 4:
                    System.out.println("Saliendo del programa. ¡Hasta pronto!");
                    continuar = false; // Romper el bucle para salir
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente nuevamente.");
            }
        }

        sc.close(); // Cerrar el Scanner al final
    }
}
