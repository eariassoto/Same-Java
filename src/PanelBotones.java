import java.awt.Color;
import java.awt.GridLayout;
import java.util.Iterator;
import java.util.concurrent.Semaphore;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("all")
public class PanelBotones extends JPanel{
	final int FILA,COLUMNA,COMIDO = -1;
	ComerySubir comerySubir;
	ComerSubiryMover comerSubiryMover;
	Semaphore semaforo;
	PanelPuntuacion panelPuntuacion;
	
	public PanelBotones(int fila,int columna,Semaphore semaforo,PanelPuntuacion panelPuntuacion){
		this.FILA = fila;
		this.COLUMNA = columna;
		this.semaforo = semaforo;
		this.panelPuntuacion = panelPuntuacion;
		comerySubir = new ComerySubir();
		comerSubiryMover = new ComerSubiryMover();
		this.setBackground(Color.BLACK);
		this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); 
		this.setLayout(new GridLayout(fila,columna,1,1));
	}
	
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
	
	public void colorearBtn(JButton btn[][], int m[][]){
		for(int i = 0; i<FILA; i++){
			for(int j = 0; j<COLUMNA; j++){
				btn[i][j].setBackground(color(m[i][j]));
			}
		}
	}
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

	public class ComerySubir implements Runnable{
		Iterator<Integer> f,c;
		JButton btn[][], btnReiniciar;
		int tbl[][],puntosComidos,fil,col;
		boolean juegoTeminado, juegoGanado;
		public ComerySubir(){}
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
				while(f.hasNext()){
					int coordf = f.next();     
					int coordc = c.next();
					btn[coordf][coordc].setBackground(color(COMIDO));
				}

				btn[fil][col].setText(String.valueOf(puntosComidos));
				panelPuntuacion.setMarcador(puntosComidos);
				Thread.sleep(750);
				btn[fil][col].setText("");

				for(int i = 0; i<FILA; i++){
					for(int j = 0; j<COLUMNA; j++){
						btn[i][j].setBackground(color(tbl[i][j]));
					}
				}
				
				if(juegoGanado){
					panelPuntuacion.mostrarMensaje(btnReiniciar, 1);
				}
				else if(juegoTeminado){
					panelPuntuacion.mostrarMensaje(btnReiniciar, 0);
				}
				
				
				semaforo.release();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}

	public class ComerSubiryMover implements Runnable{
		Iterator<Integer> f,c;
		JButton btn[][], btnReiniciar;
		int tblMov[][];
		int limite,puntosComidos,fil,col;
		boolean juegoTeminado, juegoGanado;
		public ComerSubiryMover(){}
		public void set(Iterator<Integer> f, Iterator<Integer> c, JButton btn[][], int tblMov[][], int puntosComidos, int fil, int col, JButton btnReiniciar, boolean juegoTerminado, boolean juegoGanado){
			this.f = f;
			this.c = c;
			this.btn = btn;
			this.tblMov = tblMov;
			this.puntosComidos = puntosComidos;
			this.fil = fil;
			this.col = col;
			this.juegoTeminado = juegoTerminado;
			this.juegoGanado = juegoGanado;
			this.btnReiniciar = btnReiniciar;
		}
		public void run() {
			try{
				while(f.hasNext()){
					int coordf = f.next();     
					int coordc = c.next();
					btn[coordf][coordc].setBackground(color(COMIDO));
				}

				btn[fil][col].setText(String.valueOf(puntosComidos));
				panelPuntuacion.setMarcador(puntosComidos);
				Thread.sleep(750);
				btn[fil][col].setText("");

				for(int i = 0; i<FILA; i++){
					for(int j = 0; j<COLUMNA; j++){
						btn[i][j].setBackground(color(tblMov[i][j]));
					}
				}
				
				if(juegoGanado){
					panelPuntuacion.mostrarMensaje(btnReiniciar, 1);
				}
				else if(juegoTeminado){
					panelPuntuacion.mostrarMensaje(btnReiniciar, 0);
				}

				semaforo.release();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
}
