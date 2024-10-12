package Displays;

import java.util.Scanner;

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
        Scanner sc = new Scanner(System.in);
        int opcion;
        boolean continuar = true;

        while (continuar) {
            imprimirMenu(); // Mostrar el menú principal
            opcion = sc.nextInt(); // Leer la opción del usuario
            sc.nextLine(); // Limpiar el buffer después de leer enteros

            switch (opcion) {
                case 1:
                    System.out.println("Accediendo al Menú de Pistas...");
                    mainPistas.main(null); // Llamada al menú de Pistas
                    break;
                case 2:
                    System.out.println("Accediendo al Menú de Reservas...");
                    mainReservas.main(null); // Llamada al menú de Reservas
                    break;
                case 3:
                    System.out.println("Accediendo al Menú de Usuarios...");
                    mainUsuarios.main(null); // Llamada al menú de Usuarios
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
