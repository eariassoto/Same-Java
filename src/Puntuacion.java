/*
 * @author Emmanuel Arias Soto emmanuel1412@gmail.com
 */
import java.io.Serializable;

import javax.swing.JOptionPane;



// TODO: Auto-generated Javadoc
/**
 * La clase Puntuacion que es utilizada para serializar en un archivo, esta
 * contiene las mejores puntuaciones.
 */
public class Puntuacion implements Serializable{

	/** La constante serialVersionUID. */
	public static final long serialVersionUID = -2940309403028151430L;
	
	/** La matriz que contiene las puntuaciones */
	int puntuaciones[];
	
	/** La matriz que contiene los nombres. */
	String nombres[];
	
	/**
	 * Instancia un nuevo puntuacion.
	 */
	public Puntuacion(){
		puntuaciones = new int[5];
		nombres = new String[5];
		for(int i=0; i<5 ;i++){
			puntuaciones[i] = 0;
			nombres[i] = "";
		}
	}
	
	/**
	 * Comprobar si la puntuacion está entre las mejores.
	 *
	 * @param p la puntuacion
	 * @return true, si se cumple
	 */
	public boolean comprobar(int p){
		int c = 4;
		int pos = -1;
		while(c>-1 && p>puntuaciones[c]){
			pos = c;
			c--;
		}
		if(pos!=-1){
			agregarPuntuacion(p,pos);
			return true;		
		}
		else{
			return false;
		}

	}

	/**
	 * Agregar puntuacion.
	 *
	 * @param p la puntuacion
	 * @param pos the pos
	 */
	void agregarPuntuacion(int p,int pos){

		for(int i= 3; i>=pos; i--){
			puntuaciones[i+1]=puntuaciones[i];
			nombres[i+1] = nombres[i];
		}
		puntuaciones[pos] = p;
		String s = null;
		do{
			s = JOptionPane.showInputDialog(null, "Nueva alta puntuación ingresa tu nombre: (máx. 15 carac)", "SAME", 1);
		}while(s==null || s=="" || s.length()>16);
		s.trim();
		s.replaceAll(" ", "");
		nombres[pos] = s;
	}

	/**
	 * Devuelve las puntuaciones.
	 *
	 * @return la matriz con las puntuaciones
	 */
	public int[] getPuntuacion(){
		return puntuaciones;
	}
	
	/**
	 * Devuelve los nombres.
	 *
	 * @return la matriz con los nombres
	 */
	public String[] getNombres(){
		return nombres;
	}
}
