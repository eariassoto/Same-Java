import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel; 

@SuppressWarnings("all")
public class PanelPuntuacion extends JPanel{
	
	JLabel lbl,lblMarcador,lblMensaje;
	Marcador marcador;
	final String SAME = "SAME", PUNTOS = "Puntos: ", WIN = "¡Felicidades, ganaste!", LOSE = "¡Fin del juego, perdiste!", NEW = "Nuevo Juego";
	
		public PanelPuntuacion(){
			this.setBackground(Color.WHITE);
			this.setLayout(null);
			
			marcador = new Marcador();
			
			lbl = new JLabel(SAME);
			lbl.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,48));
			lbl.setForeground(Color.BLACK);
			lbl.setBounds(30, 0, 200, 50);
			add(lbl);
			
			lblMarcador = new JLabel(PUNTOS);
			lblMarcador.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,28));
			lblMarcador.setForeground(Color.BLACK);
			lblMarcador.setBounds(5, 60, 200, 50);
			add(lblMarcador);
			
			lblMarcador = new JLabel();
			lblMarcador.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,28));
			lblMarcador.setForeground(Color.BLACK);
			lblMarcador.setBounds(105, 60, 200, 50);
			add(lblMarcador);
			
			lblMensaje = new JLabel();
			lblMensaje.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,18));
			lblMensaje.setForeground(Color.BLACK);
			lblMensaje.setBounds(5, 110, 200, 50);
			lblMensaje.setVisible(false);
			add(lblMensaje);
			
			resetMarcador();
		}
		
		public void setMarcador(int p){
			marcador.setMarcador(p);
			lblMarcador.setText(String.valueOf(marcador.getMarcador()));
		}
		
		public void resetMarcador(){
			marcador.resetMarcador();
			lblMarcador.setText(String.valueOf(marcador.getMarcador()));
			lblMensaje.setText("");
			lblMensaje.setVisible(false);
		}
		
		public void agregarBtn(JButton btn){
			btn.setBounds(85, 155, 110, 25);
			btn.setText(NEW);
			btn.setVisible(false);
			add(btn);
		}
		
		public void mostrarMensaje(JButton btn, int a){
			switch(a){
			case 0:
				lblMensaje.setText(LOSE);
				lblMensaje.setVisible(true);
				btn.setVisible(true);
				break;
			case 1:
				lblMensaje.setText(WIN);
				lblMensaje.setVisible(true);
				btn.setVisible(true);
				break;
			}
				
		}
		
		class Marcador{
			int marcador;
			void resetMarcador(){
				marcador = 0;
			}
			void setMarcador(int x){
				marcador += x;
			}
			int getMarcador(){
				return marcador;
			}
		}
	
}

