package src.p03.c01;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActividadSalidaPuerta{

	private int personas;
	private String puerta;
	private IParque parque;

	public ActividadSalidaPuerta(String puerta, IParque parque, int personas) {
		this.puerta = puerta;
		this.parque = parque;
		this.personas = personas;
	}

	public void run() {
		for (int i = personas; i > 0; i --) {
			try {
				parque.entrarAlParque(puerta);
				TimeUnit.MILLISECONDS.sleep(new Random().nextInt(5)*1000);
			} catch (InterruptedException e) {
				Logger.getGlobal().log(Level.INFO, "Salida interrumpida");
				Logger.getGlobal().log(Level.INFO, e.toString());
				return;
			}
		}
	}
}
