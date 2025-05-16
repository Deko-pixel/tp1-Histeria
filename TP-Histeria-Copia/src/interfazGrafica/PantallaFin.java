package interfazGrafica;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import logica.Jugador;
import personalizacion.Estilos;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import java.awt.Font;

public class PantallaFin extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the frame.
	 * @param estado 
	 */
	public PantallaFin(String nombre, int puntaje, ArrayList<Jugador> ranking, String estado) {
		
    	setTitle("Histeria - Fin - Dávalos, Galván, Götz del Federico, Valdiviezo");    	
		getContentPane().setBackground(Color.BLACK);
		setBounds(400, 100, 800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblFinDelJuego = new JLabel("FIN DEL JUEGO");
		lblFinDelJuego.setForeground(new Color(255, 255, 255));
		lblFinDelJuego.setHorizontalAlignment(SwingConstants.CENTER);
		lblFinDelJuego.setBounds(230, 10, 310, 70);
		lblFinDelJuego.setFont(Estilos.cargarFuentePersonalizada(24f));
		Estilos.aplicarDegradado(lblFinDelJuego, Color.RED, Color.YELLOW, Color.RED);
		getContentPane().add(lblFinDelJuego);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setFont(Estilos.cargarFuentePersonalizada(14f));
		Estilos.aplicarDegradado(btnSalir, Color.RED, Color.YELLOW, Color.RED);
		Estilos.personalizarBoton(btnSalir);

		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSalir.setBounds(210, 470, 100, 40);
		getContentPane().add(btnSalir);
		
		JButton btnMenu = new JButton("Menu Principal");
		btnMenu.setFont(Estilos.cargarFuentePersonalizada(14f));
		Estilos.aplicarDegradado(btnMenu, Color.YELLOW, Color.RED, Color.YELLOW);
		Estilos.personalizarBoton(btnMenu);
		
		btnMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new PantallaInicio(true).setVisible(true);
			}
		});
		btnMenu.setBounds(355, 470, 220, 40);
		getContentPane().add(btnMenu);
		

		
		String[] columnas = {"Nombre", "Puntaje"};
		Object[][] matrizDatos = new Object[ranking.size()][2]; //Uso Object porque hay Strings y enteros

		for (int i = 0; i < ranking.size(); i++) {
			matrizDatos[i][0] = ranking.get(i).obtenerNombre();
			matrizDatos[i][1] = ranking.get(i).obtenerPuntajeFinal();
		}
		
		JTable tablaRanking = new JTable(matrizDatos, columnas);
		tablaRanking.setBackground(Color.BLACK);
		tablaRanking.setFont(Estilos.cargarFuentePersonalizada(11f));

		tablaRanking.setTableHeader(null);
		tablaRanking.setRowHeight(30);
		tablaRanking.setEnabled(false);

		DefaultTableCellRenderer columnaDerecha = new DefaultTableCellRenderer();
		columnaDerecha.setHorizontalAlignment(SwingConstants.RIGHT);
		Estilos.aplicarDegradadoConAlineaciones(tablaRanking, Color.RED, Color.YELLOW, Color.RED);
		tablaRanking.setShowGrid(false);
		tablaRanking.setSelectionBackground(Color.BLACK); // Fondo de selección

		JScrollPane panelRanking = new JScrollPane(tablaRanking);
		panelRanking.setBounds(210, 140, 348, 300);
		panelRanking.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		panelRanking.setBorder(BorderFactory.createEmptyBorder());  // Eliminar borde de JScrollPane
		getContentPane().add(panelRanking);
		
		JLabel lblResultado = new JLabel("");
		lblResultado.setForeground(new Color(255, 255, 255));
		lblResultado.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultado.setFont(Estilos.cargarFuentePersonalizada(24f));
		Estilos.aplicarDegradado(lblResultado, Color.RED, Color.YELLOW, Color.RED);
		String textoActual = lblResultado.getText();
		lblResultado.setText(textoActual + "¡"+estado+" "+nombre+"!");
		lblResultado.setBounds(10, 60, 766, 70);
		getContentPane().add(lblResultado);


		
	}
}

