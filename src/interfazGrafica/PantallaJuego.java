package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.Dificultad;
import logica.Juego;
import logica.Jugador;
import personalizacion.UtilidadesUI;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.SwingConstants;

public class PantallaJuego extends JFrame {

	private static final long serialVersionUID = 1L;
	private Color siguienteColor; // Nuevo color para la pista
	private JButton[][] matrizBotones;
	private int tamanioGrilla;
	private Juego juego;
	private Jugador jugador;
	private JLabel lblTiempo;
	private Timer timer;

	public PantallaJuego(Dificultad dificultad, String nombre) {

		this.juego = new Juego(dificultad);
		this.jugador = new Jugador(nombre);
		this.tamanioGrilla = juego.obtenerTamano(); // Usa el tamaño del juego
		this.matrizBotones = new JButton[tamanioGrilla][tamanioGrilla];
		this.siguienteColor = juego.obtenerSiguienteColor(); // Establecer el siguiente color

    	setTitle("Histeria - Dificultad: "+dificultad+" - Dávalos, Galván, Götz del Federico, Valdiviezo");    	
		setBounds(400, 100, 800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.BLACK);
		getContentPane().setLayout(null);

		JPanel panelDeBotones = new JPanel();
		panelDeBotones.setBounds(220, 30, tamanioGrilla * 70, tamanioGrilla * 70);// tamanioGrilla *70
		panelDeBotones.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelDeBotones.setLayout(new GridLayout(tamanioGrilla, tamanioGrilla, 3, 3));// tamanioGrilla
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
		panelColorPista.setBackground(siguienteColor);

		JLabel etiquetaPista = new JLabel("Próximo color:");
		etiquetaPista.setForeground(new Color(255, 255, 255));
		etiquetaPista.setHorizontalAlignment(SwingConstants.CENTER);
		etiquetaPista.setBounds(0, 10, 130, 20);
		etiquetaPista.setFont(UtilidadesUI.cargarFuentePersonalizada(9f));
		UtilidadesUI.aplicarDegradado(etiquetaPista, Color.YELLOW, Color.RED, Color.YELLOW);
		panelPista.add(etiquetaPista);

		// Mostrar el siguiente color cuando se presione el botón "Pista"
		JButton btnPista = new JButton("Pista");
		btnPista.setFont(UtilidadesUI.cargarFuentePersonalizada(20f));
		UtilidadesUI.aplicarDegradado(btnPista, Color.YELLOW, Color.RED, Color.YELLOW);
		UtilidadesUI.personalizarBoton(btnPista);
		btnPista.addActionListener(e -> {
			if (!panelPista.isVisible()) {
				jugador.contarPistasUsadas();
			}
			panelColorPista.setBackground(siguienteColor); // Actualizar el color de la pista
			panelPista.setVisible(true); // Mostrar el panel con la pista
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
				jugador.actualizarTiempo();
				lblTiempo.setText(String.valueOf(formatoTiempo(jugador.obtenerTiempoRestante())));
				if (jugador.obtenerTiempoRestante() == 0) {
					timer.stop();
					JOptionPane.showMessageDialog(null, "¡Tiempo agotado! Has perdido.", "Fin del Juego",
							JOptionPane.WARNING_MESSAGE);
					dispose();
					PantallaFin pantallaFin = new PantallaFin(nombre, jugador.obtenerPuntajeFinal(),
							juego.obtenerRanking(),"Perdiste");
					pantallaFin.setVisible(true);
				}
			}
		});

		setVisible(true);
		timer.start();

		// Llenar matriz de botones
		for (int f = 0; f < tamanioGrilla; f++) {
			for (int c = 0; c < tamanioGrilla; c++) {
				JButton boton = new JButton();
				boton.setBackground(Color.GRAY);
				juego.establecerColorEnGrilla(f, c, Color.GRAY);

				int fila = f;
				int columna = c;
				boton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						jugador.actualizarTurnos();
						lblTurnos.setText("Turnos: " + jugador.obtenerTurnos());
						juego.establecerColorEnGrilla(fila, columna, siguienteColor);

						// Verificar coincidencias
						juego.verificarVecinos(fila, columna);
						actualizarInterfaz();

						// Actualizar el siguiente color
						siguienteColor = juego.obtenerSiguienteColor(); // Establecer el siguiente color
						panelColorPista.setBackground(siguienteColor); // Actualizar el panel de pista
						panelPista.setVisible(false); // Ocultar la pista después de hacer un clic

						// Verificar si esta completa la grilla
						if (juego.grillaEstaCompleta()) {
							timer.stop();
							dispose();
							jugador.calcularPuntaje();
							juego.actualizarRanking(jugador);
							PantallaFin pantallaFin = new PantallaFin(nombre, jugador.obtenerPuntajeFinal(),
									juego.obtenerRanking(), "Ganaste");
							pantallaFin.setVisible(true);
						}
					}
				});
				matrizBotones[f][c] = boton;
				panelDeBotones.add(boton);
			}
		}
		
		
	}

	private void actualizarInterfaz() {
		for (int fila = 0; fila < tamanioGrilla; fila++) {
			for (int columna = 0; columna < tamanioGrilla; columna++) {
				matrizBotones[fila][columna].setBackground(juego.obtenerColor(fila, columna));
			}
		}
	}

	private String formatoTiempo(int tiempo) {
		int minutos = tiempo / 60;
		int segundos = tiempo % 60;
		return String.format("Tiempo: %d:%02d", minutos, segundos);
	}
}
