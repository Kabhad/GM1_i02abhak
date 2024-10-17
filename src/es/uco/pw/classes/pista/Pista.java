package es.uco.pw.classes.pista;

import java.util.ArrayList;
import java.util.List;
import es.uco.pw.classes.material.*;

public class Pista {

	private static int idCounter = 1; // Contador estático para generar IDs únicos
    private int idPista;
    private String nombrePista;
    private boolean disponible;
    private boolean exterior;
    private TamanoPista pista;
    private int max_jugadores;
    private List<Material> materiales;
    
    // Constructor vacío
    public Pista() {
        this.idPista = idCounter++; // Asignar un ID único
        this.materiales = new ArrayList<>();
    }
    
    // Constructor parametrizado
    public Pista(String nombrePista, boolean disponible, boolean exterior, TamanoPista pista, int max_jugadores) {
        this();
        this.nombrePista = nombrePista;
        this.disponible = disponible;
        this.exterior = exterior;
        this.pista = pista;
        this.max_jugadores = max_jugadores;
    }

    // Método getter para el ID
    public int getIdPista() {
        return idPista;
    }
    
    public void setIdPista(int idPista)
    {
    	this.idPista = idPista;
    }

    // Métodos getter y setter para los demás atributos
    public String getNombrePista() {
        return nombrePista;
    }

    public void setNombrePista(String nombrePista) {
        this.nombrePista = nombrePista;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public boolean isExterior() {
        return exterior;
    }

    public void setExterior(boolean exterior) {
        this.exterior = exterior;
    }

    public TamanoPista getPista() {
        return pista;
    }

    public void setPista(TamanoPista pista) {
        this.pista = pista;
    }

    public int getMax_jugadores() {
        return max_jugadores;
    }

    public void setMax_jugadores(int max_jugadores) {
        this.max_jugadores = max_jugadores;
    }

    public List<Material> getMateriales() {
        return materiales;
    }

    public void setMateriales(List<Material> materiales) {
        this.materiales = materiales;
    }

    @Override
    public String toString() {
        return "ID: " + idPista +
               "\nNombre Pista: " + nombrePista + 
               "\nDisponible: " + disponible +
               "\nExterior: " + exterior +
               "\nTamaño Pista: " + pista +
               "\nMax Jugadores: " + max_jugadores +
               "\nMateriales: " + materiales;
    }

    // Método para consultar materiales disponibles
    public List<Material> consultarMaterialesDisponibles() {
        List<Material> materialesDisponibles = new ArrayList<>();
        for (Material material : materiales) {
            if (material.getEstado() == EstadoMaterial.DISPONIBLE) {
                materialesDisponibles.add(material);
            }
        }
        return materialesDisponibles;
    }

    // Método para asociar un material a la pista
    public boolean asociarMaterialAPista(Material material) {
        // Verificar si la pista es exterior
        if (this.exterior) {
            // Solo se permiten materiales para exterior
            if (!material.isUsoExterior()) {
                System.out.println("El material no puede ser utilizado en una pista exterior.");
                return false;
            }
        }

        // Verificar restricciones de cantidad de materiales
        int cantidadPelotas = 0;
        int cantidadCanastas = 0;
        int cantidadConos = 0;

        for (Material mat : materiales) {
            switch (mat.getTipo()) {
                case PELOTAS:
                    cantidadPelotas++;
                    break;
                case CANASTAS:
                    cantidadCanastas++;
                    break;
                case CONOS:
                    cantidadConos++;
                    break;
                default:
                    break;
            }
        }

        // Verificar cantidades máximas permitidas
        if (material.getTipo() == TipoMaterial.PELOTAS && cantidadPelotas >= 12) {
            System.out.println("No se pueden añadir más de 12 pelotas a la pista.");
            return false;
        }
        if (material.getTipo() == TipoMaterial.CANASTAS && cantidadCanastas >= 2) {
            System.out.println("No se pueden añadir más de 2 canastas a la pista.");
            return false;
        }
        if (material.getTipo() == TipoMaterial.CONOS && cantidadConos >= 20) {
            System.out.println("No se pueden añadir más de 20 conos a la pista.");
            return false;
        }

        // Si pasa todas las verificaciones, añadir el material a la pista
        materiales.add(material);
        return true; // Material añadido exitosamente
    }
}
