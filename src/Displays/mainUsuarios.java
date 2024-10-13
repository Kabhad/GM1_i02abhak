package Displays;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import Clases.Jugador;
import Gestores.GestorUsuarios;

public class mainUsuarios {
    
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
	
	 public static void main(String[] args) 
	 {
		// Instancia del gestor de usuarios
	     GestorUsuarios gestor = new GestorUsuarios();
		 Scanner sc = new Scanner(System.in);
		 int opcion; 
		 
		 while (true)
		 {
			 imprimirMenu(); //Mostrar el menu de Usuarios
			 opcion = sc.nextInt(); //Leer la opción del Usuario
			 sc.nextLine(); //Limpiar el buffer después de leer enteros
			 
			 switch(opcion)
			 {
			 case 1:
				 	System.out.println("Iniciando proceso de Alta...");
				 	gestor.altaUsuario();
				 	break;
				 	
			 case 2: 
				 //Modificar un usuario
				 System.out.println("Iniciando proceso de Modificación...");
				 gestor.modificarUsuario();
                 break;
                 
			 case 3: 
				// Listar usuarios registrados
                 gestor.listarUsuarios();
                 break;
                
			 case 4:
				// Salir al menú principal
                 System.out.println("Volviendo al menú principal...");
                 sc.close(); // Cerramos el scanner
                 return; // Salir del menú de gestión de usuarios
                 
             default: 
            	 System.out.println("Opción no válida. Por favor, intente nuevamente.");
            	 break;
				 
			 }
		 } 
	 }
}
