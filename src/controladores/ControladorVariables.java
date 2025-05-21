package controladores;

import java.util.ArrayList;
import logica.Juego;
import logica.Jugador;

public class ControladorVariables {
	private Juego juego;
	
	public ControladorVariables(Juego j) {
		this.juego = j;
	}
	
	public int obtenerTiempoRestante() {
		return juego.obtenerTiempoRestante();
	}

	public void actualizarTiempo() {
		juego.actualizarTiempo();;
	}
	
	public int obtenerTurnos() {
		return juego.obtenerTurnos();
	}

	public void actualizarTurnos() {
		juego.actualizarTurnos();
	}
	
	public String obtenerNombreJugador() {
		return juego.obtenerNombreJugador();
	}
	
	public int obtenerPuntajeFinal() {
		return juego.obtenerPuntajeJugador();
	}
	
	public ArrayList<Jugador> obtenerRanking() {
		return juego.obtenerRanking();
	}
	
}
