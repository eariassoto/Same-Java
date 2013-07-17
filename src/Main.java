import java.util.concurrent.Semaphore;

public class Main{
	public static void main(String args[]){
		
		final int F = 18, C = 15;
		
		Semaphore semaforo = new Semaphore(1);
		
		GuardarPuntuacion guardarPuntuacion = new GuardarPuntuacion();
		LeerPuntuacion leerPuntuacion = new LeerPuntuacion(guardarPuntuacion);
		leerPuntuacion.puntuacionExiste();
		Puntuacion puntuacion = leerPuntuacion.leer();
		
		PanelPuntuacion panelPuntuacion = new PanelPuntuacion(puntuacion,guardarPuntuacion);
		PanelBotones panelBotones = new PanelBotones(F,C,semaforo,panelPuntuacion);
		Tablero tablero = new Tablero(F, C,F-1,C-1);
		Interfaz interfaz = new Interfaz(panelBotones,panelPuntuacion);
		Interaccion interaccion = new Interaccion(F, C, tablero, interfaz, semaforo,panelBotones,panelPuntuacion);

		tablero.iniciarMat();
		interaccion.iniciar();
		
		
	}
}