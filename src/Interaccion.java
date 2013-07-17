import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Interaccion{

	final int FILA,COLUMNA,COMIDO = -1;
	JButton btn[][]; 
	JButton btnReiniciar;
	Semaphore semaforo;
	Tablero tablero;
	Interfaz interfaz;
	ExecutorService ejecutor;
	PanelBotones panelBotones;
	PanelPuntuacion panelPuntuacion;

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

	public void iniciar(){
		iniciarBtn();
		panelPuntuacion.agregarBtn(btnReiniciar);
		listeners();
		panelBotones.agregarBtn(btn);
		panelBotones.colorearBtn(btn, tablero.getMat());
		interfaz.setVisible(true);
	}

	void iniciarBtn(){
		btnReiniciar = new JButton();
		btn = new JButton[FILA][COLUMNA];
		for(int i = 0; i<FILA; i++){
			for(int j = 0; j<COLUMNA; j++){
				btn[i][j] = new JButton();
			}
		}
	}

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
				panelBotones.colorearBtn(btn, tablero.getMat());
				panelPuntuacion.resetMarcador();
				btnReiniciar.setVisible(false);
			}
		}; 
		btnReiniciar.addActionListener(btnReiniciarListener);

	}

	void clic(int fil, int col){  
		try{
			semaforo.acquire();
			tablero.comer(fil,col);
			tablero.subirMat();
			tablero.moverMat();
			tablero.comprobarMat();
			if(tablero.debeMov()){
				panelBotones.comerSubiryMover.set(tablero.getCoordF(), tablero.getCoordC(), btn, tablero.getTbl(), tablero.getPuntosComidos(),fil,col,btnReiniciar,tablero.getTerminado(),tablero.getGanado());
				ejecutor.execute(panelBotones.comerSubiryMover);
			}else{
				panelBotones.comerySubir.set(tablero.getCoordF(), tablero.getCoordC(), btn, tablero.getTbl(),tablero.getPuntosComidos(),fil,col,btnReiniciar,tablero.getTerminado(),tablero.getGanado());
				ejecutor.execute(panelBotones.comerySubir);
			}
		}catch(Exception e){
			e.printStackTrace(); 
			}
	}
}