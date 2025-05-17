package controladores;

import java.awt.Color;
import interfazGrafica.PantallaJuego;
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

	public void marcarPistaEnVista(PantallaJuego vista) {
		int [][]posicion= juego.obtenerPista();
		if(posicion!=null) {
			vista.marcarPosicionPista(posicion[0][0],posicion[0][1]);
		}	
	}

	public boolean terminoElJuego() {
		return juego.terminoElJuego();
	}
	
}
