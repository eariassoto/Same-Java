/*
 * @author Emmanuel Arias Soto emmanuel1412@gmail.com
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


// TODO: Auto-generated Javadoc
/**
 * La clase LeerPuntuacion se encarga de leer
 * el archivo y devolver un objeto Puntuacion 
 * o de no existir crear uno nuevo.
 */
public class LeerPuntuacion {

	/** El objeto puntuacion que luego va a ser devuelto con la info del archivo. */
	Puntuacion puntuacion;
	
	/** Por si es necesario crear un archivo nuevo. */
	GuardarPuntuacion guardarPuntuacion;
	
	/**
	 * Instancia una nueva puntuacion.
	 *
	 * @param guardarPuntuacion el guardarPuntuacion
	 */
	public LeerPuntuacion(GuardarPuntuacion guardarPuntuacion){
		puntuacion = null;
		this.guardarPuntuacion = guardarPuntuacion;
	}
	
	/**
	 * De no existir el archivo, crear uno nuevo.
	 */
	public void puntuacionExiste(){
		File f = new File("puntuacion.ser");
		if(f.exists()!=true) { 
			guardarPuntuacion.guardar(new Puntuacion());
		} 
	}
	
	/**
	 * Se encarga de leer el archivo.
	 *
	 * @return una puntuacion con los datos almacenados
	 */
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
