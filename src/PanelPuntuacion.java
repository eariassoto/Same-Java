/*
 * @author Emmanuel Arias Soto emmanuel1412@gmail.com
 */
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel; 
import javax.swing.JTextArea;

// TODO: Auto-generated Javadoc
/**
 * La clase PanelPuntuacion que contienen las atiquetas que muestran
 * al usuario su puntuación actual y las mejores puntuaciones, además
 * del botón para iniciar un nuevo juego.
 */
@SuppressWarnings("all")
public class PanelPuntuacion extends JPanel{
	
	/** Los label que van a mostrar la información. */
	JLabel lbl,lblMarcador,lblMensaje;
	
	/** La matriz que se usa para almacenar las mejores puntuaciones. */
	int p[];
	
	/** Un string para mostrar mensajes y una matriz con los nombres de 
	 * las mejores puntuaciones. */
	String mensaje, n[];
	
	/** Area de texto para mostrar la tabla de puntuaciones. */
	JTextArea txtPuntuacion;
	
	/** La clase interna marcador. */
	Marcador marcador;
	
	/** El objeto puntuacion. */
	Puntuacion puntuacion;
	
	/** guardarPuntuacion. */
	GuardarPuntuacion guardarPuntuacion;
	
	/** Mensajes necesarios. */
	final String SAME = "SAME", PUNTOS = "Puntos: ", WIN = "¡Felicidades, ganaste!", LOSE = "¡Fin del juego, perdiste!", NEW = "Nuevo Juego";
	
		/**
		 * Instancia un nuevo panelPuntuacion.
		 *
		 * @param puntuacion el objeto puntuacion
		 * @param guardarPuntuacion el objeto guardarPuntuacion
		 */
		public PanelPuntuacion(Puntuacion puntuacion,GuardarPuntuacion guardarPuntuacion){
			this.setBackground(Color.WHITE);
			this.setLayout(null);
			this.puntuacion = puntuacion;
			this.guardarPuntuacion = guardarPuntuacion;
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
			
			txtPuntuacion = new JTextArea();
			txtPuntuacion.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,15));
			txtPuntuacion.setEditable(false);
			txtPuntuacion.setSize(150, 150);
			txtPuntuacion.setBackground(Color.LIGHT_GRAY);
			txtPuntuacion.setBounds(5, 200, 185, 125);
			txtPuntuacion.setVisible(true);
			add(txtPuntuacion);
			
			resetMarcador();
			setPuntuacion();
		}
		
		/**
		 * Establece el marcador.
		 *
		 * @param p the new marcador
		 */
		public void setMarcador(int p){
			marcador.setMarcador(p);
			lblMarcador.setText(String.valueOf(marcador.getMarcador()));
		}
		
		/**
		 * Resetea el marcador.
		 */
		public void resetMarcador(){
			marcador.resetMarcador();
			lblMarcador.setText(String.valueOf(marcador.getMarcador()));
			lblMensaje.setText("");
			lblMensaje.setVisible(false);
		}
		
		/**
		 * Agrega el boton al panel.
		 *
		 * @param btn el botón
		 */
		public void agregarBtn(JButton btn){
			btn.setBounds(85, 155, 110, 25);
			btn.setText(NEW);
			btn.setVisible(false);
			this.add(btn);
		}
		
		/**
		 * Mostrar mensaje.
		 *
		 * @param btn el botón
		 * @param a la opción del mensaje
		 */
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
		
		/**
		 * Escribe las puntuaciones en el area de texto.
		 */
		public void setPuntuacion(){
			p = puntuacion.getPuntuacion();
			n = puntuacion.getNombres();
			mensaje = "Mejores Puntuaciones:\n";
			for(int i = 0; i<5;i++){
				if(p[i]!=0){
					mensaje += String.valueOf(i+1) + "- "+n[i]+" "+p[i]+"\n";
				}
				else{
					mensaje += String.valueOf(i+1) + "-\n";
				}
			}
			txtPuntuacion.setText(mensaje);
		}
		
		/**
		 * Comprobar si la puntuación está entre las mejores.
		 */
		public void comprobar(){
			if(puntuacion.comprobar(marcador.getMarcador())){
				guardarPuntuacion.guardar(puntuacion);
				setPuntuacion();
			}
		}
		
		/**
		 * La clase interna Marcador que sirve para manejar
		 * la puntuacion actual del usuario.
		 */
		class Marcador{

			/** El marcador. */
			int marcador;

			/**
			 * Resetear marcador.
			 */
			void resetMarcador(){
				marcador = 0;
			}

			/**
			 * Establece el marcador.
			 *
			 * @param x el nuevo marcador
			 */
			void setMarcador(int x){
				marcador += x;
			}

			/**
			 * Devuelve el marcador.
			 *
			 * @return el marcador
			 */
			int getMarcador(){
				return marcador;
			}
		}
}

