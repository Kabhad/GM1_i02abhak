package Clases;

import java.util.ArrayList;
import java.util.List;

enum tamanioPista
{
	MINIBASKET, ADULTOS, _3VS3
}

public class Pista {

	private String nombrePista;
	private boolean disponible;
	private boolean exterior;
	private tamanioPista pista;
	private int max_jugadores;
	private List<Material> materiales;
	
	public Pista() {
		this.materiales = new ArrayList<>();
	}
	
	public Pista(String nombrePista, boolean disponible, boolean exterior, tamanioPista pista, int max_jugadores) {
		this.nombrePista = nombrePista;
		this.disponible = disponible;
		this.exterior = exterior;
		this.pista = pista;
		this.max_jugadores = max_jugadores;
		this.materiales = new ArrayList<>();
	}

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

	public tamanioPista getPista() {
		return pista;
	}

	public void setPista(tamanioPista pista) {
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
		return "Pista [nombrePista=" + nombrePista + ", disponible=" + disponible + ", exterior=" + exterior
				+ ", pista=" + pista + ", max_jugadores=" + max_jugadores + ", materiales=" + materiales + "]";
	}
	
	public List<Material> consultarMaterial () {
		List<Material> materialesDisponibles = new ArrayList<>();
		for(Material material : materiales) {
			if(material.getEstado() == EstadoMaterial.DISPONIBLE) {
				materialesDisponibles.add(material);
			}
		}
		
		return materialesDisponibles;
	}
	
	public boolean asociarMateriales(Material material) {
		int cont_pelotas = 0, cont_canastas = 0, cont_conos = 0;
		
		for(Material m : materiales) {
			if(m.getTipo() == TipoMaterial.CANASTAS) {cont_canastas++;}
			else if(m.getTipo() == TipoMaterial.CONOS) {cont_conos++;}
			else if(m.getTipo() == TipoMaterial.PELOTAS ) {cont_pelotas++;}
		}
		
		if(exterior && !material.isUsoExterior()) {
			 System.out.println("Si la pista es exterior, solo se pueden añadir materiales de uso exterior.");
			 return false;
		}
		
		if(material.getTipo() == TipoMaterial.CANASTAS && cont_canastas >= 2) {
			System.out.println("No se pueden añadir más de 2 canasatas.");
			return false;
		}
		
		if(material.getTipo() == TipoMaterial.CONOS && cont_conos >= 20) {
			System.out.println("No se pueden añadir más de 20 conos.");
			return false;
		}
		
		if(material.getTipo() == TipoMaterial.PELOTAS && cont_pelotas >= 12) {
			System.out.println("No se pueden añadir más de 12 pelotas.");
			return false;
		}
		
		materiales.add(material);
		return true;
	}
	
}
