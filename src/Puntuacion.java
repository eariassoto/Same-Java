import java.io.Serializable;

import javax.swing.JOptionPane;



public class Puntuacion implements Serializable{

	public static final long serialVersionUID = -2940309403028151430L;
	int puntuacion[];
	String nombres[];
	
	public Puntuacion(){
		puntuacion = new int[5];
		nombres = new String[5];
		for(int i=0; i<5 ;i++){
			puntuacion[i] = 0;
			nombres[i] = "";
		}
	}
	
	public boolean comprobar(int x){
		int c = 4;
		int pos = -1;
		while(c>-1 && x>puntuacion[c]){
			pos = c;
			c--;
		}
		if(pos!=-1){
			agregarPuntuacion(x,pos);
			return true;		
		}
		else{
			return false;
		}

	}

	void agregarPuntuacion(int x,int pos){

		for(int i= 3; i>=pos; i--){
			puntuacion[i+1]=puntuacion[i];
			nombres[i+1] = nombres[i];
		}
		puntuacion[pos] = x;
		String s = null;
		do{
			s = JOptionPane.showInputDialog(null, "Nueva alta puntuación ingresa tu nombre: (máx. 15 carac)", "SAME", 1);
		}while(s==null || s=="" || s.length()>16);
		s.trim();
		s.replaceAll(" ", "");
		nombres[pos] = s;
	}

	public int[] getPuntuacion(){
		return puntuacion;
	}
	public String[] getNombres(){
		return nombres;
	}
	
}
