package personalizacion;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicLabelUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;

public class UtilidadesUI {

    private static final String RUTA_FUENTE = "src/personalizacion/PressStart2P-Regular.ttf";

    public static Font cargarFuentePersonalizada(float tamaño) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File(RUTA_FUENTE)).deriveFont(tamaño);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return new Font("Comic Sans MS", Font.BOLD, (int) tamaño); // Fallback
        }
    }

    public static void aplicarDegradado(JLabel label, Color c1, Color c2, Color c3) {
        label.setOpaque(false);
        label.setUI(new BasicLabelUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g;
                int w = c.getWidth(), h = c.getHeight();
                g2d.setPaint(crearDegradado(w, h, c1, c2, c3));
                g2d.setFont(label.getFont());

                FontMetrics fm = g2d.getFontMetrics();
                String text = label.getText();
                int x = (w - fm.stringWidth(text)) / 2;
                int y = (h + fm.getAscent()) / 2 - 4;

                g2d.drawString(text, x, y);
            }
        });
    }

    public static void aplicarDegradado(JButton button, Color c1, Color c2, Color c3) {
        button.setOpaque(false);
        button.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g;
                int w = c.getWidth(), h = c.getHeight();
                g2d.setPaint(crearDegradado(w, h, c1, c2, c3));
                g2d.setFont(button.getFont());

                FontMetrics fm = g2d.getFontMetrics();
                String text = button.getText();
                int x = (w - fm.stringWidth(text)) / 2;
                int y = (h + fm.getAscent()) / 2;

                g2d.drawString(text, x, y);
            }
        });
    }

    public static void aplicarDegradadoConAlineaciones(JTable tabla, Color c1, Color c2, Color c3) {
        TableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                label.setHorizontalAlignment(column == 0 ? SwingConstants.LEFT :
                        column == 1 ? SwingConstants.RIGHT : SwingConstants.CENTER);

                label.setOpaque(false);
                label.setUI(new BasicLabelUI() {
                    @Override
                    public void paint(Graphics g, JComponent c) {
                        Graphics2D g2d = (Graphics2D) g;
                        int w = c.getWidth(), h = c.getHeight();
                        g2d.setPaint(crearDegradado(w, h, c1, c2, c3));
                        g2d.setFont(label.getFont());

                        FontMetrics fm = g2d.getFontMetrics();
                        String text = label.getText();
                        int x = switch (label.getHorizontalAlignment()) {
                            case SwingConstants.LEFT -> 5;
                            case SwingConstants.RIGHT -> w - fm.stringWidth(text) - 5;
                            default -> (w - fm.stringWidth(text)) / 2;
                        };
                        int y = (h + fm.getAscent()) / 2 - 2;
                        g2d.drawString(text, x, y);
                    }
                });
                return label;
            }
        };

        TableColumnModel model = tabla.getColumnModel();
        for (int i = 0; i < model.getColumnCount(); i++) {
            model.getColumn(i).setCellRenderer(renderer);
        }
    }

    public static void configurarFuenteParaMensajes(float fuenteMensaje, float fuenteBoton) {
        UIManager.put("OptionPane.messageFont", cargarFuentePersonalizada(fuenteMensaje));
        UIManager.put("OptionPane.buttonFont", cargarFuentePersonalizada(fuenteBoton));
    }

    public static void mostrarMensaje(String mensaje, String titulo, int tipo) {
        configurarFuenteParaMensajes(10f, 8f);
        JOptionPane.showMessageDialog(null, mensaje, titulo, tipo);
    }

    public static void personalizarBoton(JButton boton) {
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setOpaque(false);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        final String textoOriginal = boton.getText();

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                boton.setText(textoOriginal + "<");
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                boton.setText(textoOriginal);
            }
        });
    }

    public static void personalizarCursor(JTextField campo, Color color, int ancho) {
        campo.setCaretColor(color);
        campo.setCaret(new DefaultCaret() {
            {
                setBlinkRate(500);
            }

            @Override
            public void paint(Graphics g) {
                JTextComponent comp = getComponent();
                if (comp == null || !isVisible()) return;

                try {
                    Rectangle r = comp.modelToView(getDot());
                    if (r == null) return;

                    g.setColor(comp.getCaretColor());
                    g.fillRect(r.x, r.y, ancho, r.height);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public static JLabel crearEtiqueta(String texto, float tamaño, int x, int y, int ancho, int alto, Color colorInicial) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setFont(cargarFuentePersonalizada(tamaño));
        label.setForeground(Color.WHITE);
        label.setBounds(x, y, ancho, alto);
        aplicarDegradado(label, colorInicial, Color.RED, colorInicial);
        return label;
    }

    public static JButton crearBoton(String texto, int x, int y, int ancho, int alto) {
        JButton boton = new JButton(texto);
        boton.setBounds(x, y, ancho, alto);
        boton.setFont(cargarFuentePersonalizada(15f));
        aplicarDegradado(boton, Color.YELLOW, Color.RED, Color.YELLOW);
        personalizarBoton(boton);
        return boton;
    }
    
    private static LinearGradientPaint crearDegradado(int width, int height, Color c1, Color c2, Color c3) {
        return new LinearGradientPaint(new Point(0, 0), new Point(width, height),
                new float[]{0.0f, 0.5f, 1.0f}, new Color[]{c1, c2, c3});
    }

}

