import java.util.Random;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

public class Tablero{
	int tbl[][],puntosComidos,columnaTemp[];
	final int FILA, COLUMNA, CF, CC, COMIDO = -1;
	boolean debeMover, juegoTerminado,juegoGanado;
	Iterator<Integer> mover;
	Coordenadas coordenadas;

	public Tablero(int fila, int columna,int cf,int cc){
		this.FILA = fila;
		this.COLUMNA = columna;
		this.CF = cf;
		this.CC = cc;
		coordenadas = new Coordenadas();
	}

	public void iniciarMat(){
		tbl = new int[FILA][COLUMNA];
		Random rnd = new Random();
		for(int i = 0; i<FILA; i++){
			for(int j = 0; j<COLUMNA; j++){
				tbl[i][j] = rnd.nextInt(4);
			}
		}
	}
	public int[][] getMat(){
		return tbl;
	}

	public void comer(int x, int y){
		coordenadas.limpiar();
		int cuadrosComidos = comerMat(x,y);
		puntosComidos = (cuadrosComidos * (cuadrosComidos-1)) / 2;
	}

	int comerMat(int x, int y){
		int cuadro = 1;
		coordenadas.agregar(x,y);
		int color = tbl[x][y];
		tbl[x][y]=COMIDO;
		if (x!=0 && tbl[x - 1][y] == color) {
			cuadro += comerMat(x - 1, y);
		}	
		if (x<17 && tbl[x + 1][y] == color) {
			cuadro += comerMat(x + 1, y);
		}
		if (y!=0 && tbl[x][y - 1] == color) {
			cuadro += comerMat(x, y - 1);
		}
		if (y<14 && tbl[x][y + 1] == color) {
			cuadro += comerMat(x, y + 1);
		}
		return cuadro;
	}

	public void subirMat(){ 
		for(int i = 0; i<COLUMNA; i++){
			int c = FILA-1;
			for(int j = FILA-1; j>=0; j--){
				if(tbl[j][i]!=COMIDO){
					tbl[c][i] = tbl[j][i];
					c-=1;
				}
			}
			while(c >= 0){
				tbl[c][i] = COMIDO;
				c-=1;
			}
		}
	}

	public void moverMat(){
		debeMover = false;
		for(int i = COLUMNA-1; i >= 0; i--){
			if(tbl[FILA-1][i]==COMIDO){

				for(int j = i; j < COLUMNA-1; j++){ 
					for(int k = 0;k<FILA;k++){
						tbl[k][j]=tbl[k][j+1];
					}
				}

				for(int l = 0;l<FILA;l++){
					tbl[l][COLUMNA-1]=COMIDO;
				}

				debeMover = true;
			}
		}
	}

	public void comprobarMat(){
		juegoTerminado = true;
		int controlColumna = 0, controlFila = FILA-1;
		while (juegoTerminado && controlColumna < COLUMNA-1) { 
			while (juegoTerminado && controlFila > 0) {
				if (getPos(controlFila, controlColumna) == getPos(controlFila-1, controlColumna) && getPos(controlFila, controlColumna)!=COMIDO) {
					juegoTerminado = false;
				} else if (getPos(controlFila, controlColumna) == getPos(controlFila, controlColumna+1) && getPos(controlFila, controlColumna)!=COMIDO) {
					juegoTerminado = false;
				} else {
					controlFila--;
				}
			}
			controlColumna ++;
			controlFila = FILA-1;
		}

		juegoGanado = true;
		controlColumna = 0;
		controlFila = FILA-1;
		while (juegoGanado && controlColumna < COLUMNA-1) {
			while (juegoGanado && controlFila > 0) {
				if (getPos(controlFila, controlColumna)!=COMIDO) {
					juegoGanado = false;
				} else {
					controlFila--;
				}
			}
			controlColumna ++;
			controlFila = FILA-1;
		}
	}

	public boolean puedeClic(int x, int y){
		boolean b = false;
		if(getPos(x,y)==COMIDO){
			b = false;
		}
		else if (x!=0 && getPos(x,y) == getPos(x-1,y)) {
			b = true;
		}
		else if (x<17 && getPos(x,y) == getPos(x+1,y)) {
			b = true;
		}
		else if (y!=0 && getPos(x,y) == getPos(x,y-1)) {
			b = true;
		}
		else if (y<14 && getPos(x,y) == getPos(x,y+1)) {
			b = true;
		}
		return b;
	}

	public boolean debeMov(){
		return debeMover;
	}

	public Iterator<Integer> getCoordF(){
		List<Integer> fil = new ArrayList<Integer>(coordenadas.getCoordf()); 
		Iterator<Integer> f = fil.iterator();
		return f;
	} 

	public Iterator<Integer> getCoordC(){
		List<Integer> col = new ArrayList<Integer>(coordenadas.getCoordc()); 
		Iterator<Integer> c = col.iterator();
		return c;
	}

	public int[][] getTbl(){
		return tbl;
	}

	public int getPuntosComidos(){
		return puntosComidos;
	}

	public int getColMov(){
		Iterator<Integer> control = coordenadas.getMov();
		int c = 0;
		while(control.hasNext()){
			c = control.next();
		}
		return c;
	}

	public int getPos(int x, int y){
		return tbl[x][y];
	}

	public void limpiar(){
		coordenadas.limpiar();
	}

	public boolean getTerminado(){
		return juegoTerminado;
	}

	public boolean getGanado(){
		return juegoGanado;
	}

	class Coordenadas{
		List<Integer> fil, col, colSub, colMov;
		Coordenadas(){
			fil = new ArrayList<Integer>();
			col = new ArrayList<Integer>();
			colSub = new ArrayList<Integer>();
			colMov = new ArrayList<Integer>();
		}
		void agregar(int x, int y){
			fil.add(x);
			col.add(y);
		}

		void agregar(int x){
			colMov.add(x);
		}

		List<Integer> getCoordf(){
			return fil;
		}
		List<Integer> getCoordc(){
			return col;
		}

		Iterator<Integer> getMov(){
			return colMov.iterator();
		}

		void limpiar(){
			fil.clear();
			col.clear();
			colSub.clear();
			colMov.clear();
		}
	}

}
