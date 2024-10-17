package es.uco.pw.gestores.pistas;

import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.io.*;

import es.uco.pw.classes.material.Material;
import es.uco.pw.classes.pista.Pista;

import es.uco.pw.classes.material.TipoMaterial;
import es.uco.pw.classes.material.EstadoMaterial;
import es.uco.pw.classes.pista.TamanoPista;

public class GestorPistas {

    private static GestorPistas instancia; // Instancia única (Singleton)
    private List<Pista> pistas;            // Lista de pistas disponibles en el sistema
    private List<Material> materiales;     // Lista de materiales disponibles en el sistema
    private String ficheroPistasPath;
    private String ficheroMaterialesPath;

    // Constructor privado para evitar instanciación directa
    private GestorPistas() {
        this.pistas = new ArrayList<>();
        this.materiales = new ArrayList<>();
        this.cargarRutaFicheros(); // Cargar las rutas de los ficheros desde properties.txt
    }

    // Método estático para obtener la única instancia del gestor
    public static synchronized GestorPistas getInstance() {
        if (instancia == null) {
            instancia = new GestorPistas();
        }
        return instancia;
    }

    // Método para cargar la ruta de los ficheros desde properties.txt
    private void cargarRutaFicheros() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/Ficheros/properties.txt")) {
            properties.load(fis);
            this.ficheroPistasPath = properties.getProperty("pistasFile");
            this.ficheroMaterialesPath = properties.getProperty("materialesFile");
        } catch (IOException e) {
            System.out.println("Error al leer el fichero de propiedades: " + e.getMessage());
        }
    }

    // Método para cargar las pistas desde un fichero
    public void cargarPistasDesdeFichero() {
        try (BufferedReader br = new BufferedReader(new FileReader(ficheroPistasPath))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                int idPista = Integer.parseInt(datos[0]);
                String nombrePista = datos[1];
                boolean disponible = Boolean.parseBoolean(datos[2]);
                boolean exterior = Boolean.parseBoolean(datos[3]);
                TamanoPista tamanioPista = TamanoPista.valueOf(datos[4]);
                int maxJugadores = Integer.parseInt(datos[5]);

                Pista pista = new Pista(nombrePista, disponible, exterior, tamanioPista, maxJugadores);
                pista.setIdPista(idPista);  // Setear el ID directamente
                pistas.add(pista);
            }
            System.out.println("Pistas cargadas desde el fichero " + ficheroPistasPath);
        } catch (IOException e) {
            System.out.println("Error al cargar las pistas: " + e.getMessage());
        }
    }

    // Método para guardar las pistas en un fichero
    public void guardarPistasEnFichero() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroPistasPath))) {
            for (Pista pista : pistas) {
                StringBuilder sb = new StringBuilder();
                sb.append(pista.getIdPista()).append(",")
                  .append(pista.getNombrePista()).append(",")
                  .append(pista.isDisponible()).append(",")
                  .append(pista.isExterior()).append(",")
                  .append(pista.getPista().name()).append(",")
                  .append(pista.getMax_jugadores());

                bw.write(sb.toString());
                bw.newLine();
            }
            System.out.println("Pistas guardadas en el fichero " + ficheroPistasPath);
        } catch (IOException e) {
            System.out.println("Error al guardar las pistas: " + e.getMessage());
        }
    }

    // Método para cargar los materiales desde un fichero
    public void cargarMaterialesDesdeFichero() {
        try (BufferedReader br = new BufferedReader(new FileReader(ficheroMaterialesPath))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                int idMaterial = Integer.parseInt(datos[0]);
                TipoMaterial tipo = TipoMaterial.valueOf(datos[1]);
                boolean usoExterior = Boolean.parseBoolean(datos[2]);
                EstadoMaterial estado = EstadoMaterial.valueOf(datos[3]);

                Material material = new Material(idMaterial, tipo, usoExterior, estado);
                materiales.add(material);
            }
            System.out.println("Materiales cargados desde el fichero " + ficheroMaterialesPath);
        } catch (IOException e) {
            System.out.println("Error al cargar los materiales: " + e.getMessage());
        }
    }

    // Método para guardar los materiales en un fichero
    public void guardarMaterialesEnFichero() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroMaterialesPath))) {
            for (Material material : materiales) {
                StringBuilder sb = new StringBuilder();
                sb.append(material.getId()).append(",")
                  .append(material.getTipo().name()).append(",")
                  .append(material.isUsoExterior()).append(",")
                  .append(material.getEstado().name());

                bw.write(sb.toString());
                bw.newLine();
            }
            System.out.println("Materiales guardados en el fichero " + ficheroMaterialesPath);
        } catch (IOException e) {
            System.out.println("Error al guardar los materiales: " + e.getMessage());
        }
    }

    // Método para crear una nueva pista y añadirla a la lista de pistas
    public void crearPista(String nombre, boolean disponible, boolean exterior, TamanoPista pista, int maxJugadores) {
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
        return pistaSeleccionada.asociarMaterialAPista(materialSeleccionado);
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
    public List<Pista> buscarPistasDisponibles(int numJugadores, TamanoPista tipoPista) {
        List<Pista> pistasLibres = new ArrayList<>();
        for (Pista pista : pistas) {
            if (pista.isDisponible() && pista.getPista() == tipoPista && pista.getMax_jugadores() >= numJugadores) {
                pistasLibres.add(pista);
            }
        }
        return pistasLibres;
    }


    // Método para listar todas las pistas con sus detalles
    public String listarPistas() {
        StringBuilder resultado = new StringBuilder();
        for (Pista pista : pistas) {
            resultado.append("Nombre de la Pista: ").append(pista.getNombrePista()).append("\n")
                .append("Disponible: ").append(pista.isDisponible() ? "Sí" : "No").append("\n")
                .append("Exterior: ").append(pista.isExterior() ? "Sí" : "No").append("\n")
                .append("Tamaño de la Pista: ").append(pista.getPista().toString()).append("\n")
                .append("Máximo de Jugadores: ").append(pista.getMax_jugadores()).append("\n")
                .append("Materiales Disponibles: ").append(pista.getMateriales().toString()).append("\n")
                .append("----------------------------------\n");
        }
        return resultado.toString();
    }
}
