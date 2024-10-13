package Gestores;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Clases.Jugador;

public class GestorUsuarios 
{
	// Atributo que contiene la lista de usuarios registrados
    private List<Jugador> listaUsuarios;
    
 // Constructor vacío que inicializa la lista de usuarios
    public GestorUsuarios() 
    {
        listaUsuarios = new ArrayList<>();
    }
    
    // Método para dar de alta a un usuario
    public boolean altaUsuario() 
    {
        Scanner scanner = new Scanner(System.in);
        Jugador nuevoUsuario = new Jugador();

        // Pedir el nombre y apellidos del usuario
        System.out.print("Ingrese nombre y apellidos: ");
        String nombreApellidos = scanner.nextLine();
        nuevoUsuario.setNombreApellidos(nombreApellidos);

        // Pedir la fecha de nacimiento
        System.out.print("Ingrese fecha de nacimiento (dd/MM/yyyy): ");
        String fechaNacimientoStr = scanner.nextLine();
        // Declaramos objeto sdf para para convertir el String de la fecha a formato Date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Le decimos a Java el formato con el que se espera que se introduzca la fecha
        // Variable de la clase java.util.Date, que es la clase de Java que representa una fecha
        java.util.Date fechaNacimiento = null; // Está declarada a null porque aun no hemos convertido la entrada del usuario a una fecha real
        // try-catch se utiliza para lanzar una excepcion en caso de que la fecha introducida este en un formato incorrecto
        try 
        {
            fechaNacimiento = sdf.parse(fechaNacimientoStr);
        } 
        // Si hay un error, el programa entra en el bloque catch
        catch (ParseException e) 
        {
            System.out.println("Error en el formato de la fecha. Intente nuevamente.");
            return false;  // Si hay error en el formato, salir del proceso de alta
        }
        // Si la fecha se pudo convertir correctamente, se asigna al nuevo usuario
        nuevoUsuario.setFechaNacimiento(fechaNacimiento);

        // Pedir el correo electrónico del usuario
        System.out.print("Ingrese el correo electrónico: ");
        String correo = scanner.nextLine();
        nuevoUsuario.setCorreoElectronico(correo);

        // Verificar si el correo electrónico ya está registrado
        for (Jugador usuario : listaUsuarios) {
            if (usuario.getCorreoElectronico().equalsIgnoreCase(nuevoUsuario.getCorreoElectronico())) {
                System.out.println("El usuario con el correo electrónico " + nuevoUsuario.getCorreoElectronico() + " ya está registrado.");
                return false; // Usuario ya está registrado
            }
        }

        // Si no está registrado, agregar a la lista de usuarios
        listaUsuarios.add(nuevoUsuario);
        System.out.println("Usuario " + nuevoUsuario.getNombreApellidos() + " registrado con éxito.");
        return true; // Registro exitoso
    }
    
    // Método para modificar la información de un usuario existente
    public boolean modificarUsuario() 
    {
        Scanner scanner = new Scanner(System.in);
        
        // Pedimos el correo del usuario a modificar
        System.out.print("Ingrese el correo electrónico del usuario que desea modificar: ");
        String correoElectronico = scanner.nextLine();

        // Buscar el usuario por correo electrónico
        for (Jugador usuario : listaUsuarios) 
        {
            if (usuario.getCorreoElectronico().equalsIgnoreCase(correoElectronico))
            {
                // Bucle para permitir múltiples modificaciones, hasta que el usuario decida finalizar el proceso de modificación
                int opcion;
                do 
                {
                    System.out.println("\n¿Qué campo desea modificar?");
                    System.out.println("1. Nombre y apellidos");
                    System.out.println("2. Fecha de nacimiento");
                    System.out.println("3. Correo electrónico");
                    System.out.println("4. Salir");
                    System.out.print("Seleccione una opción: ");
                    opcion = scanner.nextInt();

                    switch (opcion) 
                    {
                        case 1:
                            // Modificar nombre y apellidos
                            System.out.print("Ingrese el nuevo nombre y apellidos: ");
                            String nuevoNombre = scanner.nextLine();
                            usuario.setNombreApellidos(nuevoNombre);
                            System.out.println("Nombre y apellidos actualizados.");
                            break;

                        case 2:
                            // Modificar fecha de nacimiento
                            System.out.print("Ingrese la nueva fecha de nacimiento (dd/MM/yyyy): ");
                            String nuevaFechaStr = scanner.nextLine();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            java.util.Date nuevaFecha = null;
                            try 
                            {
                                nuevaFecha = sdf.parse(nuevaFechaStr); // parse para convertir el string introducido en formato Date
                                usuario.setFechaNacimiento(nuevaFecha);
                                System.out.println("Fecha de nacimiento actualizada.");
                            }
                            catch (ParseException e)
                            {
                                System.out.println("Formato de fecha incorrecto. Intente nuevamente.");
                            }
                            break;

                        case 3:
                            // Modificar correo electrónico
                            System.out.print("Ingrese el nuevo correo electrónico: ");
                            String nuevoCorreo = scanner.nextLine();
                            
                            // Verificar si el nuevo correo ya está en uso
                            boolean correoExistente = false;
                            for (Jugador u : listaUsuarios) 
                            {
                                if (u.getCorreoElectronico().equalsIgnoreCase(nuevoCorreo)) 
                                {
                                    correoExistente = true;
                                    break; // break para salir del bucle
                                }
                            }

                            if (correoExistente) 
                            {
                                System.out.println("El correo electrónico ya está registrado por otro usuario. No se puede modificar.");
                            } 
                            else 
                            {
                                usuario.setCorreoElectronico(nuevoCorreo);
                                System.out.println("Correo electrónico actualizado.");
                            }
                            break;

                        case 4:
                            // Salir de la modificación
                            System.out.println("Saliendo de la modificación...");
                            break;

                        default:
                            // Opción no válida
                            System.out.println("Opción no válida. Por favor, intente nuevamente.");
                            break;
                    }
                } while (opcion != 4);

                System.out.println("Modificación del usuario completada.");
                return true;
            }
        }

        // Si no se encuentra el usuario con el correo dado
        System.out.println("No se encontró un usuario con el correo electrónico: " + correoElectronico);
        return false;
    }
    
    // Método para listar a los usuarios registrados
    public void listarUsuarios() 
    {
    	// Comprobamos si la lista esta vacía
        if (listaUsuarios.isEmpty()) 
        {
            System.out.println("No hay usuarios registrados.");
        } 
        else 
        {
        	System.out.println("Usuarios registrados:");
            for (Jugador usuario : listaUsuarios) 
            {
                System.out.println(usuario.toString());
                System.out.println("---------------------------");
            }
        }
    }
}
