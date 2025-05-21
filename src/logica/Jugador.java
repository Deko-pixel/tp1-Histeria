package logica;

public class Jugador {
	private String nombre;
	private int puntajeInicial;
	private int puntajeFinal;
	
	public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntajeInicial = 1000;

    }
	
	public Jugador(String nombre, int puntaje) {
        this.nombre = nombre;
        this.puntajeFinal = puntaje;
    }
	
	public void calcularPuntaje(int turnos, int pistasUsadas, int tiempoTotal, int tiempoRestante) {
		this.puntajeFinal = puntajeInicial - turnos - (pistasUsadas*50) - (tiempoTotal - tiempoRestante);
	}
	
	public int obtenerPuntajeFinal() {
		return puntajeFinal;
	}
	
	public String obtenerNombre() {
		return nombre;
	}
		
}
