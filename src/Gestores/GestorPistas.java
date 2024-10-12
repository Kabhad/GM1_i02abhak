package Gestores;

import java.util.ArrayList;
import java.util.List;

import Clases.Material;
import Clases.Pista;

import Clases.TipoMaterial;
import Clases.EstadoMaterial;

import Clases.tamanioPista;

public class GestorPistas {
    
    private List<Pista> pistas;      // Lista de pistas disponibles en el sistema
    private List<Material> materiales;  // Lista de materiales disponibles en el sistema
    
    public GestorPistas() {
        this.pistas = new ArrayList<>();
        this.materiales = new ArrayList<>();
    }
    
    // Método para crear una nueva pista y añadirla a la lista de pistas
    public void crearPista(String nombre, boolean disponible, boolean exterior, tamanioPista pista, int maxJugadores) {
        Pista nuevaPista = new Pista(nombre, disponible, exterior, pista, maxJugadores);
        pistas.add(nuevaPista);
    }
    
    // Método para crear un nuevo material y añadirlo a la lista de materiales
    public void crearMaterial(int id, TipoMaterial tipo, boolean usoExterior, EstadoMaterial estado) {
        Material nuevoMaterial = new Material(id, tipo, usoExterior, estado);
        materiales.add(nuevoMaterial);
    }
    
    // Método para asociar un material a una pista disponible
    public boolean asociarMaterialAPista(String nombrePista, int idMaterial) {
        Pista pistaSeleccionada = buscarPistaPorNombre(nombrePista);
        Material materialSeleccionado = buscarMaterialPorId(idMaterial);
        
        if (pistaSeleccionada == null) {
            System.out.println("La pista no existe.");
            return false;
        }

        if (materialSeleccionado == null) {
            System.out.println("El material no existe.");
            return false;
        }

        if (!pistaSeleccionada.isDisponible()) {
            System.out.println("La pista no está disponible.");
            return false;
        }

        if (materialSeleccionado.getEstado() != EstadoMaterial.DISPONIBLE) {
            System.out.println("El material no está disponible (en mal estado o reservado).");
            return false;
        }

        // Verificar si el material ya está asignado a otra pista
        for (Pista pista : pistas) {
            if (pista.getMateriales().contains(materialSeleccionado)) {
                System.out.println("El material ya está asignado a otra pista.");
                return false;
            }
        }

        // Asociar el material a la pista si cumple las restricciones
        return pistaSeleccionada.asociarMateriales(materialSeleccionado);
    }
    
    // Método para listar todas las pistas no disponibles
    public List<Pista> listarPistasNoDisponibles() {
        List<Pista> pistasNoDisponibles = new ArrayList<>();
        for (Pista pista : pistas) {
            if (!pista.isDisponible()) {
                pistasNoDisponibles.add(pista);
            }
        }
        return pistasNoDisponibles;
    }
    
    // Método para buscar pistas disponibles según el número de jugadores y tipo de pista
    public List<Pista> buscarPistasDisponibles(int numJugadores, tamanioPista tipoPista) {
        List<Pista> pistasLibres = new ArrayList<>();
        for (Pista pista : pistas) {
            if (pista.isDisponible() && pista.getPista() == tipoPista && pista.getMax_jugadores() >= numJugadores) {
                pistasLibres.add(pista);
            }
        }
        return pistasLibres;
    }
    
    // Método auxiliar para buscar una pista por su nombre
    private Pista buscarPistaPorNombre(String nombrePista) {
        for (Pista pista : pistas) {
            if (pista.getNombrePista().equalsIgnoreCase(nombrePista)) {
                return pista;
            }
        }
        return null;
    }
    
    // Método auxiliar para buscar un material por su ID
    private Material buscarMaterialPorId(int idMaterial) {
        for (Material material : materiales) {
            if (material.getId() == idMaterial) {
                return material;
            }
        }
        return null;
    }
    
    // Método para listar todas las pistas con sus detalles
    public void listarPistas() {
        for (Pista pista : pistas) {
            System.out.println(pista.toString());
        }
    }
}
