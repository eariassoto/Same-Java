import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import java.util.Iterator;

@SuppressWarnings("all")
public class Interfaz extends JFrame{
	
	public Interfaz(PanelBotones panelBotones,PanelPuntuacion panelPuntuacion){
		super("SAME");

		JPanel panelRoot = new JPanel();
		panelRoot.setBackground( Color.WHITE );
		panelRoot.setPreferredSize( new Dimension( 0, 100 ) );

		SpringLayout layout = new SpringLayout();
		panelRoot.setLayout(layout);

		SpringLayout.Constraints rootPaneCons = layout.getConstraints(panelRoot);
		rootPaneCons.setWidth( Spring.width( this ) );
		rootPaneCons.setHeight( Spring.height( this ) );


		panelRoot.add(panelBotones);
		SpringLayout.Constraints leftPaneCons = layout.getConstraints(panelBotones);
		leftPaneCons.setWidth( Spring.scale( rootPaneCons.getWidth(), .75f ) );
		leftPaneCons.setHeight( Spring.scale( rootPaneCons.getHeight(), 1 ) );


		panelRoot.add( panelPuntuacion );
		SpringLayout.Constraints rightPaneCons = layout.getConstraints(panelPuntuacion);
		rightPaneCons.setX( Spring.scale( rootPaneCons.getWidth(), .75f ) );
		rightPaneCons.setWidth( Spring.scale( rootPaneCons.getWidth(), .25f ) );
		rightPaneCons.setHeight( Spring.scale( rootPaneCons.getHeight(), 1 ) );

		this.add( panelRoot, BorderLayout.CENTER );
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.setResizable(false);
		this.setSize(800,600);
	}
}