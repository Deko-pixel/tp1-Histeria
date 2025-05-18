package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import controladores.ControladorGrilla;
import controladores.ControladorJuego;
import controladores.ControladorVariables;
import logica.Dificultad;
import logica.Jugador;
import logica.ObservadorJuego;
import personalizacion.UtilidadesUI;

public class PantallaJuego extends JFrame implements ObservadorJuego {

    private static final long serialVersionUID = 1L;
    private JButton[][] matrizBotones;
    private ControladorVariables controlesV;
    private ControladorGrilla controlesG;
    private ControladorJuego controlesJ;
    private Timer timer;
    private JLabel lblTiempo;
    private JLabel lblTurnos;

    public PantallaJuego(Dificultad dificultad, String nombre) {
        controlesJ = new ControladorJuego(dificultad, nombre);
        controlesJ.agregarObservador(this);
        controlesG = new ControladorGrilla(controlesJ.getJuego());
        controlesV = new ControladorVariables(controlesJ.getJuego());

        configurarVentana(dificultad);
        JPanel panelDeBotones = configurarGrilla();
        crearPanelPista();
        crearPanelSuperior(nombre);
        agregarBotonPista();
        iniciarTimer(nombre);
        crearGrillaDeBotones(panelDeBotones, nombre);

        setVisible(true);
        timer.start();
    }
    
    public void marcarPosicionPista(int fila, int col) {
        for (int f = 0; f < matrizBotones.length; f++) {
            for (int c = 0; c < matrizBotones[0].length; c++) {
                matrizBotones[f][c].setBorder(null);
            }
        }
        matrizBotones[fila][col]
            .setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3));
    }
    
    public void actualizarGrilla() {
        for (int fila = 0; fila < matrizBotones.length; fila++) {
            for (int col = 0; col < matrizBotones.length; col++) {
                matrizBotones[fila][col].setBackground(
                    controlesG.obtenerColorEnPosicion(fila, col)
                );
            }
        }
    }

	public void actualizarTurnos(int turnos) {
		lblTurnos.setText("Turnos: " + turnos);
	}

	public void detenerTimer() {
		if (timer != null)
	        timer.stop();
	}

	public void mostrarFinDelJuego(String mensaje, int puntajeFinal, ArrayList<Jugador> ranking) {
		dispose();
	    new PantallaFin(controlesV.obtenerNombreJugador(), puntajeFinal, ranking, mensaje).setVisible(true);
	}
	
	@Override
	public void notificarCambio() {
		actualizarTurnos(controlesV.obtenerTurnos());
	    actualizarGrilla();
	}

	@Override
	public void notificarFinDelJuego() {
		detenerTimer();
	    mostrarFinDelJuego("Ganaste", 
	    controlesV.obtenerPuntajeFinal(), 
	    controlesV.obtenerRanking());
	}

    private void configurarVentana(Dificultad dificultad) {
        setTitle("Histeria - Dificultad: " + dificultad +" - Dávalos, Galván, Götz del Federico, Valdiviezo");
        setBounds(400, 100, 800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        getContentPane().setLayout(null);
    }

    private JPanel configurarGrilla() {
        int tamano = controlesG.obtenerTamanoGrilla();
        matrizBotones = new JButton[tamano][tamano];
        JPanel panelDeBotones = new JPanel();
        panelDeBotones.setBounds(220, 30, tamano * 70, tamano * 70);
        panelDeBotones.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panelDeBotones.setLayout(new GridLayout(tamano, tamano, 3, 3));
        panelDeBotones.setBackground(Color.BLACK);
        getContentPane().add(panelDeBotones);
        return panelDeBotones;
    }

    private void crearPanelPista() {
        JPanel panelPista = new JPanel();
        panelPista.setBounds(40, 170, 130, 120);
        panelPista.setBackground(Color.BLACK);
        panelPista.setLayout(null);
        panelPista.setVisible(false);

        JPanel panelColorPista = new JPanel();
        panelColorPista.setBounds(35, 35, 60, 60);
        panelPista.add(panelColorPista);

        getContentPane().add(panelPista);
    }

    private void crearPanelSuperior(String nombre) {
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBounds(40, 30, 130, 120);
        panelSuperior.setBackground(Color.BLACK);
        panelSuperior.setLayout(null);

        JLabel labelNombre = UtilidadesUI.crearEtiqueta(nombre, 10f, 0, 20, 130, 20, Color.YELLOW);
        lblTiempo = UtilidadesUI.crearEtiqueta("Tiempo: 0:00", 10f, 0, 50, 130, 20, Color.YELLOW);
        lblTurnos = UtilidadesUI.crearEtiqueta("Turnos: 0", 10f, 0, 80, 130, 20, Color.YELLOW);

        panelSuperior.add(labelNombre);
        panelSuperior.add(lblTiempo);
        panelSuperior.add(lblTurnos);

        getContentPane().add(panelSuperior, BorderLayout.NORTH);
    }

    private void agregarBotonPista() {
        JButton btnPista = UtilidadesUI.crearBoton("Pista", 40, 329, 130, 30);
        btnPista.addActionListener(e -> 
        { 
        	int[][] pista=controlesG.obtenerPista();
        		if (pista!=null) 
        			marcarPosicionPista(pista[0][0],pista[0][1]);
        	});
        getContentPane().add(btnPista);
    }

    private void iniciarTimer(String nombre) {
        timer = new Timer(1000, e -> {
            controlesV.actualizarTiempo();
            lblTiempo.setText(formatoTiempo(controlesV.obtenerTiempoRestante()));
            if (controlesV.obtenerTiempoRestante() == 0) {
                timer.stop();
                JOptionPane.showMessageDialog(null,
                    "¡Tiempo agotado! Has perdido.",
                    "Fin del Juego", JOptionPane.WARNING_MESSAGE);
                dispose();
                new PantallaFin(
                    nombre,
                    controlesV.obtenerPuntajeFinal(),
                    controlesV.obtenerRanking(),
                    "Perdiste"
                ).setVisible(true);
            }
        });
    }

    private void crearGrillaDeBotones(JPanel panelDeBotones, String nombre) {
        for (int f = 0; f < matrizBotones.length; f++) {
            for (int c = 0; c < matrizBotones.length; c++) {
                int fila = f, columna = c;
                JButton boton = new JButton();
                boton.setBackground(Color.GRAY);
                boton.addActionListener(e -> {
                	controlesV.actualizarTurnos();
                	controlesG.actualizarEstadoJuego(fila, columna);
                	actualizarBordes();
                });
                matrizBotones[f][c] = boton;
                panelDeBotones.add(boton);
            }
        }
    }
    
    public void actualizarBordes() {
        for (int f = 0; f < matrizBotones.length; f++) {
            for (int c = 0; c < matrizBotones[0].length; c++) {
                matrizBotones[f][c].setBorder(null);
            }
        }
    }
    
    private String formatoTiempo(int tiempo) {
        int minutos = tiempo / 60;
        int segundos = tiempo % 60;
        return String.format("Tiempo: %d:%02d", minutos, segundos);
    }
    
}

