package logica;

import java.awt.Color;
import java.util.ArrayList;

public class GerenteGrilla {
	private Color[][] grilla;
	
	private static final int[][] DIRECCIONESVECINOS = { //Para tomar los vecinos de una casilla y no repetir codigo
	        {-1, 0}, {1, 0}, {0, -1}, {0, 1}
	};
	
	public GerenteGrilla(int tamano) {
		this.grilla = new Color[tamano][tamano];
		for (int i = 0; i<tamano;i++) {
			for (int j = 0; j<tamano;j++){
				grilla[i][j] = Color.GRAY;
			}
		}
	}
	
	protected int obtenerTamano() {
		return grilla.length;
	}

	protected Color obtenerColor(int fila, int columna) {
		if (posicionInvalida(fila) || posicionInvalida(columna)) 
			return null;
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
			borrarVecinos(fila,columna);
		}
	}
	
	private void borrarVecinos(int fila, int columna) {
		for(int [] dir : DIRECCIONESVECINOS) {
			int f = fila + dir [0];
			int c = columna + dir [1];
			establecerColorEnGrilla(f, c, Color.GRAY);
		}
	}

	protected int[][] obtenerPista(Color aColorear) {
	    int filaGris = -1;
	    int colGris = -1;

	    for (int fila = 0; fila < obtenerTamano(); fila++) {
	        for (int col = 0; col < obtenerTamano(); col++) {
	            if (grilla[fila][col] == Color.GRAY) {
	            	filaGris = fila;
	            	colGris = col;
	                ArrayList<Color> vecinos = obtenerColoresVecinos(fila, col);

	                if (vecinos.isEmpty()) 
	                    return new int[][] { { fila, col } };
	              
	                boolean hayCoincidencia = false;
	                for (Color vecino : vecinos) {
	                	if (mismoColor(vecino, aColorear))
	                		hayCoincidencia = true;
	                }
	                if (!hayCoincidencia) 
	                	return new int[][] { { fila, col } };
	            }
	        }
	    }
	    return new int[][] { { filaGris, colGris } };
	}

	protected boolean grillaEstaCompleta() {
		for (int fila = 0; fila < obtenerTamano(); fila++) {
			for (int columna = 0; columna < obtenerTamano(); columna++) {
				if (grilla[fila][columna] == Color.GRAY) {
					return false;
				}
			}
		}
		return true;
	}
	
	private ArrayList<Color> obtenerColoresVecinos(int fila, int col) {
	    ArrayList<Color> colores = new ArrayList<>();
	    for (int[] dir : DIRECCIONESVECINOS) {
	        int f = fila + dir[0];
	        int c = col + dir[1];
	        if (!posicionInvalida(f) && !posicionInvalida(c)) {
	            Color vecino = grilla[f][c];
	            if (vecino != null && vecino != Color.GRAY && !colores.contains(vecino)) {
	                colores.add(vecino);
	            }
	        }
	    }
	    return colores;
	}
	
	private boolean hayVecinosDelMismoColor(int fila, int columna) {
		Color actual =obtenerColor(fila, columna);
		if (actual.equals(Color.GRAY) || actual==null)
			return false;
		for(int[] dir : DIRECCIONESVECINOS) {
			int f = fila + dir[0];
			int c = columna + dir[1];
			if (!posicionInvalida(f) && !posicionInvalida(c)) {
				Color vecino = obtenerColor(f,c);
				if (vecino != null && mismoColor(actual, vecino)){
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean posicionInvalida(int posX) {
		return posX < 0 || posX >= obtenerTamano();
	}

	private boolean mismoColor(Color a, Color b) {
		return a.equals(b);
	}
	
}
