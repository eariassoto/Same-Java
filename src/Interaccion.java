/*
 * @author Emmanuel Arias Soto emmanuel1412@gmail.com
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

// TODO: Auto-generated Javadoc
/**
 * La clase Interaccion que se encarga de manejar las interacciones entre
 * el usuario y la lógica del juego.
 */
public class Interaccion{

	/** Las constantes del numero de filas y columnas del tablero,
	 * además de la representación del color del cuadro comido. */
	final int FILA,COLUMNA,COMIDO = -1;
	
	/** La matriz btn que es la representación gráfica del tablero. */
	JButton btn[][]; 
	
	/** El botón btnreiniciar que permite crear un nuevo juego. */
	JButton btnReiniciar;
	
	/** El semaforo para controlar cuando el usuario puede hacer clic en un
	 * cuadro del tablero. */
	Semaphore semaforo;
	
	/** La clase Tablero. */
	Tablero tablero;
	
	/** La clase Interfaz. */
	Interfaz interfaz;
	
	/** El ejecutor que va a ejecutar el hilo de animación. */
	ExecutorService ejecutor;
	
	/** El panelBotones que contiene la representación grafica del tablero. */
	PanelBotones panelBotones;
	
	/** The panelPuntuacion que contiene los elementos que muestran las puntuaciones. */
	PanelPuntuacion panelPuntuacion;

	/**
	 * Instancia una nueva interaccion.
	 *
	 * @param fila el número de filas del tablero
	 * @param columna el número de columnas del tablero
	 * @param tablero el tablero
	 * @param interfaz la interfaz
	 * @param semaforo el semaforo
	 * @param panelBotones el panel de botones
	 * @param panelPuntuacion el panel de puntuacion
	 */
	public Interaccion(int fila, int columna, Tablero tablero, Interfaz interfaz, Semaphore semaforo, PanelBotones panelBotones, PanelPuntuacion panelPuntuacion){
		this.FILA = fila;
		this.COLUMNA = columna;
		this.tablero = tablero;
		this.interfaz = interfaz;
		this.semaforo = semaforo;
		this.panelBotones = panelBotones;
		this.panelPuntuacion = panelPuntuacion;
		ejecutor = Executors.newFixedThreadPool(2);
	}

	/**
	 *  Inicia las representaciones gráfica y lógica del tablero.
	 */
	public void iniciar(){
		iniciarBtn();
		panelPuntuacion.agregarBtn(btnReiniciar);
		listeners();
		panelBotones.agregarBtn(btn);
		panelBotones.colorearBtn(btn, tablero.getTbl());
		interfaz.setVisible(true);
	}

	/**
	 * Crea la matriz btn.
	 */
	void iniciarBtn(){
		btnReiniciar = new JButton();
		btn = new JButton[FILA][COLUMNA];
		for(int i = 0; i<FILA; i++){
			for(int j = 0; j<COLUMNA; j++){
				btn[i][j] = new JButton();
			}
		}
	}

	/**
	 * Agrega los escuchadores de eventos a los botnes.
	 */
	void listeners(){
		ActionListener btnListener = new ActionListener (){
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i<FILA; i++){
					for(int j = 0; j<COLUMNA; j++){
						if(e.getSource() == btn[i][j]){  
							try{
								if(semaforo.tryAcquire()){
									semaforo.release();
									if(tablero.puedeClic(i, j)){
										clic(i,j);
									}
								}
							}catch(Exception g){g.printStackTrace();}
						}
					}
				}
			}
		}; 
		for(int i = 0; i<FILA; i++){
			for(int j = 0; j<COLUMNA; j++){
				btn[i][j].addActionListener(btnListener);
			}
		}

		ActionListener btnReiniciarListener = new ActionListener (){
			public void actionPerformed(ActionEvent e) {
				tablero.iniciarMat();
				panelBotones.colorearBtn(btn, tablero.getTbl());
				panelPuntuacion.resetMarcador();
				btnReiniciar.setVisible(false);
			}
		}; 
		btnReiniciar.addActionListener(btnReiniciarListener);

	}

	/**
	 * Coordina las acciones cuando el usuario hace clic en alguno de los cuadros.
	 *
	 * @param fil la fila del cuadro 
	 * @param col la columna del cuadro
	 */
	void clic(int fil, int col){  
		try{
			semaforo.acquire();
			tablero.comer(fil,col);
			tablero.subirMat();
			tablero.moverMat();
			tablero.comprobarMat();
			panelBotones.animacionBotones.set(tablero.getCoordF(), tablero.getCoordC(), btn, tablero.getTbl(),tablero.getPuntosComidos(),fil,col,btnReiniciar,tablero.getTerminado(),tablero.getGanado());
			ejecutor.execute(panelBotones.animacionBotones);

		}catch(Exception e){
			e.printStackTrace(); 
		}
	}
}