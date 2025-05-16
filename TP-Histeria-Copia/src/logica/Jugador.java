package logica;

public class Jugador {

	private String nombre;
	private int puntajeInicial;
	private int puntajeFinal;
	private int turnos;
	private int cantPistasUsadas;
	private int tiempoRestante; // Tiempo en segundos
	private int tiempoTotal;

	
	public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntajeInicial = 1000;
		this.tiempoTotal = 300;
		this.tiempoRestante = tiempoTotal; // 5 minutos (300 segundos)

    }
	
	public Jugador(String nombre, int puntaje) {
        this.nombre = nombre;
        this.puntajeFinal = puntaje;
    }
	
	public void calcularPuntaje() {
		this.puntajeFinal = puntajeInicial - this.obtenerTurnos() - (this.cantPistasUsadas*10) - (this.tiempoTotal-this.tiempoRestante);
	}
	
	public int obtenerPuntajeFinal() {
		
		return this.puntajeFinal;
	}
	
	public String obtenerNombre() {
		return this.nombre;
	}
	
	public int obtenerTurnos() {
		return turnos;
	}
	
	public void actualizarTurnos() {
		this.turnos++;
	}
	
	public void contarPistasUsadas() {
		this.cantPistasUsadas++;
	}
	
	public int obtenerTiempoRestante() {
		return this.tiempoRestante;
	}
	
	public int obtenerTiempoTotal() {
		return this.tiempoTotal;
	}

	public void actualizarTiempo() {
		this.tiempoRestante--;

	}
		
}
