package src.p03.c01;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.*;

public class Parque implements IParque{

	public static final int AFORO_MAX = 50;
	private int contadorPersonasTotales;
	private Hashtable<String, Integer> contadoresPersonasPuerta;
	private Hashtable<String, Integer> contadoresSalidaPuerta;
	
	
	public Parque() {
		contadorPersonasTotales = 0;
		contadoresPersonasPuerta = new Hashtable<String, Integer>();
		contadoresSalidaPuerta = new Hashtable<String, Integer>();
	}

	@Override
	public synchronized void entrarAlParque(String puerta){		// TODO
		
		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuerta.get(puerta) == null){
			contadoresPersonasPuerta.put(puerta, 0);
		}
		
		// Precondiciones
		comprobarAntesDeEntrar();	
		
		// Aumentamos el contador total y el individual
		contadorPersonasTotales++;		
		contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta)+1);
		
		// Imprimimos el estado del parque
		imprimirInfo(puerta, "Entrada");
		
		// Invariante
		checkInvariante();
		
		notifyAll();
	}
	
	// 
	// TODO Método salirDelParque
	//
	public synchronized void salirDelParque(String puerta) {
		
		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuerta.get(puerta) == null){
			contadoresPersonasPuerta.put(puerta, 0);
		}
		
		// Precondiciones
		comprobarAntesDeSalir();	
		
		// Aumentamos el contador total y el individual
		contadorPersonasTotales--;
		contadoresSalidaPuerta.put(puerta, contadoresSalidaPuerta.get(puerta)-1);
		
		// Imprimimos el estado del parque
		imprimirInfo(puerta, "Salida");
		
		// Invariante
		checkInvariante();
		
		notifyAll();
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
		assert contadorPersonasTotales > 0 : "INV: No es posible un aforo negativo"; // TODO - Confirmar
		assert contadorPersonasTotales < AFORO_MAX : "INV: No es posible superar el aforo máximo"; // TODO - Confirmar
	}

	protected void comprobarAntesDeEntrar() {
		
		while(contadorPersonasTotales == AFORO_MAX ){ 
	        try {
	        	wait(); 
			} catch (InterruptedException e) {
				Logger.getGlobal().log(Level.INFO, "Entrada interrumpida, superado el aforo maximo");
				Logger.getGlobal().log(Level.INFO, e.toString());
			}
	    }
	}

	protected void comprobarAntesDeSalir(){	
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