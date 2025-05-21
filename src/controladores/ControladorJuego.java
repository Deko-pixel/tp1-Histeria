package controladores;

import logica.Dificultad;
import logica.Juego;

public class ControladorJuego {
	private Juego juego;

    public ControladorJuego(Dificultad dificultad, String nombre) {
        this.juego = new Juego(dificultad, nombre);
    } 
    
    public Juego getJuego() {
        return juego;
    }
    
}
