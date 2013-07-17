import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class GuardarPuntuacion {
	Puntuacion puntuacion;
	public void guardar(Puntuacion p){
		puntuacion = p;
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
