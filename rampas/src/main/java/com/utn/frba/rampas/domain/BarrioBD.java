package com.utn.frba.rampas.domain;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class BarrioBD implements Serializable {

//	Ejemplo	
//	nombre: "Almagro"
// 	limites: "[[[[-58.47242,-34.5661],[-58.47296,-34.56642],[-58.47299,-34.56644],[-58.47242,-34.5661]]]]"

	@Expose @Id private Long id;
	@Expose @Index private String nombre;
	@Expose private String limites;	

	/* Es necesario este constructor para que funcione el GSON */
	public BarrioBD() { }
	
	public BarrioBD(String nombre,String limites) {
		setNombre(nombre);
		setLimites(limites);
	}

	public long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getLimites() {
		return limites;
	}
	
	public void setLimites(String limites) {
		this.limites = limites;
	}
	
	public ArrayList<Point> getPuntos() {
		ArrayList<Point> puntos = new ArrayList<Point>();
		String limitesSinCorchetes = getLimites().substring(4,getLimites().length()-4);
		String[] limitesComoArray = limitesSinCorchetes.split("\\],\\[");
		String[] aux;
		for (int i = 0; i < limitesComoArray.length; i++) {
			aux = limitesComoArray[i].split(",");
			puntos.add(new Point(Double.parseDouble(aux[0]),Double.parseDouble(aux[1])));
		}
		return puntos;
	}
	
	public boolean contiene(Rampa unaRampa) {
		ArrayList<Point> puntos = getPuntos();
		Polygon poligono = Polygon.Builder().addVertexes(puntos).build();
		if (poligono.contains(new Point(unaRampa.getLatitud(),unaRampa.getLongitud()))) {
			return true;
		}	
		else {
			return false;
		}
	}
	
}
