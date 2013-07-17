import java.util.concurrent.Semaphore;

public class Main{
	public static void main(String args[]){
		final int F = 18, C = 15;
		Semaphore semaforo = new Semaphore(1);
		PanelPuntuacion panelPuntuacion = new PanelPuntuacion();
		PanelBotones panelBotones = new PanelBotones(F,C,semaforo,panelPuntuacion);
		Tablero tablero = new Tablero(F, C,F-1,C-1);
		Interfaz interfaz = new Interfaz(panelBotones,panelPuntuacion);
		Interaccion interaccion = new Interaccion(F, C, tablero, interfaz, semaforo,panelBotones,panelPuntuacion);

		tablero.iniciarMat();
		interaccion.iniciar();
		
	}
}