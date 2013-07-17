import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


public class LeerPuntuacion {

	Puntuacion puntuacion;
	GuardarPuntuacion guardarPuntuacion;
	public LeerPuntuacion(GuardarPuntuacion guardarPuntuacion){
		puntuacion = null;
		this.guardarPuntuacion = guardarPuntuacion;
	}
	
	public void puntuacionExiste(){
		File f = new File("puntuacion.ser");
		if(f.exists()!=true) { 
			guardarPuntuacion.guardar(new Puntuacion());
		} 
	}
	
	public Puntuacion leer(){
		try
	      {
	         FileInputStream fileIn = new FileInputStream("puntuacion.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         puntuacion = (Puntuacion) in.readObject();
	         in.close();
	         fileIn.close();
	         
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("Clase no encontrada");
	         c.printStackTrace();
	      }
		return puntuacion;
	}
}
