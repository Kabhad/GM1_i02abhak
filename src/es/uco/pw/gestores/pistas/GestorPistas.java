package es.uco.pw.gestores.pistas;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.*;
import java.io.*;

import es.uco.pw.classes.material.Material;
import es.uco.pw.classes.pista.Pista;
import es.uco.pw.classes.material.TipoMaterial;
import es.uco.pw.classes.material.EstadoMaterial;
import es.uco.pw.classes.pista.TamanoPista;

/**
 * Clase que gestiona las pistas y materiales del sistema.
 * Implementa el patrón Singleton para asegurar que solo haya una instancia de esta clase.
 */
public class GestorPistas {

    private static GestorPistas instancia; // Instancia única (Singleton)
    private List<Pista> pistas;            // Lista de pistas disponibles en el sistema
    private List<Material> materiales;     // Lista de materiales disponibles en el sistema
    private String ficheroPistasPath;      // Ruta del fichero de pistas
    private String ficheroMaterialesPath;   // Ruta del fichero de materiales

    /**
     * Constructor privado para evitar la instanciación directa.
     * Inicializa las listas de pistas y materiales y carga las rutas de los ficheros.
     */
    private GestorPistas() {
        this.pistas = new ArrayList<>();
        this.materiales = new ArrayList<>();
        this.cargarRutaFicheros(); // Cargar las rutas de los ficheros desde properties.txt
    }

    /**
     * Método estático para obtener la única instancia del gestor.
     * 
     * @return La instancia única de GestorPistas.
     */
    public static synchronized GestorPistas getInstance() {
        if (instancia == null) {
            instancia = new GestorPistas();
        }
        return instancia;
    }

    /**
     * Método para cargar la ruta de los ficheros desde properties.txt.
     * 
     * @throws RuntimeException Si hay un error al leer el fichero de propiedades.
     */
    private void cargarRutaFicheros() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/Ficheros/properties.txt")) {
            properties.load(fis);
            this.ficheroPistasPath = properties.getProperty("pistasFile");
            this.ficheroMaterialesPath = properties.getProperty("materialesFile");
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el fichero de propiedades: " + e.getMessage());
        }
    }

    /**
     * Método para cargar las pistas desde un fichero.
     * 
     * @throws IOException Si hay un error al leer el fichero de pistas.
     */
    public void cargarPistasDesdeFichero() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(ficheroPistasPath))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                int idPista = Integer.parseInt(datos[0]);
                String nombrePista = datos[1];
                boolean disponible = Boolean.parseBoolean(datos[2]);
                boolean exterior = Boolean.parseBoolean(datos[3]);
                TamanoPista tamanioPista = TamanoPista.valueOf(datos[4]);
                int maxJugadores = Integer.parseInt(datos[5]);

                Pista pista = new Pista(nombrePista, disponible, exterior, tamanioPista, maxJugadores);
                pista.setIdPista(idPista);

                // Cargar materiales asociados
                if (datos.length > 6 && !datos[6].isEmpty()) {
                    String[] materialesIds = datos[6].split(",");
                    for (String materialIdStr : materialesIds) {
                        try {
                            int idMaterial = Integer.parseInt(materialIdStr.trim());
                            Material material = buscarMaterialPorId(idMaterial);
                            if (material != null) {
                                pista.asociarMaterialAPista(material);
                            }
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("Error al parsear el ID de material: " + materialIdStr);
                        }
                    }
                }
                pistas.add(pista);
            }
        }
    }

    /**
     * Método para guardar las pistas en un fichero.
     * 
     * @throws IOException Si hay un error al escribir en el fichero de pistas.
     */
    public void guardarPistasEnFichero() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroPistasPath))) {
            for (Pista pista : pistas) {
                StringBuilder sb = new StringBuilder();
                sb.append(pista.getIdPista()).append(";")
                  .append(pista.getNombrePista()).append(";")
                  .append(pista.isDisponible()).append(";")
                  .append(pista.isExterior()).append(";")
                  .append(pista.getPista().name()).append(";")
                  .append(pista.getMax_jugadores());

                List<Material> materiales = pista.getMateriales();
                if (!materiales.isEmpty()) {
                    sb.append(";");
                    for (int i = 0; i < materiales.size(); i++) {
                        sb.append(materiales.get(i).getId());
                        if (i < materiales.size() - 1) {
                            sb.append(",");
                        }
                    }
                }
                bw.write(sb.toString());
                bw.newLine();
            }
        }
    }

    /**
     * Método para cargar los materiales desde un fichero.
     * 
     * @throws IOException Si hay un error al leer el fichero de materiales.
     */
    public void cargarMaterialesDesdeFichero() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(ficheroMaterialesPath))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    String[] datos = linea.split(";");
                    if (datos.length >= 4) {
                        try {
                            int idMaterial = Integer.parseInt(datos[0].trim());
                            TipoMaterial tipo = TipoMaterial.valueOf(datos[1].trim());
                            boolean usoExterior = Boolean.parseBoolean(datos[2].trim());
                            EstadoMaterial estado = EstadoMaterial.valueOf(datos[3].trim());

                            Material material = new Material(idMaterial, tipo, usoExterior, estado);
                            materiales.add(material);
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("Error al parsear material en la línea: " + linea);
                        }
                    } else {
                        throw new IllegalArgumentException("Línea incompleta en el fichero de materiales: " + linea);
                    }
                }
            }
        }
    }

    /**
     * Método para guardar los materiales en un fichero.
     * 
     * @throws IOException Si hay un error al escribir en el fichero de materiales.
     */
    public void guardarMaterialesEnFichero() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroMaterialesPath))) {
            for (Material material : materiales) {
                StringBuilder sb = new StringBuilder();
                sb.append(material.getId()).append(";")
                  .append(material.getTipo().name()).append(";")
                  .append(material.isUsoExterior()).append(";")
                  .append(material.getEstado().name());

                bw.write(sb.toString());
                bw.newLine();
            }
        }
    }

    /**
     * Método para crear una nueva pista y añadirla a la lista de pistas.
     * 
     * @param nombre       Nombre de la nueva pista.
     * @param disponible   Indica si la pista está disponible.
     * @param exterior     Indica si la pista es exterior.
     * @param pista        Tamaño de la pista.
     * @param maxJugadores Número máximo de jugadores en la pista.
     */
    public void crearPista(String nombre, boolean disponible, boolean exterior, TamanoPista pista, int maxJugadores) {
        Pista nuevaPista = new Pista(nombre, disponible, exterior, pista, maxJugadores);
        pistas.add(nuevaPista);
    }

    /**
     * Método para crear un nuevo material y añadirlo a la lista de materiales.
     * 
     * @param id          Identificador del nuevo material.
     * @param tipo        Tipo del nuevo material.
     * @param usoExterior Indica si el material es para uso exterior.
     * @param estado      Estado del nuevo material.
     */
    public void crearMaterial(int id, TipoMaterial tipo, boolean usoExterior, EstadoMaterial estado) {
        Material nuevoMaterial = new Material(id, tipo, usoExterior, estado);
        materiales.add(nuevoMaterial);
    }

    /**
     * Método para asociar un material a una pista disponible.
     * 
     * @param nombrePista Nombre de la pista a la que se quiere asociar el material.
     * @param idMaterial  ID del material a asociar.
     * @return True si la asociación fue exitosa, false en caso contrario.
     * @throws IllegalArgumentException Si la pista o el material no existen, o si la pista o el material no están disponibles.
     */
    public boolean asociarMaterialAPista(String nombrePista, int idMaterial) {
        Pista pistaSeleccionada = buscarPistaPorNombre(nombrePista);
        Material materialSeleccionado = buscarMaterialPorId(idMaterial);

        if (pistaSeleccionada == null) {
            throw new IllegalArgumentException("La pista no existe.");
        }

        if (materialSeleccionado == null) {
            throw new IllegalArgumentException("El material no existe.");
        }

        if (!pistaSeleccionada.isDisponible()) {
            throw new IllegalArgumentException("La pista no está disponible.");
        }

        if (materialSeleccionado.getEstado() != EstadoMaterial.DISPONIBLE) {
            throw new IllegalArgumentException("El material no está disponible.");
        }

        for (Pista pista : pistas) {
            if (pista.getMateriales().contains(materialSeleccionado)) {
                throw new IllegalArgumentException("El material ya está asignado a otra pista.");
            }
        }

        return pistaSeleccionada.asociarMaterialAPista(materialSeleccionado);
    }

    /**
     * Método auxiliar para buscar una pista por su nombre.
     * 
     * @param nombrePista Nombre de la pista a buscar.
     * @return La pista correspondiente al nombre dado, o null si no se encuentra.
     */
    private Pista buscarPistaPorNombre(String nombrePista) {
        for (Pista pista : pistas) {
            if (pista.getNombrePista().equalsIgnoreCase(nombrePista)) {
                return pista;
            }
        }
        return null;
    }

    /**
     * Método auxiliar para buscar un material por su ID.
     * 
     * @param idMaterial ID del material a buscar.
     * @return El material correspondiente al ID dado, o null si no se encuentra.
     */
    private Material buscarMaterialPorId(int idMaterial) {
        for (Material material : materiales) {
            if (material.getId() == idMaterial) {
                return material;
            }
        }
        return null;
    }

    /**
     * Método para buscar todas las pistas disponibles.
     * 
     * @return Lista de pistas disponibles.
     */
    public List<Pista> buscarPistasDisponibles() {
        return pistas.stream()
                     .filter(Pista::isDisponible)
                     .collect(Collectors.toList());
    }

    /**
     * Método para listar todas las pistas no disponibles.
     * 
     * @return Lista de pistas no disponibles.
     */
    public List<Pista> listarPistasNoDisponibles() {
        return pistas.stream()
                     .filter(pista -> !pista.isDisponible())
                     .collect(Collectors.toList());
    }

    /**
     * Método para buscar pistas disponibles según el número de jugadores y tipo de pista.
     * 
     * @param numJugadores Número de jugadores que se busca.
     * @param tipoPista    Tipo de pista que se busca.
     * @return Lista de pistas disponibles que cumplen con los criterios dados.
     */
    public List<Pista> buscarPistasDisponibles(int numJugadores, TamanoPista tipoPista) {
        return pistas.stream()
                     .filter(pista -> pista.isDisponible() && pista.getPista() == tipoPista && pista.getMax_jugadores() >= numJugadores)
                     .collect(Collectors.toList());
    }

    /**
     * Método para listar todas las pistas con sus detalles.
     * 
     * @return String con los detalles de todas las pistas.
     */
    public String listarPistas() {
        StringBuilder resultado = new StringBuilder();
        for (Pista pista : pistas) {
            resultado.append("ID: ").append(pista.getIdPista()).append("\n")
                     .append("Nombre Pista: ").append(pista.getNombrePista()).append("\n")
                     .append("Disponible: ").append(pista.isDisponible() ? "Sí" : "No").append("\n")
                     .append("Exterior: ").append(pista.isExterior() ? "Sí" : "No").append("\n")
                     .append("Tamaño Pista: ").append(pista.getPista().toString()).append("\n")
                     .append("Max Jugadores: ").append(pista.getMax_jugadores()).append("\n")
                     .append("Materiales: ");
            
            if (pista.getMateriales().isEmpty()) {
                resultado.append("[]\n");
            } else {
                List<Integer> idsMateriales = pista.getMateriales().stream().map(Material::getId).collect(Collectors.toList());
                resultado.append(idsMateriales).append("\n");
            }

            resultado.append("----------------------------------\n");
        }
        return resultado.toString();
    }

    /**
     * Método para buscar una pista por su ID.
     * 
     * @param idPista ID de la pista a buscar.
     * @return La pista correspondiente al ID dado, o null si no se encuentra.
     */
    public Pista buscarPistaPorId(int idPista) {
        for (Pista pista : pistas) {
            if (pista.getIdPista() == idPista) {
                return pista;
            }
        }
        return null;
    }
}

