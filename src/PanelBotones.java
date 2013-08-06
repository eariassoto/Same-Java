/*
 * @author Emmanuel Arias Soto emmanuel1412@gmail.com
 */
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Iterator;
import java.util.concurrent.Semaphore;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class PanelBotones.
 */
@SuppressWarnings("all")
public class PanelBotones extends JPanel{
	
	/** Las constantes del numero de filas y columnas del tablero,
	 * además de la representación del color del cuadro comido. */
	final int FILA,COLUMNA,COMIDO = -1;
	
	/** Clase interna. */
	AnimacionBotones animacionBotones;
	
	/** El semaforo, las clases internas comerySubir y comerSubiryMover 
	 * se encargan de liberarlas. */
	Semaphore semaforo;
	
	/** The panelPuntuacion que contiene los elementos que muestran las puntuaciones. */
	PanelPuntuacion panelPuntuacion;
	
	/**
	 * Instancia un nuevo panelBotones.
	 *
	 * @param fila el número de filas del tablero
	 * @param columna el número de columnas del tablero
	 * @param semaforo el semaforo
	 * @param panelPuntuacion el panel de puntuacion
	 */
	public PanelBotones(int fila,int columna,Semaphore semaforo,PanelPuntuacion panelPuntuacion){
		this.FILA = fila;
		this.COLUMNA = columna;
		this.semaforo = semaforo;
		this.panelPuntuacion = panelPuntuacion;
		animacionBotones = new AnimacionBotones();
		this.setBackground(Color.BLACK);
		this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); 
		this.setLayout(new GridLayout(fila,columna,1,1));
	}
	
	/**
	 * Agrega la matriz de botones al panel, le quita el borde
	 * y establece la fuente en blanco.
	 *
	 * @param btn la matriz de botones
	 */
	public void agregarBtn(JButton btn[][]){
		for(int i = 0; i<FILA; i++){
			for(int j = 0; j<COLUMNA; j++){
				this.add(btn[i][j]);
				btn[i][j].setBorder(null);
				btn[i][j].setFocusable(false);
				btn[i][j].setForeground(Color.WHITE);
			}
		}
	}
	
	/**
	 * Colorea la matriz de btn.
	 *
	 * @param matriz de botones
	 * @param m matriz de datos del tablero
	 */
	public void colorearBtn(JButton btn[][], int m[][]){
		for(int i = 0; i<FILA; i++){
			for(int j = 0; j<COLUMNA; j++){
				btn[i][j].setBackground(color(m[i][j]));
			}
		}
	}
	
	/**
	 * Color.
	 *
	 * @param c la representacion del color
	 * @return un objeto Color correspondiente
	 */
	Color color(int c){
		switch(c){
		case 0:
			return Color.RED;
		case 1:
			return Color.BLUE;
		case 2:
			return Color.GREEN;
		case 3:
			return Color.CYAN;
		case -1:
			return Color.BLACK;
		default:
			return Color.WHITE;
		}
	}

	/**
	 * La clase AnimacionBotones que realiza el efecto de comer fichas y
	 * "refrescar" el tablero sin eliminar columnas.
	 */
	public class AnimacionBotones implements Runnable{
		
		/** Los iteradores de las filas y columnas. */
		Iterator<Integer> f,c;
		
		/** La matriz btn y el botón para reiniciar. */
		JButton btn[][], btnReiniciar;
		
		/** La matriz de datos del tablero, los puntos comidos, 
		 * la fila y columna actual. */
		int tbl[][],puntosComidos,fil,col;
		
		/** Dos boolean para comprobar si se ha ganado o perdido
		 * la partida. */
		boolean juegoTeminado, juegoGanado;
		
		/**
		 * Instancia un nuevo comerySubir.
		 */
		public AnimacionBotones(){}
		
		/**
		 * Establece los.
		 *
		 * @param f iterador de las filas
		 * @param c iterador de las columnas
		 * @param btn la matriz de botones
		 * @param tbl la matriz de datos
		 * @param puntosComidos los puntos comidos
		 * @param fil la fila
		 * @param col la columna
		 * @param btnReiniciar el botón reiniciar
		 * @param juegoTerminado el juego terminado
		 * @param juegoGanado el juego ganado
		 */
		public void set(Iterator<Integer> f, Iterator<Integer> c, JButton btn[][],int tbl[][], int puntosComidos, int fil, int col, JButton btnReiniciar, boolean juegoTerminado, boolean juegoGanado){
			this.f = f;
			this.c = c;
			this.btn = btn;
			this.tbl = tbl;
			this.puntosComidos = puntosComidos;
			this.fil = fil;
			this.col = col;
			this.juegoTeminado = juegoTerminado;
			this.juegoGanado = juegoGanado;
			this.btnReiniciar = btnReiniciar;
		}
		
		public void run() {
			try{
				/** Mientras hayan cuadros para comer */
				while(f.hasNext()){
					int coordf = f.next();     
					int coordc = c.next();
					btn[coordf][coordc].setBackground(color(COMIDO));
				}
				
				/**  Actualizar el marcador */
				btn[fil][col].setText(String.valueOf(puntosComidos));
				panelPuntuacion.setMarcador(puntosComidos);
				
				/** Esperar 0.75 segundos */
				Thread.sleep(750);
				
				btn[fil][col].setText("");

				/** Refrescar el tablero */
				for(int i = 0; i<FILA; i++){
					for(int j = 0; j<COLUMNA; j++){
						btn[i][j].setBackground(color(tbl[i][j]));
					}
				}
				
				/** Si se ha comido toda la cuadricula */
				if(juegoGanado){
					panelPuntuacion.mostrarMensaje(btnReiniciar, 1);
					panelPuntuacion.setMarcador(1000);
					panelPuntuacion.comprobar();
				}
				
				/** Si no hay más posibilidades de comer */
				else if(juegoTeminado){
					panelPuntuacion.mostrarMensaje(btnReiniciar, 0);
					panelPuntuacion.comprobar();
				}
				
				/** Liberar el semaforo */
				semaforo.release();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
}
