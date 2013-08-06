/*
 * @author Emmanuel Arias Soto emmanuel1412@gmail.com
 */
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

// TODO: Auto-generated Javadoc
/**
 * La clase GuardarPuntuacion se encarga de guardar en
 * un archivo las mejores 5 puntuaciones registradas por
 * el juego.
 */
public class GuardarPuntuacion {
	
	/** El objeto puntuacion que va a ser usado para escribir el archivo. */
	Puntuacion puntuacion;
	
	/**
	 * Guardar.
	 *
	 * @param p La instancia de Puntuacion para ser escrita.
	 */
	public void guardar(Puntuacion p){
		this.puntuacion = p;
		try
		{
			FileOutputStream fileOut = new FileOutputStream("puntuacion.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(puntuacion);
			out.close();
			fileOut.close();
		}catch(IOException i)
		{
			i.printStackTrace();
		}
	}
}
