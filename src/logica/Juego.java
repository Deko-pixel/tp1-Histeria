package logica;

import java.awt.Color;
import java.util.ArrayList;

public class Juego {
	private GerenteGrilla gerente;
	private ArrayList<Jugador> ranking;
	private Jugador jugador;
	private int turnos;
	private int cantPistasUsadas;
	private int tiempoRestante; // Tiempo en segundos
	private int tiempoTotal;
	private boolean juegoTerminado;

	public Juego(Dificultad dificultad, String nombre) {
		switch (dificultad) {
        case PRINCIPIANTE -> gerente = new GerenteGrilla(5);
        case INTERMEDIO -> gerente = new GerenteGrilla(6);
        case AVANZADO -> gerente = new GerenteGrilla(7);
        default -> throw new IllegalArgumentException("Dificultad inválida: " + dificultad);
		}
		this.ranking = new ArrayList<Jugador>(10);
		this.jugador= new Jugador(nombre);
		this.tiempoRestante = 300;
		this.juegoTerminado = false;
		//Ranking por defecto
		String nombres[] = {"Alan", "Débora", "Juan", "Leo", "Bob", "Cosme", "Alan","Débora", "Juan", "Leo" };
		int puntajes[] = {740, 733, 712, 666, 631, 574, 541, 500, 432, 321};
		for (int i = 0; i < 10; i++) {
			ranking.add(i, new Jugador(nombres[i], puntajes[i]));
		}
	}
	
	public int obtenerTamanoGrilla() {
		return gerente.obtenerTamano();
	}
	
	public Color obtenerColorEnPosicion(int fila, int columna) {
		return gerente.obtenerColor(fila, columna);
	}
	
	public int[][] obtenerPista() {
		this.cantPistasUsadas++;
		return gerente.obtenerPista();
	}
	
	public int obtenerTurnos() {
		return turnos;
	}
	
	public void actualizarTurnos() {
		this.turnos++;
	}
	
	public int obtenerTiempoRestante() {
		return this.tiempoRestante;
	}
	
	public void actualizarTiempo() {
		this.tiempoRestante--;
	}
	
	public int obtenerTiempoTotal() {
		return this.tiempoTotal;
	}
	
	public boolean terminoElJuego() {
		return this.juegoTerminado;
	}
	
	public void actualizarEstadoJuego(int fila, int columna) {
		gerente.actualizarGrilla(fila, columna, obtenerColorAleatorio());
		if(gerente.grillaEstaCompleta()) {
			juegoTerminado = true;
			calcularPuntaje();
			actualizarRanking();
		}
	}

	
	public void calcularPuntaje() {
		jugador.calcularPuntaje(turnos, cantPistasUsadas, tiempoTotal, tiempoRestante);
	}
	
	public int obtenerPuntajeJugador() {
		return jugador.obtenerPuntajeFinal();
	}
	
	public String obtenerNombreJugador() {
		return jugador.obtenerNombre();
	}
	
	public ArrayList<Jugador> obtenerRanking() {
		return this.ranking;
	}
	
	
	public void actualizarRanking() {

	    // Buscar la posición correcta
	    int posicion = 0;
	    while (posicion < ranking.size() && this.jugador.obtenerPuntajeFinal() < ranking.get(posicion).obtenerPuntajeFinal()) {
	        posicion++;
	    }

	    if (posicion <= 10) {
	    	ranking.add(posicion, this.jugador);
	    	ranking.remove(ranking.size() - 1); //Eliminar el último para conservar solo 10 siempre			
		}
	}
	
	private Color obtenerColorAleatorio() {
		Color naranja = new Color(255, 128, 0);
		Color[] colores = { Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, naranja, Color.MAGENTA };
		return colores[(int) (Math.random() * colores.length)];
	}

}
