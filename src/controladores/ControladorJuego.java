package controladores;

import logica.Dificultad;
import logica.Juego;
import logica.ObservadorJuego;

public class ControladorJuego {
	private Juego juego;

    public ControladorJuego(Dificultad dificultad, String nombre) {
        this.juego = new Juego(dificultad, nombre);
    }
    
    public Juego getJuego() {
        return juego;
    }
    
    public void agregarObservador(ObservadorJuego o) {
    	juego.agregarObservador(o);
    }
    
}
