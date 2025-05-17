package logica;

import java.awt.Color;
import java.util.ArrayList;

public class GerenteGrilla {
	private Color[][] grilla;
	
	public GerenteGrilla(int tamano) {
		this.grilla = new Color[tamano][tamano];
		for (int i = 0; i<tamano;i++) {
			for (int j = 0; j<tamano;j++){
				this.grilla[i][j] = Color.GRAY;
				}
		}
	}
	
	protected int obtenerTamano() {
		return this.grilla.length;
	}

	protected Color obtenerColor(int fila, int columna) {
		return grilla[fila][columna];
	}

	protected void establecerColorEnGrilla(int fila, int columna, Color color) {
		if (!posicionInvalida(fila) && !posicionInvalida(columna))
			grilla[fila][columna] = color;
	}

	protected void actualizarGrilla(int fila, int columna, Color color) {
		establecerColorEnGrilla(fila, columna, color);
		if (hayVecinosDelMismoColor(fila, columna)) {
			establecerColorEnGrilla(fila, columna, Color.GRAY);
			establecerColorEnGrilla(fila + 1, columna, Color.GRAY);
			establecerColorEnGrilla(fila - 1, columna, Color.GRAY);
			establecerColorEnGrilla(fila, columna + 1, Color.GRAY);
			establecerColorEnGrilla(fila, columna - 1, Color.GRAY);
		}
	}
	
	protected int[][] obtenerPista() {
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

	protected boolean grillaEstaCompleta() {
		for (int fila = 0; fila < obtenerTamano(); fila++) {
			for (int columna = 0; columna < obtenerTamano(); columna++) {
				if (obtenerColor(fila, columna) == Color.GRAY) {
					return false;
				}
			}
		}
		return true;
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
	
	private boolean hayVecinosDelMismoColor(int fila, int columna) {
		boolean esMismoColor = false;

		if (!posicionInvalida(fila - 1) && mismoColor(obtenerColor(fila, columna), obtenerColor(fila - 1, columna))) { 
			esMismoColor = true;
		}
		if (!posicionInvalida(fila + 1) && mismoColor(obtenerColor(fila, columna), obtenerColor(fila + 1, columna))) {
			esMismoColor = true;
		}
		if (!posicionInvalida(columna - 1) && mismoColor(obtenerColor(fila, columna), obtenerColor(fila, columna - 1))) { 
			esMismoColor = true;
		}
		if ((!posicionInvalida(columna + 1) && mismoColor(obtenerColor(fila, columna), obtenerColor(fila, columna + 1)))) { 
			esMismoColor = true;
		}
		return esMismoColor;
	}
	
	private boolean posicionInvalida(int posX) {
		return posX < 0 || posX >= obtenerTamano();
	}

	private boolean mismoColor(Color colA, Color colB) {
		return colA.equals(colB);
	}
	
}
