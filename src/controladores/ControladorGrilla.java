package controladores;

import java.awt.Color;
import logica.Juego;

public class ControladorGrilla {
	private Juego juego;
	
	public ControladorGrilla(Juego juego) {
		this.juego =juego;
	}
	
	public int obtenerTamanoGrilla() {
		int tamano = juego.obtenerTamanoGrilla();
		return tamano;
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
