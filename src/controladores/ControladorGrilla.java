package controladores;

import java.awt.Color;

import logica.Dificultad;
import logica.Juego;

public class ControladorGrilla {
	private Juego juego;
	
	public ControladorGrilla(Dificultad dificultad, String nombre) {
		juego = new Juego(dificultad, nombre);
	}
	
	public Juego obtenerJuego() {
		return juego;
	}
	
	public int obtenerTamanoGrilla() {
		return juego.obtenerTamanoGrilla();
	}

	public Color obtenerColorEnPosicion(int fila, int columna) {
		return juego.obtenerColorEnPosicion(fila, columna);
	}


	public void actualizarEstadoJuego(int fila, int columna) {
		juego.actualizarEstadoJuego(fila, columna);
		
	}

	public int[][] obtenerPista() {
		return juego.obtenerPista();
	}

	public boolean terminoElJuego() {
		return juego.terminoElJuego();
	}
}
