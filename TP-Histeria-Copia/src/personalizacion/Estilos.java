package personalizacion;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.swing.table.*;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicLabelUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;


public class Estilos {
	
	private static String ruta = "src/personalizacion/PressStart2P-Regular.ttf";
	
	public static Font cargarFuentePersonalizada(float tamaño) {
	    try {
	        return Font.createFont(Font.TRUETYPE_FONT, new File(ruta)).deriveFont(tamaño);
	    } catch (FontFormatException | IOException e) {
	        e.printStackTrace();
	        return new Font("Comic Sans MS", Font.BOLD, (int) tamaño); // Fallback si falla
	    }
	}
	
	public static void aplicarDegradado(JLabel label, Color color1, Color color2, Color color3) {
	    label.setOpaque(false); // Fondo transparente

	    label.setUI(new BasicLabelUI() {
	        @Override
	        public void paint(Graphics g, javax.swing.JComponent c) {
	            Graphics2D g2d = (Graphics2D) g;

	            int width = c.getWidth();
	            int height = c.getHeight();

	            // Degradado
	            Point start = new Point(0, 0);
	            Point end = new Point(width, height);
	            Color[] colors = {color1, color2, color3};
	            float[] fractions = {0.0f, 0.5f, 1.0f};

	            LinearGradientPaint gradient = new LinearGradientPaint(start, end, fractions, colors);
	            g2d.setPaint(gradient);

	            // Usar fuente y medir el ancho del texto
	            g2d.setFont(label.getFont());
	            FontMetrics fm = g2d.getFontMetrics();
	            String text = label.getText();

	            int textWidth = fm.stringWidth(text);
	            int textHeight = fm.getAscent(); // altura desde la línea base hasta la parte superior del texto

	            // Calcular coordenadas para centrar el texto
	            int x = (width - textWidth) / 2;
	            int y = (height + textHeight) / 2 - 4; // Ajuste fino para alinearlo mejor

	            // Dibujar el texto centrado
	            g2d.drawString(text, x, y);
	        }
	    });
	}
	
	public static void aplicarDegradado(JButton button, Color color1, Color color2, Color color3) {
        button.setOpaque(false); // Asegurarse de que el fondo sea transparente

        // Sobrescribimos el paintComponent del JButton para agregar el degradado
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, javax.swing.JComponent c) {
                // Crear el objeto Graphics2D
                Graphics2D g2d = (Graphics2D) g;

                // Obtener el ancho y la altura del JButton
                int width = c.getWidth();
                int height = c.getHeight();

                // Definir los puntos donde aplicaremos el degradado
                Point start = new Point(0, 0); // Empieza en la esquina superior izquierda
                Point end = new Point(width, height); // Termina en la esquina inferior derecha

                // Crear el degradado con tres colores
                Color[] colors = {color1, color2, color3};
                float[] fractions = {0.0f, 0.5f, 1.0f}; // Puntos de transición entre colores

                LinearGradientPaint gradient = new LinearGradientPaint(start, end, fractions, colors);
                g2d.setPaint(gradient);

                // Aplicar el degradado al texto directamente
                g2d.setFont(button.getFont()); // Usamos la fuente del JButton
                FontMetrics metrics = g2d.getFontMetrics();
                int x = (width - metrics.stringWidth(button.getText())) / 2; // Centrar el texto horizontalmente
                int y = (height + metrics.getAscent()) / 2; // Centrar el texto verticalmente

                // Dibujar el texto con el degradado
                g2d.drawString(button.getText(), x, y);
            }
        });
    }
	
	public static void aplicarDegradadoConAlineaciones(JTable tabla, Color color1, Color color2, Color color3) {
	    TableCellRenderer renderer = new DefaultTableCellRenderer() {
	        @Override
	        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

	            // Alineación según columna
	            if (column == 0) {
	                label.setHorizontalAlignment(SwingConstants.LEFT);
	            } else if (column == 1) {
	                label.setHorizontalAlignment(SwingConstants.RIGHT);
	            }

	            label.setOpaque(false);
	            label.setUI(new BasicLabelUI() {
	                @Override
	                public void paint(Graphics g, JComponent c) {
	                    Graphics2D g2d = (Graphics2D) g;
	                    int width = c.getWidth();
	                    int height = c.getHeight();

	                    Color[] colors = {color1, color2, color3};
	                    float[] fractions = {0.0f, 0.5f, 1.0f};
	                    LinearGradientPaint gradient = new LinearGradientPaint(new Point(0, 0), new Point(width, height), fractions, colors);
	                    g2d.setPaint(gradient);

	                    g2d.setFont(label.getFont());
	                    FontMetrics metrics = g2d.getFontMetrics();
	                    int x;
	                    if (label.getHorizontalAlignment() == SwingConstants.LEFT) {
	                        x = 5;
	                    } else if (label.getHorizontalAlignment() == SwingConstants.RIGHT) {
	                        x = width - metrics.stringWidth(label.getText()) - 5;
	                    } else {
	                        x = (width - metrics.stringWidth(label.getText())) / 2;
	                    }
	                    int y = (height + metrics.getAscent()) / 2 - 2;
	                    g2d.drawString(label.getText(), x, y);
	                }
	            });

	            return label;
	        }
	    };

	    // Asignar el mismo renderer a todas las columnas
	    TableColumnModel columnModel = tabla.getColumnModel();
	    for (int i = 0; i < columnModel.getColumnCount(); i++) {
	        columnModel.getColumn(i).setCellRenderer(renderer);
	    }
	}

	public static void mostrarMensaje(String mensaje, String textoVentana, int tipo) {
	    UIManager.put("OptionPane.messageFont", Estilos.cargarFuentePersonalizada(10f));
	    UIManager.put("OptionPane.buttonFont", Estilos.cargarFuentePersonalizada(8f));
	    JOptionPane.showMessageDialog(null, mensaje, textoVentana, tipo);
	}
	
	public static void personalizarBoton(JButton boton) {
		boton.setBackground(null);  // Puedes poner un color de fondo si lo deseas
		boton.setContentAreaFilled(false); // saca el fondo
	    boton.setBorderPainted(false);     // saca el borde
	    boton.setFocusPainted(false);      // saca el borde de foco
	    boton.setOpaque(false);            // transparente
	    boton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // mano al pasar

	    boton.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseEntered(java.awt.event.MouseEvent evt) {
	            boton.setText(boton.getText() + "<");
	        }

	        public void mouseExited(java.awt.event.MouseEvent evt) {
	            // Quita el subrayado
	            boton.setText(boton.getText().replaceAll("<", "").replaceAll("<", ""));
	        }
	    });
	}
	public static void personalizarCursor(JTextField textField, Color color, int ancho) {
	    textField.setCaretColor(color);
	    textField.setCaret(new DefaultCaret() {
	        {
	            setBlinkRate(500); // opcional
	        }

	        @Override
	        public void paint(Graphics g) {
	            JTextComponent comp = getComponent();
	            if (comp == null || !isVisible()) return;

	            try {
	                Rectangle r = comp.modelToView(getDot());
	                if (r == null) return;

	                g.setColor(comp.getCaretColor());

	                // Cursor más grueso (tipo bloque retro)
	                g.fillRect(r.x, r.y, ancho, r.height);
	            } catch (BadLocationException e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}
	
	
	
}
