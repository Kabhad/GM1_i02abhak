package Gestores;

import java.util.ArrayList;
import java.util.List;

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
    public boolean darDeAltaUsuario(Jugador nuevoUsuario) 
    {
        // Verificar si el correo electrónico ya está registrado
        for (Jugador usuario : listaUsuarios) 
        {
            if (usuario.getCorreoElectronico().equalsIgnoreCase(nuevoUsuario.getCorreoElectronico()))
            {
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
    public boolean modificarUsuario(String correoElectronico, Jugador datosActualizados)
    {
        for (Jugador usuario : listaUsuarios)
        {
            if (usuario.getCorreoElectronico().equalsIgnoreCase(correoElectronico))
            {
                // Actualizamos la información del usuario
                usuario.setNombreApellidos(datosActualizados.getNombreApellidos());
                usuario.setFechaNacimiento(datosActualizados.getFechaNacimiento());
                usuario.setCorreoElectronico(datosActualizados.getCorreoElectronico());
                // No modificamos la fecha de inscripción, ya que debe permanecer igual
                System.out.println("Usuario actualizado correctamente.");
                return true;
            }
        }
        System.out.println("No se encontró un usuario con el correo electrónico: " + correoElectronico);
        return false; // No se encontró el usuario
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
