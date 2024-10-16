package es.uco.pw.gestores.jugadores;

import es.uco.pw.classes.jugador.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GestorJugadores {
	// Atributo que contiene la lista de usuarios registrados
    private List<Jugador> listaJugadores;
    
 // Constructor vacío que inicializa la lista de usuarios
    public GestorJugadores() 
    {
        listaJugadores = new ArrayList<>();
    }
    
    // Método para dar de alta a un usuario
    public boolean altaJugador(Jugador nuevoJugador) {
        // Verificar si el correo electrónico ya está registrado
        for (Jugador jugador : listaJugadores) {
            if (jugador.getCorreoElectronico().equalsIgnoreCase(nuevoJugador.getCorreoElectronico())) {
                return false; // Jugador ya está registrado, retorna false
            }
        }

        // Si no está registrado, agregar a la lista de jugadores
        listaJugadores.add(nuevoJugador);
        return true; // Registro exitoso, retorna true
    }
    
    // Método para modificar la información de un usuario existente
    public int modificarJugador(String correoElectronico, String nuevoNombre, Date nuevaFechaNacimiento, String nuevoCorreo) {
        // Buscar el usuario por correo electrónico
        for (Jugador jugador : listaJugadores) {
            if (jugador.getCorreoElectronico().equalsIgnoreCase(correoElectronico)) {
                // Verificar si el nuevo correo ya está en uso (excepto el del propio usuario)
                for (Jugador usuario : listaJugadores) {
                    if (!usuario.getCorreoElectronico().equalsIgnoreCase(correoElectronico) &&
                        usuario.getCorreoElectronico().equalsIgnoreCase(nuevoCorreo)) {
                        return 0; // El nuevo correo ya está en uso
                    }
                }

                // Actualizar los datos del usuario
                jugador.setNombreApellidos(nuevoNombre);
                jugador.setFechaNacimiento(nuevaFechaNacimiento);
                jugador.setCorreoElectronico(nuevoCorreo);
                return 1; // Modificación exitosa
            }
        }

        // Si no se encuentra el usuario
        return -1; // Usuario no encontrado
    }
    
    // Método para listar a los usuarios registrados
    public String listarJugadores() {
        String resultado = "";
        
        for (Jugador jugador : listaJugadores) {
            resultado += "Nombre: " + jugador.getNombreApellidos() + "\n";
            resultado += "Fecha de Nacimiento: " + jugador.getFechaNacimiento() + "\n";
            resultado += "Correo Electrónico: " + jugador.getCorreoElectronico() + "\n";
            resultado += "----------------------------------\n";
        }
        
        return resultado;
    }
    
    // Método para comprobar si la lista está vacía
    public int listaVacia()
	{
        if (listaJugadores.isEmpty()) 
        {
            return 1;  // Lista vacía
        } 
        else 
        {
            return 0;  // Lista con jugadores
        }
    }
}