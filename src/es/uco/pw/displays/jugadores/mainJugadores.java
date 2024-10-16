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
        System.out.println("4. Salir del programa");
        System.out.println("=====================================");
        System.out.print("Seleccione una opción: ");
    }

    public static void main(String[] args) {
        // Instancia del gestor de usuarios
        GestorJugadores gestor = new GestorJugadores();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Scanner sc = new Scanner(System.in);
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
	
	                    System.out.print("Ingrese fecha de nacimiento (dd/MM/yyyy): ");
	                    String fechaNacimientoStr = sc.nextLine();
	                    Date fechaNacimiento = dateFormat.parse(fechaNacimientoStr);
	
	                    System.out.print("Ingrese el correo electrónico: ");
	                    String correo = sc.nextLine();
	
	                    // Crear un nuevo objeto Jugador con los datos introducidos
	                    Jugador nuevoJugador = new Jugador();
	                    nuevoJugador.setNombreApellidos(nombreApellidos);
	                    nuevoJugador.setFechaNacimiento(fechaNacimiento);
	                    nuevoJugador.setCorreoElectronico(correo);
	                    sc.close();
	
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
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    System.out.print("Ingrese la nueva fecha de nacimiento (dd/MM/yyyy): ");
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
                    sc.close();

                    int resultadoModificacion = gestor.modificarJugador(correoModificar, nuevoNombre, nuevaFechaNacimiento, nuevoCorreo);

                    switch (resultadoModificacion)
                    {
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
                    
                // Listar usuarios registrados
                case 3:
                	sc.close(); // Cerramos el scanner porque no nos va a hacer falta
                    int vacia = gestor.listaVacia();
                    if (vacia == 1) 
                    {
                        System.out.println("La lista de jugadores está vacía.");
                    } 
                    else
                    {
                    	System.out.println("Listando jugadores: ");
                    	gestor.listarJugadores();
                    }
                    break;
                    
                // Salir al menú principal
                case 4: 
                	sc.close(); // Cerramos el scanner porque no nos va a hacer falta
                    System.out.println("Saliendo del programa...");
                    return;

                default:
                    System.out.println("Opción no válida. Por favor, intente nuevamente.");
                    break;
            }
        } while (opcion != 4);
    }
}

