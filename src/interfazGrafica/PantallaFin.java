package interfazGrafica;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import logica.Jugador;
import personalizacion.UtilidadesUI;
import java.awt.*;
import java.util.ArrayList;

public class PantallaFin extends JFrame {

    private static final long serialVersionUID = 1L;

    public PantallaFin(String nombre, int puntaje, ArrayList<Jugador> ranking, String estado) {
    	configurarVentana();
        inicializarComponentes(nombre, estado, ranking);
    }
    
    private void configurarVentana() {
    	setTitle("Histeria - Fin - Dávalos, Galván, Götz del Federico, Valdiviezo");
        setBounds(400, 100, 800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        getContentPane().setLayout(null);
    }

    private void inicializarComponentes(String nombre, String estado, ArrayList<Jugador> ranking) {
        crearEtiquetas(nombre, estado);
        crearBotones();
        crearTablaRanking(ranking);
    }

    private void crearEtiquetas(String nombre, String estado) {
        JLabel lblFinDelJuego = UtilidadesUI.crearEtiqueta("FIN DEL JUEGO", 24f, 230, 10, 310, 70, Color.YELLOW);
        getContentPane().add(lblFinDelJuego);

        JLabel lblResultado = UtilidadesUI.crearEtiqueta("¡" + estado + " " + nombre + "!", 24f, 10, 60, 766, 70, Color.YELLOW);
        getContentPane().add(lblResultado);
    }

    private void crearBotones() {
        JButton btnSalir = UtilidadesUI.crearBoton("Salir", 210, 470, 100, 40);
        btnSalir.addActionListener(e -> dispose());
        getContentPane().add(btnSalir);
        JButton btnMenu = UtilidadesUI.crearBoton("Menu Principal", 355, 470, 220, 40);
        btnMenu.addActionListener(e -> {
            dispose();
            new PantallaInicio(true).setVisible(true);
        });
        getContentPane().add(btnMenu);
    }

    private void crearTablaRanking(ArrayList<Jugador> ranking) {
        String[] columnas = {"Nombre", "Puntaje"};
        Object[][] matrizDatos = new Object[ranking.size()][2];

        for (int i = 0; i < ranking.size(); i++) {
            matrizDatos[i][0] = ranking.get(i).obtenerNombre();
            matrizDatos[i][1] = ranking.get(i).obtenerPuntajeFinal();
        }

        JTable tablaRanking = new JTable(matrizDatos, columnas);
        tablaRanking.setBackground(Color.BLACK);
        tablaRanking.setFont(UtilidadesUI.cargarFuentePersonalizada(11f));
        tablaRanking.setTableHeader(null);
        tablaRanking.setRowHeight(30);
        tablaRanking.setEnabled(false);
        tablaRanking.setShowGrid(false);
        tablaRanking.setSelectionBackground(Color.BLACK);

        DefaultTableCellRenderer columnaDerecha = new DefaultTableCellRenderer();
        columnaDerecha.setHorizontalAlignment(SwingConstants.RIGHT);
        UtilidadesUI.aplicarDegradadoConAlineaciones(tablaRanking, Color.RED, Color.YELLOW, Color.RED);

        JScrollPane panelRanking = new JScrollPane(tablaRanking);
        panelRanking.setBounds(210, 140, 348, 300);
        panelRanking.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        panelRanking.setBorder(BorderFactory.createEmptyBorder());
        getContentPane().add(panelRanking);
    }

}
