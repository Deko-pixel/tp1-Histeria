package controladores;

import interfazGrafica.PantallaJuego;
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

	public void clicEnCasilla(int fila, int columna, PantallaJuego vista) {
		controladorVariables.actualizarTurnos();
	    vista.actualizarTurnos(controladorVariables.obtenerTurnos());
	    controladorGrilla.actualizarEstadoJuego(fila, columna);

	    if (controladorGrilla.terminoElJuego()) {
	        vista.detenerTimer();
	        vista.mostrarFinDelJuego("Ganaste", controladorVariables.obtenerPuntajeFinal(), controladorVariables.obtenerRanking());
	    } else {
	        vista.actualizarGrilla();
	    }
	}

}
