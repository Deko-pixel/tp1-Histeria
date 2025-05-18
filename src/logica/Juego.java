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
	private ArrayList<ObservadorJuego> observadores = new ArrayList<>();
	private Color siguienteColor;

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
		this.siguienteColor = obtenerColorAleatorio();
		//Ranking por defecto
		String nombres[] = {"Alan", "Débora", "Juan", "Leo", "Bob", "Cosme", "Alan","Débora", "Juan", "Leo" };
		int puntajes[] = {740, 733, 712, 666, 631, 574, 541, 500, 432, 321};
		for (int i = 0; i < 10; i++) {
			ranking.add(i, new Jugador(nombres[i], puntajes[i]));
		}
	}
	
	public void agregarObservador(ObservadorJuego obs) {
	    observadores.add(obs);
	}

	public void eliminarObservador(ObservadorJuego obs) {
	    observadores.remove(obs);
	}

	private void notificarObservadores() {
	    for (ObservadorJuego o : observadores) {
	        o.notificarCambio();
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
		return gerente.obtenerPista(siguienteColor);
	}
	
	public int obtenerTurnos() {
		return turnos;
	}
	
	public void actualizarTurnos() {
		this.turnos++;
		notificarObservadores();
	}
	
	public int obtenerTiempoRestante() {
		return this.tiempoRestante;
	}
	
	public void actualizarTiempo() {
		this.tiempoRestante--;
		notificarObservadores();
	}
	
	public int obtenerTiempoTotal() {
		return this.tiempoTotal;
	}
	
	public boolean terminoElJuego() {
		return this.juegoTerminado;
	}
	
	public void actualizarEstadoJuego(int fila, int columna) {
		gerente.actualizarGrilla(fila, columna, siguienteColor);
		if(gerente.grillaEstaCompleta()) {
			juegoTerminado = true;
			calcularPuntaje();
			actualizarRanking();
			notificarFinDelJuego();
		}
		notificarObservadores();
		siguienteColor = obtenerColorAleatorio();
	}

	
	private void notificarFinDelJuego() {
		for(ObservadorJuego o: observadores) {
			o.notificarFinDelJuego();
		}
	}

	public void calcularPuntaje() {
		jugador.calcularPuntaje(turnos, cantPistasUsadas, tiempoTotal, tiempoRestante);
		System.out.println(cantPistasUsadas);
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
