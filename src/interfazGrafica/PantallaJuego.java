package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controladores.ControladorGrilla;
import controladores.ControladorVariables;
import logica.Dificultad;
import personalizacion.UtilidadesUI;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.SwingConstants;

public class PantallaJuego extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton[][] matrizBotones;
	ControladorVariables controlesV;
	ControladorGrilla controlesG;
	private Timer timer;

	public PantallaJuego(Dificultad dificultad, String nombre) {

		controlesG = new ControladorGrilla(dificultad, nombre);
		controlesV = new ControladorVariables(controlesG.obtenerJuego()); //Asi no creamos dos objetos Juego.
//		this.tamanioGrilla = juego.obtenerTamano(); *USAR LO DE ENUM*;
		this.matrizBotones = new JButton[controlesG.obtenerTamanoGrilla()][controlesG.obtenerTamanoGrilla()];

    	setTitle("Histeria - Dificultad: "+dificultad+" - Dávalos, Galván, Götz del Federico, Valdiviezo");    	
		setBounds(400, 100, 800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.BLACK);
		getContentPane().setLayout(null);

		JPanel panelDeBotones = new JPanel();
		panelDeBotones.setBounds(220, 30, controlesG.obtenerTamanoGrilla() * 70, controlesG.obtenerTamanoGrilla() * 70);// tamanioGrilla *70
		panelDeBotones.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelDeBotones.setLayout(new GridLayout(controlesG.obtenerTamanoGrilla(), controlesG.obtenerTamanoGrilla(), 3, 3));// tamanioGrilla
		panelDeBotones.setBackground(Color.BLACK);
		getContentPane().add(panelDeBotones);

		JPanel panelPista = new JPanel();
		panelPista.setBounds(40, 170, 130, 120);
		panelPista.setBackground(Color.BLACK);
		panelPista.setLayout(null);
		panelPista.setVisible(false);
		getContentPane().add(panelPista);

		JPanel panelColorPista = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelColorPista.getLayout();
		panelColorPista.setBounds(35, 35, 60, 60);
		panelPista.add(panelColorPista);


//		JLabel etiquetaPista = new JLabel("Próximo color:");
//		etiquetaPista.setForeground(new Color(255, 255, 255));
//		etiquetaPista.setHorizontalAlignment(SwingConstants.CENTER);
//		etiquetaPista.setBounds(0, 10, 130, 20);
//		etiquetaPista.setFont(UtilidadesUI.cargarFuentePersonalizada(9f));
//		UtilidadesUI.aplicarDegradado(etiquetaPista, Color.YELLOW, Color.RED, Color.YELLOW);
//		panelPista.add(etiquetaPista);

		// Mostrar el siguiente color cuando se presione el botón "Pista"
		//JButton btnPista = UtilidadesUI.crearBoton("Pista" 40, 329, 130, 30, )
		//ESTO HAY QUE EDITARLO
		//FUNCIONA PERO SOLO ERA PARA PROBARLO, ROMPE EL SEPARATED PRESENTATION
		JButton btnPista = new JButton("Pista");
		btnPista.setFont(UtilidadesUI.cargarFuentePersonalizada(20f));
		UtilidadesUI.aplicarDegradado(btnPista, Color.YELLOW, Color.RED, Color.YELLOW);
		UtilidadesUI.personalizarBoton(btnPista);
		btnPista.addActionListener(e -> {

			int[][] posicionPista = controlesG.obtenerPista();
			if (posicionPista != null) {
			    int fila = posicionPista[0][0];
			    int col = posicionPista[0][1];
			    
			    for (int f = 0; f < matrizBotones.length; f++) {
		            for (int c = 0; c < matrizBotones[0].length; c++) {
		            	matrizBotones[f][c].setBorder(null); //
		            }
		        }
			    matrizBotones[fila][col].setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3));
		    }
			    
	    	
//			panelColorPista.setBackground(juego.obtenerColorAleatorio());
//			panelPista.setVisible(true);
		});
		btnPista.setBounds(40, 329, 130, 30);
		getContentPane().add(btnPista);
		
		

		// Panel superior para el cronómetro
		JPanel panelSuperior = new JPanel();
		panelSuperior.setBounds(40, 30, 130, 120);
		panelSuperior.setBackground(Color.BLACK);
		panelSuperior.setLayout(null);

		JLabel lblTurnos = new JLabel("Turnos: 0");
		lblTurnos.setForeground(new Color(255, 255, 255));
		lblTurnos.setBounds(0, 80, 130, 20);
		lblTurnos.setFont(UtilidadesUI.cargarFuentePersonalizada(10f));
		UtilidadesUI.aplicarDegradado(lblTurnos, Color.YELLOW, Color.RED, Color.YELLOW);
		panelSuperior.add(lblTurnos);
		
		JLabel lblTiempo = new JLabel("Tiempo: ");
		lblTiempo.setForeground(new Color(255, 255, 255));
		lblTiempo.setBounds(0, 50, 130, 20);
		lblTiempo.setFont(UtilidadesUI.cargarFuentePersonalizada(10f));
		UtilidadesUI.aplicarDegradado(lblTiempo, Color.YELLOW, Color.RED, Color.YELLOW);
		panelSuperior.add(lblTiempo);


		getContentPane().add(panelSuperior, BorderLayout.NORTH);

		JLabel lblNombre = new JLabel("");
		lblNombre.setForeground(new Color(255, 255, 255));
		lblNombre.setText(nombre);
		lblNombre.setBounds(0, 20, 130, 20);
		lblNombre.setFont(UtilidadesUI.cargarFuentePersonalizada(10f));
		UtilidadesUI.aplicarDegradado(lblNombre, Color.YELLOW, Color.RED, Color.YELLOW);
		panelSuperior.add(lblNombre);

		timer = new Timer(1000, new ActionListener() { // Timer tick por segundo
			@Override
			public void actionPerformed(ActionEvent e) {
				controlesV.actualizarTiempo();
				lblTiempo.setText(String.valueOf(formatoTiempo(controlesV.obtenerTiempoRestante())));
				if (controlesV.obtenerTiempoRestante() == 0) {
					timer.stop();
					JOptionPane.showMessageDialog(null, "¡Tiempo agotado! Has perdido.", "Fin del Juego",
							JOptionPane.WARNING_MESSAGE);
					dispose();
					PantallaFin pantallaFin = new PantallaFin(nombre, controlesV.obtenerPuntajeFinal(),
							controlesV.obtenerRanking(),"Perdiste");
					pantallaFin.setVisible(true);
				}
			}
		});

		setVisible(true);
		timer.start();

		// Llenar matriz de botones
		for (int f = 0; f < matrizBotones.length; f++) {
			for (int c = 0; c < matrizBotones.length; c++) {
				JButton boton = new JButton();
				boton.setBackground(Color.GRAY);

				int fila = f;
				int columna = c;
				boton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						controlesV.actualizarTurnos();
						lblTurnos.setText("Turnos: " + controlesV.obtenerTurnos());
						controlesG.actualizarEstadoJuego(fila, columna);

						// Verificar si esta completa la grilla
						if (controlesG.terminoElJuego()) {
							timer.stop();
							dispose();
							PantallaFin pantallaFin = new PantallaFin(nombre, controlesV.obtenerPuntajeFinal(),
									controlesV.obtenerRanking(), "Ganaste");
							pantallaFin.setVisible(true);
						}
						actualizarInterfaz();
					}
				});
				matrizBotones[f][c] = boton;
				panelDeBotones.add(boton);
			}
		}
		
		
	}

	private void actualizarInterfaz() {
		for (int fila = 0; fila < matrizBotones.length; fila++) {
			for (int columna = 0; columna < matrizBotones.length; columna++) {
				matrizBotones[fila][columna].setBackground(controlesG.obtenerColorEnPosicion(fila, columna));
				matrizBotones[fila][columna].setBorder(null); //
			}
		}
	}

	private String formatoTiempo(int tiempo) {
		int minutos = tiempo / 60;
		int segundos = tiempo % 60;
		return String.format("Tiempo: %d:%02d", minutos, segundos);
	}
}
