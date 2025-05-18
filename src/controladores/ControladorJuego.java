package controladores;

import logica.Dificultad;
import logica.Juego;

public class ControladorJuego {
	private Juego juego;
    private ControladorGrilla controladorGrilla;
    private ControladorVariables controladorVariables;

    public ControladorJuego(Dificultad dificultad, String nombre) {
        this.juego = new Juego(dificultad, nombre);
        this.controladorGrilla = new ControladorGrilla(juego);
        this.controladorVariables = new ControladorVariables(juego);
    }

    public ControladorGrilla getControladorGrilla() {
        return controladorGrilla;
    }

    public ControladorVariables getControladorVariables() {
        return controladorVariables;
    }
    
    public Juego getJuego() {
        return juego;
    }

	public void clicEnCasilla(int fila, int columna) {
		controladorVariables.actualizarTurnos();
	    controladorGrilla.actualizarEstadoJuego(fila, columna);
	}

}
