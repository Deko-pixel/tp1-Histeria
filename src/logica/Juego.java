package logica;

import java.awt.Color;
import java.util.ArrayList;

public class Juego {
	private Color[][] grilla;
	private Color siguienteColor;
	private ArrayList<Jugador> ranking;

	public Juego(Dificultad dificultad) {
		switch (dificultad) {
        case PRINCIPIANTE -> this.grilla = new Color[5][5];
        case INTERMEDIO -> this.grilla = new Color[6][6];
        case AVANZADO -> this.grilla = new Color[7][7];
        default -> throw new IllegalArgumentException("Dificultad inválida: " + dificultad);
		}

		this.siguienteColor = obtenerColorAleatorio();
		this.ranking = new ArrayList<Jugador>(10);
		
		//Ranking por defecto
		String nombres[] = {"Alan", "Débora", "Juan", "Leo", "Bob", "Cosme", "Alan","Débora", "Juan", "Leo" };
		int puntajes[] = {740, 733, 712, 666, 631, 574, 541, 500, 432, 321};
		for (int i = 0; i < 10; i++) {
			ranking.add(i, new Jugador(nombres[i], puntajes[i]));
		}
		
	}

	public int obtenerTamano() {
		return this.grilla.length;
	}

	public Color obtenerColor(int fila, int columna) {
		return grilla[fila][columna];
	}

	public void establecerColorEnGrilla(int fila, int columna, Color color) {
		if (!posicionInvalida(fila) && !posicionInvalida(columna))
			grilla[fila][columna] = color;
	}

	public Color obtenerColorAleatorio() {
		Color naranja = new Color(255, 128, 0);
		Color[] colores = { Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, naranja, Color.MAGENTA };
		return colores[(int) (Math.random() * colores.length)];
	}

	public void verificarVecinos(int fila, int columna) {
		if (hayVecinosDelMismoColor(fila, columna)) {
			establecerColorEnGrilla(fila, columna, Color.GRAY);
			establecerColorEnGrilla(fila + 1, columna, Color.GRAY);
			establecerColorEnGrilla(fila - 1, columna, Color.GRAY);
			establecerColorEnGrilla(fila, columna + 1, Color.GRAY);
			establecerColorEnGrilla(fila, columna - 1, Color.GRAY);
		} else {
			establecerColorEnGrilla(fila, columna, siguienteColor);
		}

		siguienteColor = obtenerColorAleatorio();

	}

	public boolean grillaEstaCompleta() {
		for (int fila = 0; fila < obtenerTamano(); fila++) {
			for (int columna = 0; columna < obtenerTamano(); columna++) {
				if (obtenerColor(fila, columna) == Color.GRAY) {
					return false;
				}
			}
		}
		return true;
	}

	public Color obtenerSiguienteColor() {
		return this.siguienteColor;
	}

	public ArrayList<Jugador> obtenerRanking() {
		return this.ranking;
	}
	
	
	public void actualizarRanking(Jugador jugador) {

	    // Buscar la posición correcta
	    int posicion = 0;
	    while (posicion < ranking.size() && jugador.obtenerPuntajeFinal() < ranking.get(posicion).obtenerPuntajeFinal()) {
	        posicion++;
	    }

	    if (posicion <= 10) {
	    	ranking.add(posicion, jugador);
	    	ranking.remove(ranking.size() - 1); //Eliminar el último para conservar solo 10 siempre			
		}
	        
	    
	}
	
	public int[][] obtenerPista() {
	    int mejorFila = -1;
	    int mejorCol = -1;
	    int menorCantidadColoresVecinos = 6;

	    for (int fila = 0; fila < obtenerTamano(); fila++) {
	        for (int col = 0; col < obtenerTamano(); col++) {
	            if (obtenerColor(fila, col) == Color.GRAY) {
	                ArrayList<Color> coloresVecinos = obtenerColoresVecinos(fila, col);

	                if (coloresVecinos.isEmpty()) {
	                    return new int[][] { { fila, col } };
	                }

	                if (coloresVecinos.size() < menorCantidadColoresVecinos) {
	                    menorCantidadColoresVecinos = coloresVecinos.size();
	                    mejorFila = fila;
	                    mejorCol = col;
	                }
	            }
	        }
	    }

	    if (mejorFila != -1 && mejorCol != -1) {
	        return new int[][] { { mejorFila, mejorCol } };
	    }

	    return null;
	}
	
	private ArrayList<Color> obtenerColoresVecinos(int fila, int col) {
	    ArrayList<Color> colores = new ArrayList<>();
	    int[][] direcciones = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };

	    for (int[] dir : direcciones) {
	        int f = fila + dir[0];
	        int c = col + dir[1];
	        if (!posicionInvalida(f) && !posicionInvalida(c)) {
	            Color vecino = obtenerColor(f, c);
	            if (vecino != null && vecino != Color.GRAY && !colores.contains(vecino)) {
	                colores.add(vecino);
	            }
	        }
	    }
	    return colores;
	}
	
	private boolean posicionInvalida(int posX) {
		return posX < 0 || posX >= obtenerTamano();
	}

	private boolean mismoColor(Color colA, Color colB) {
		return colA.equals(colB);
	}
	
	private boolean hayVecinosDelMismoColor(int fila, int columna) {
		boolean esMismoColor = false;

		if (!posicionInvalida(fila - 1) && mismoColor(obtenerColor(fila, columna), obtenerColor(fila - 1, columna))) { // Chequea
																													// la
																													// celda
																													// superior
			esMismoColor = true;
		}
		if (!posicionInvalida(fila + 1) && mismoColor(obtenerColor(fila, columna), obtenerColor(fila + 1, columna))) { // Chequea
																													// la
																													// celda
																													// inferior
			esMismoColor = true;
		}
		if (!posicionInvalida(columna - 1) && mismoColor(obtenerColor(fila, columna), obtenerColor(fila, columna - 1))) { // Chequea
																														// la
																														// celda
																														// izquierda
			esMismoColor = true;
		}
		if ((!posicionInvalida(columna + 1) && mismoColor(obtenerColor(fila, columna), obtenerColor(fila, columna + 1)))) { // Chequea
																														// la
																														// celda
																														// derecha
			esMismoColor = true;
		}
		return esMismoColor;
	}

}
