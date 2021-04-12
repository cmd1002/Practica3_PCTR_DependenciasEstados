package src.p03.c01;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.*;

public class Parque implements IParque{

	// DONE 
	public static final int AFORO_MAX = 50;
	private int contadorPersonasTotales;
	private Hashtable<String, Integer> contadoresPersonasPuerta;
	private Hashtable<String, Integer> contadoresSalidaPuerta;
	
	
	public Parque() {	// TODO
		contadorPersonasTotales = 0;
		contadoresPersonasPuerta = new Hashtable<String, Integer>();
		contadoresSalidaPuerta = new Hashtable<String, Integer>();// DONE
	}

	@Override
	public void entrarAlParque(String puerta){		// TODO
		
		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuerta.get(puerta) == null){
			contadoresPersonasPuerta.put(puerta, 0);
		}
		
		// Precondiciones - TODO
		comprobarAntesDeEntrar();	
		
		// Aumentamos el contador total y el individual
		contadorPersonasTotales++;		
		contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta)+1);
		
		// Imprimimos el estado del parque
		imprimirInfo(puerta, "Entrada");
		
		// Postcondiciones - TODO
		
		
		// Invariante - TODO
		
	}
	
	// 
	// TODO Método salirDelParque
	//
	public void salirDelParque(String puerta) {
		
		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuerta.get(puerta) == null){
			contadoresPersonasPuerta.put(puerta, 0);
		}
		
		// Precondiciones - TODO
		comprobarAntesDeSalir();	
		
		// Aumentamos el contador total y el individual
		contadorPersonasTotales--;		
		contadoresPersonasPuerta.put(puerta, contadoresSalidaPuerta.get(puerta)+1);
		
		// Imprimimos el estado del parque
		imprimirInfo(puerta, "Salida");
		
		// Postcondiciones - TODO
		
		
		// Invariante - TODO
	}
	
	private void imprimirInfo (String puerta, String movimiento){
		System.out.println(movimiento + " por puerta " + puerta);
		System.out.println("--> Personas en el parque " + contadorPersonasTotales); //+ " tiempo medio de estancia: "  + tmedio);
		
		// Iteramos por todas las puertas e imprimimos sus entradas
		for(String p: contadoresPersonasPuerta.keySet()){
			System.out.println("----> Por puerta " + p + " " + contadoresPersonasPuerta.get(p));
		}
		System.out.println(" ");
	}
	
	private int sumarContadoresPuerta() {
		int sumaContadoresPuerta = 0;
			Enumeration<Integer> iterPuertas = contadoresPersonasPuerta.elements();
			while (iterPuertas.hasMoreElements()) {
				sumaContadoresPuerta += iterPuertas.nextElement();
			}
		return sumaContadoresPuerta;
	}
	
	protected void checkInvariante() {
		assert sumarContadoresPuerta() == contadorPersonasTotales : "INV: La suma de contadores de las puertas debe ser igual al valor del contador del parte";
		// TODO 
		// TODO
	}

	protected void comprobarAntesDeEntrar(){	// TODO
		
		while(contadorPersonasTotales == AFORO_MAX ){ 
	        try {
	        	wait(); 
			} catch (InterruptedException e) {
				Logger.getGlobal().log(Level.INFO, "Entrada interrumpida, superado el aforo maximo");
				Logger.getGlobal().log(Level.INFO, e.toString());
			}
	    }
	}

	protected void comprobarAntesDeSalir(){		// TODO
		while(contadorPersonasTotales == 0 ){ 
	        try {
	        	wait(); 
			} catch (InterruptedException e) {
				Logger.getGlobal().log(Level.INFO, "Salida interrumpida, parque vacío");
				Logger.getGlobal().log(Level.INFO, e.toString());
			}
	    }
	}


}