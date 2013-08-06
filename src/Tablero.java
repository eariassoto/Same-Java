/*
 * @author Emmanuel Arias Soto emmanuel1412@gmail.com
 */
import java.util.Random;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * La clase Tablero que maneja la representación lógica del tablero.
 */
public class Tablero{
	
	/** La matriz que representa los datos del tablero, los puntos
	 * comidos y una matriz que se utiliza como una columna temporal */
	int tbl[][],puntosComidos,columnaTemp[];
	
	/** Constantes para manejar los numeros de filas, columnas y la 
	 * representación del color comido*/
	final int FILA, COLUMNA, COMIDO = -1;
	
	/** Dos boolean para comprobar si se ha ganado o perdido
	 * la partida. */
	boolean juegoTerminado,juegoGanado;
	
	/** Iterador para mover columnas de la matriz. */
	Iterator<Integer> mover;
	
	/** La clase interna coordenadas. */
	Coordenadas coordenadas;

	/**
	 * Instancia un nuevo tablero.
	 *
	 * @param fila las filas
	 * @param columna las columnas
	 */
	public Tablero(int fila, int columna){
		this.FILA = fila;
		this.COLUMNA = columna;
		coordenadas = new Coordenadas();
	}

	/**
	 * Iniciar la matriz de datos.
	 */
	public void iniciarMat(){
		tbl = new int[FILA][COLUMNA];
		Random rnd = new Random();
		for(int i = 0; i<FILA; i++){
			for(int j = 0; j<COLUMNA; j++){
				tbl[i][j] = rnd.nextInt(4);
			}
		}
	}

	/**
	 * Se encarga de comer los cuadros empezando por el
	 * indicado por los parámetros y calcular los puntos 
	 * obtenidos.
	 *
	 * @param x la coordenada x del primer cuadro
	 * @param y la coordenada y del primer cuadro
	 */
	public void comer(int x, int y){
		coordenadas.limpiar();
		int cuadrosComidos = comerMat(x,y);
		puntosComidos = (cuadrosComidos * (cuadrosComidos-1)) / 2;
	}

	/**
	 * Come los cuadros de la matriz y envia las coordenadas 
	 * de dichos cuadros a la clase coordenadas.
	 *
	 * @param x la coordenada x del primer cuadro
	 * @param y la coordenada y del primer cuadro
	 * @return el numero de cuadros que se ha comido
	 */
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

	/**
	 * Subir los cuadros de la matriz.
	 */
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

	/**
	 * Mover las columnas "vacías" de la matriz al final.
	 */
	public void moverMat(){
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
			}
		}
	}

	/**
	 * Comprobar el estado de la matriz.
	 */
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

	/**
	 * Determina si se puede hacer clic en el cuadro
	 *
	 * @param x la coordenada x del cuadro
	 * @param y la coordenada y del cuadro
	 * @return true, si se puede
	 */
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

	/**
	 * Devuelve las coordenadas de las filas.
	 *
	 * @return el iterador de las filas
	 */
	public Iterator<Integer> getCoordF(){
		List<Integer> fil = new ArrayList<Integer>(coordenadas.getCoordf()); 
		Iterator<Integer> f = fil.iterator();
		return f;
	} 

	/**
	 * Devuelve las coordenadas de las columnas
	 *
	 * @return el iterador de las columnas
	 */
	public Iterator<Integer> getCoordC(){
		List<Integer> col = new ArrayList<Integer>(coordenadas.getCoordc()); 
		Iterator<Integer> c = col.iterator();
		return c;
	}

	/**
	 * Devuelve la matriz con los datos.
	 *
	 * @return la matriz tbl
	 */
	public int[][] getTbl(){
		return tbl;
	}

	/**
	 * Devuelve los puntos comidos.
	 *
	 * @return  puntosComidos
	 */
	public int getPuntosComidos(){
		return puntosComidos;
	}

	/**
	 * Devuelve el numero de columnas que hay que mover.
	 *
	 * @return numero de columnas a mover
	 */
	public int getColMov(){
		Iterator<Integer> control = coordenadas.getMov();
		int c = 0;
		while(control.hasNext()){
			c = control.next();
		}
		return c;
	}

	/**
	 * Devuelve la información de una determinada posicion de la matriz de datos
	 *
	 * @param x coordenada x
	 * @param y coordenada y
	 * @return la información
	 */
	public int getPos(int x, int y){
		return tbl[x][y];
	}

	/**
	 * Limpiar los iteradores de la clase coordenadas.
	 */
	public void limpiar(){
		coordenadas.limpiar();
	}

	/**
	 * Devuelve el estado terminado.
	 *
	 * @return boolean terminado
	 */
	public boolean getTerminado(){
		return juegoTerminado;
	}

	/**
	 * Devuelve el estado ganado.
	 *
	 * @return boolean ganado
	 */
	public boolean getGanado(){
		return juegoGanado;
	}

	/**
	 * La clase interna Coordenadas que se encarga de manejar
	 * las coordenadas que hay que comer para la representación
	 * gráfica del tablero.
	 */
	class Coordenadas{
		
		/** Los iteradores. */
		List<Integer> fil, col, colSub, colMov;
		
		/**
		 * Instancia un nuevo coordenadas.
		 */
		Coordenadas(){
			fil = new ArrayList<Integer>();
			col = new ArrayList<Integer>();
			colSub = new ArrayList<Integer>();
			colMov = new ArrayList<Integer>();
		}
		
		/**
		 * Agregar una coordenada.
		 *
		 * @param x coordenada x
		 * @param y coordenada y
		 */
		void agregar(int x, int y){
			fil.add(x);
			col.add(y);
		}

		/**
		 * Agregar una columna para mover.
		 *
		 * @param x la columna
		 */
		void agregar(int x){
			colMov.add(x);
		}

		/**
		 * Devuelve el iterador de las filas.
		 *
		 * @return iterador filas
		 */
		List<Integer> getCoordf(){
			return fil;
		}
		
		/**
		 * Devuelve el iterador de las columnas
		 *
		 * @return iterador columnas
		 */
		List<Integer> getCoordc(){
			return col;
		}

		/**
		 * Devuelve el iterador de las columnas.
		 *
		 * @return las columnas
		 */
		Iterator<Integer> getMov(){
			return colMov.iterator();
		}

		/**
		 * Limpiar los iteradores.
		 */
		void limpiar(){
			fil.clear();
			col.clear();
			colSub.clear();
			colMov.clear();
		}
	}

}
