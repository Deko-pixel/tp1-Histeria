package interfazGrafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import personalizacion.UtilidadesUI;
import logica.Dificultad;

public class PantallaInicio extends JFrame {
    private static final long serialVersionUID = 1L;
    private JComboBox<Dificultad> comboBoxDificultad;
    private JTextField textFieldNombre;
    private JPanel panelInicio, panelDatos;

    public PantallaInicio(boolean mostrarDatos) {
        configurarVentana();
        inicializarComponentes();
        configurarPanelInicio();
        configurarPanelDatos();
        mostrarPanel(mostrarDatos);
    }

    private void configurarVentana() {
        setTitle("Histeria - Dávalos, Galván, Götz del Federico, Valdiviezo");
        setBounds(400, 100, 500, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        getContentPane().setLayout(null);
    }

    private void inicializarComponentes() {
        comboBoxDificultad = new JComboBox<>(Dificultad.values());
        comboBoxDificultad.insertItemAt(null, 0);
        comboBoxDificultad.setSelectedIndex(0);
        comboBoxDificultad.setFont(UtilidadesUI.cargarFuentePersonalizada(10f));
        comboBoxDificultad.setBackground(Color.BLACK);
        comboBoxDificultad.setForeground(Color.YELLOW);
    }

    private void configurarPanelInicio() {
        panelInicio = new JPanel(null);
        panelInicio.setBounds(0, 0, 500, 500);
        panelInicio.setBackground(Color.BLACK);

        JLabel lblBienvenido = UtilidadesUI.crearEtiqueta("¡Bienvenido!", 24f, 100, 10, 300, 50, Color.YELLOW);
        JLabel lblTitulo = UtilidadesUI.crearEtiqueta("Un nuevo reto te espera", 20f, 10, 48, 480, 50, Color.YELLOW);
        JLabel lblListo = UtilidadesUI.crearEtiqueta("¿Estas Listo?", 13f, 165, 405, 170, 40, Color.YELLOW);

        JLabel gifLabel = new JLabel(new ImageIcon(getClass().getResource("/interfazGrafica/GIFHisteria.gif")));
        gifLabel.setBounds(100, 95, 300, 300);
        panelInicio.add(gifLabel);

        JButton btnSi = UtilidadesUI.crearBoton("Si", 125, 450, 60, 40);
        btnSi.addActionListener(e -> mostrarPanel(true));
        JButton btnNo = UtilidadesUI.crearBoton("No", 325, 450, 60, 40);
        btnNo.addActionListener(e -> dispose());

        panelInicio.add(lblBienvenido);
        panelInicio.add(lblTitulo);
        panelInicio.add(lblListo);
        panelInicio.add(btnSi);
        panelInicio.add(btnNo);
        getContentPane().add(panelInicio);
    }

    private void configurarPanelDatos() {
        panelDatos = new JPanel(null);
        panelDatos.setBounds(0, 0, 500, 500);
        panelDatos.setBackground(Color.BLACK);
        getContentPane().add(panelDatos);

        textFieldNombre = new JTextField("Nombre o Apodo");
        textFieldNombre.setBounds(300, 271, 160, 40);
        textFieldNombre.setFont(UtilidadesUI.cargarFuentePersonalizada(10f));
        textFieldNombre.setBackground(Color.BLACK);
        textFieldNombre.setForeground(Color.YELLOW);
        UtilidadesUI.personalizarCursor(textFieldNombre, Color.YELLOW, 7);
        agregarListenersTextField();

        JLabel lblNombre = UtilidadesUI.crearEtiqueta("Ingrese su nombre", 10f, 20, 270, 190, 40, Color.YELLOW);
        JLabel lblDificultad = UtilidadesUI.crearEtiqueta("Seleccione la dificultad", 10f, 20, 170, 270, 40, Color.YELLOW);
        JLabel lblHisteria = UtilidadesUI.crearEtiqueta("Histeria", 40f, 84, 34, 350, 90, Color.YELLOW);

        comboBoxDificultad.setBounds(300, 170, 160, 40);
        panelDatos.add(comboBoxDificultad);

        JButton btnComenzar = UtilidadesUI.crearBoton("Comenzar", 300, 350, 160, 40);
        btnComenzar.addActionListener(e -> { accionComenzar(e); });
        JButton btnComoJugar = UtilidadesUI.crearBoton("Cómo jugar", 20, 350, 170, 40);
        btnComoJugar.addActionListener(e -> {accionComoJugar(e); });
        JButton btnSalir = UtilidadesUI.crearBoton("Salir", 200, 420, 90, 40);
        btnSalir.addActionListener(e -> dispose());

        panelDatos.add(textFieldNombre);
        panelDatos.add(lblNombre);
        panelDatos.add(lblDificultad);
        panelDatos.add(lblHisteria);
        panelDatos.add(btnComenzar);
        panelDatos.add(btnComoJugar);
        panelDatos.add(btnSalir);
    }

    private void agregarListenersTextField() {
        textFieldNombre.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { verificarNombre(); }
            public void removeUpdate(DocumentEvent e) { verificarNombre(); }
            public void insertUpdate(DocumentEvent e) { verificarNombre(); }
        });

        textFieldNombre.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (textFieldNombre.getText().equals("Nombre o Apodo")) {
                    textFieldNombre.setText("");
                }
            }
            public void focusLost(FocusEvent evt) {
                if (textFieldNombre.getText().isEmpty()) {
                    textFieldNombre.setText("Nombre o Apodo");
                }
            }
        });
    }

    private void verificarNombre() {
        String nombre = textFieldNombre.getText();
        if (nombre.length() > 12 && !nombre.equals("Nombre o Apodo") && !nombre.isEmpty()) {
            UtilidadesUI.mostrarMensaje("Nombre o apodo demasiado largo", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionComenzar(ActionEvent e) {
        Dificultad dificultad = (Dificultad) comboBoxDificultad.getSelectedItem();
        String nombre = textFieldNombre.getText();
        String titulo = "Error";

        if (dificultad == null && nombre.equals("Nombre o Apodo")) {
            UtilidadesUI.mostrarMensaje("Por favor, siga las instrucciones", titulo, JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (dificultad == null) {
            UtilidadesUI.mostrarMensaje("Por favor, selecciona una dificultad.", titulo, JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (nombre.equals("Nombre o Apodo") || nombre.isEmpty()) {
            UtilidadesUI.mostrarMensaje("Por favor, ingrese su nombre o apodo.", titulo, JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (nombre.length() > 12) {
            UtilidadesUI.mostrarMensaje("Nombre o apodo demasiado largo, cambialo", titulo, JOptionPane.ERROR_MESSAGE);
            return;
        }

        new PantallaJuego(dificultad, nombre).setVisible(true);
        dispose();
    }

    private void accionComoJugar(ActionEvent e) {
        String mensaje = """
        Se busca colorear completamente una grilla de 5 × 5,
        pero evitando que dos celdas vecinas tengan el mismo color.
        Inicialmente, todas las celdas están sin colorear.

        En cada turno, el jugador hace click sobre una celda,
        y este click tiene el efecto de cambiar el color de esa casilla
        a uno aleatorio de entre 6 posibles.

        La dificultad consiste en que si este nuevo color coincide
        con el de alguna casilla vecina, la casilla cambiada
        ¡y todas sus vecinas se apagarán!

        Si necesitás ayuda, podés usar el botón de pista.
        Sin embargo, usarlas en exceso reducirá tu puntaje final,
        ¡así que usalas con sabiduría!
        """;

        UtilidadesUI.mostrarMensaje(mensaje, "Reglas del juego", JOptionPane.QUESTION_MESSAGE);
    }

    private void mostrarPanel(boolean mostrarDatos) {
        panelInicio.setVisible(!mostrarDatos);
        panelDatos.setVisible(mostrarDatos);
    }
}
