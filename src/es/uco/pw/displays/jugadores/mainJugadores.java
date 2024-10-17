package es.uco.pw.displays.jugadores;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import es.uco.pw.classes.jugador.Jugador;
import es.uco.pw.gestores.jugadores.GestorJugadores;

public class mainJugadores {
    public static void imprimirMenu() {
        System.out.println("=====================================");
        System.out.println("      Bienvenido al Menú de Usuarios");
        System.out.println("=====================================");
        System.out.println("1. Alta de Usuario");
        System.out.println("2. Modificar Usuario");
        System.out.println("3. Listar Usuarios");
        System.out.println("0. Volver al menú principal");
        System.out.println("=====================================");
        System.out.print("Seleccione una opción: ");
    }

    public static void main(Scanner sc) {
        // Instancia del gestor de usuarios utilizando el patrón Singleton
        GestorJugadores gestor = GestorJugadores.getInstance(); // Obtener la instancia única
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int opcion;

        do {
            imprimirMenu(); // Mostrar el menú de Usuarios
            opcion = sc.nextInt(); // Leer la opción del Usuario
            sc.nextLine(); // Limpiar el buffer después de leer enteros

            switch (opcion) {
                // Proceso de alta de jugador
                case 1:
                    System.out.println("Iniciando proceso de alta de jugador...");
                    try {
                        System.out.print("Ingrese nombre y apellidos: ");
                        String nombreApellidos = sc.nextLine();

                        System.out.print("Ingrese fecha de nacimiento (yyyy-MM-dd): ");
                        String fechaNacimientoStr = sc.nextLine();
                        Date fechaNacimiento = dateFormat.parse(fechaNacimientoStr);

                        System.out.print("Ingrese el correo electrónico: ");
                        String correo = sc.nextLine();

                        // Crear un nuevo objeto Jugador con los datos introducidos
                        Jugador nuevoJugador = new Jugador();
                        nuevoJugador.setNombreApellidos(nombreApellidos);
                        nuevoJugador.setFechaNacimiento(fechaNacimiento);
                        nuevoJugador.setCorreoElectronico(correo);

                        // Llamar al método altaJugador y verificar el resultado
                        boolean registroExitoso = gestor.altaJugador(nuevoJugador);

                        if (registroExitoso) {
                            System.out.println("Jugador registrado con éxito.");
                        } else {
                            System.out.println("Error: el correo ya está registrado.");
                        }

                    } catch (ParseException e) {
                        System.out.println("Formato de fecha incorrecto. Intente nuevamente.");
                    }
                    break;

                // Modificar usuario
                case 2:
                    System.out.println("Iniciando proceso de modificación de jugador...");

                    System.out.print("Ingrese el correo electrónico del jugador a modificar: ");
                    String correoModificar = sc.nextLine();

                    System.out.print("Ingrese el nuevo nombre y apellidos: ");
                    String nuevoNombre = sc.nextLine();

                    // Definir el formato para la fecha
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    System.out.print("Ingrese la nueva fecha de nacimiento (yyyy-MM-dd): ");
                    String nuevaFechaNacimientoStr = sc.nextLine();
                    java.util.Date nuevaFechaNacimiento = null;
                    try {
                        nuevaFechaNacimiento = sdf.parse(nuevaFechaNacimientoStr);
                    } catch (Exception e) {
                        System.out.println("Formato de fecha incorrecto.");
                        break; // Salir si hay error en la fecha
                    }

                    System.out.print("Ingrese el nuevo correo electrónico: ");
                    String nuevoCorreo = sc.nextLine();

                    int resultadoModificacion = gestor.modificarJugador(correoModificar, nuevoNombre, nuevaFechaNacimiento, nuevoCorreo);

                    switch (resultadoModificacion) {
                        case 1:
                            System.out.println("Modificación realizada con éxito.");
                            break;
                        case 0:
                            System.out.println("Error: El correo electrónico ya está registrado.");
                            break;
                        case -1:
                            System.out.println("Error: No se encontró el jugador.");
                            break;
                    }

                    break;

                // Listar usuarios registrados
                case 3:
                    int vacia = gestor.listaVacia();
                    if (vacia == 1) {
                        System.out.println("La lista de jugadores está vacía.");
                    } else {
                        System.out.println("Listando jugadores: ");
                        System.out.println(gestor.listarJugadores()); // Imprime la lista de jugadores
                    }
                    break;

                // Salir al menú principal
                case 0:
                    // Volver al menú principal
                    System.out.println("Volviendo al menú principal...");
                    break;


                default:
                    System.out.println("Opción no válida. Por favor, intente nuevamente.");
                    break;
            }
        } while (opcion != 0);
    }
}
