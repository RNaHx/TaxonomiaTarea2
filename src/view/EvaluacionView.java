// view/EvaluacionView.java
package view;

import backend.Item;
import backend.Pregunta;
import controller.EvaluacionController;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class EvaluacionView extends JFrame {
    private List<Item> items;
    private Map<Integer, List<String>> respuestasUsuario = new HashMap<>();
    private int itemActual = 0;

    private JPanel panelPreguntas;
    private JButton btnAnterior;
    private JButton btnSiguiente;
    private JLabel lblItem;

    private final EvaluacionController controller;

    public EvaluacionView() {
        controller = new EvaluacionController();
        setupUI();
        seleccionarArchivoYComenzar();
    }

    private void setupUI() {
        setTitle("Evaluación MVC");
        setSize(600, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        lblItem = new JLabel("Item", SwingConstants.CENTER);
        add(lblItem, BorderLayout.NORTH);

        panelPreguntas = new JPanel();
        panelPreguntas.setLayout(new BoxLayout(panelPreguntas, BoxLayout.Y_AXIS));
        add(new JScrollPane(panelPreguntas), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        btnAnterior = new JButton("Anterior");
        btnSiguiente = new JButton("Siguiente");

        btnAnterior.addActionListener(e -> {
            if (itemActual > 0) {
                itemActual--;
                mostrarItem(items.get(itemActual));
            }
        });

        btnSiguiente.addActionListener(e -> {
            if (btnSiguiente.getText().equals("Entregar")) {
                mostrarResultados();
            } else {
                if (itemActual < items.size() - 1) {
                    itemActual++;
                    mostrarItem(items.get(itemActual));
                }
                if (itemActual == items.size() - 1) {
                    btnSiguiente.setText("Entregar");
                }
            }
        });

        panelBotones.add(btnAnterior);
        panelBotones.add(btnSiguiente);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void seleccionarArchivoYComenzar() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccione el archivo de evaluación");

        int resultado = fileChooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            try {
                items = controller.cargarEvaluacionDesdeTxt(archivo);
                itemActual = 0;
                mostrarItem(items.get(itemActual));
                setVisible(true);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al leer el archivo.");
                System.exit(1);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se seleccionó archivo.");
            System.exit(0);
        }
    }

    private void mostrarItem(Item item) {
        panelPreguntas.removeAll();
        Map<Integer, List<String>> respuestasItem = new HashMap<>();

        for (Pregunta pregunta : item.preguntas) {
            JPanel panelPregunta = new JPanel(new BorderLayout());
            String titulo = String.format("[%s] %s", pregunta.nivel.toUpperCase(), pregunta.texto);
            panelPregunta.setBorder(BorderFactory.createTitledBorder(titulo));

            JPanel opcionesPanel = new JPanel();
            opcionesPanel.setLayout(new BoxLayout(opcionesPanel, BoxLayout.Y_AXIS));

            if (item.tipo.equalsIgnoreCase("verdadero_falso")) {
                ButtonGroup group = new ButtonGroup();
                for (String opcion : pregunta.opciones) {
                    JRadioButton rb = new JRadioButton(opcion);
                    group.add(rb);
                    opcionesPanel.add(rb);
                    rb.addItemListener(e -> {
                        if (rb.isSelected()) {
                            respuestasItem.put(pregunta.preguntaId, Collections.singletonList(opcion));
                            respuestasUsuario.put(pregunta.preguntaId, Collections.singletonList(opcion));
                        }
                    });
                }
            } else { // opcion_multiple
                List<JCheckBox> checkBoxes = new ArrayList<>();
                for (String opcion : pregunta.opciones) {
                    JCheckBox cb = new JCheckBox(opcion);
                    checkBoxes.add(cb);
                    opcionesPanel.add(cb);
                }
                for (JCheckBox cb : checkBoxes) {
                    cb.addItemListener(e -> {
                        List<String> seleccionadas = new ArrayList<>();
                        for (JCheckBox box : checkBoxes) {
                            if (box.isSelected()) {
                                seleccionadas.add(box.getText());
                            }
                        }
                        respuestasItem.put(pregunta.preguntaId, seleccionadas);
                        respuestasUsuario.put(pregunta.preguntaId, seleccionadas);
                    });
                }
            }

            panelPregunta.add(opcionesPanel, BorderLayout.CENTER);
            panelPreguntas.add(panelPregunta);
        }

        panelPreguntas.revalidate();
        panelPreguntas.repaint();
    }

    private void mostrarResultados() {
        Map<String, Object> resultado = controller.evaluar(items, respuestasUsuario);
        JOptionPane.showMessageDialog(this, resultado.get("detalle").toString());
        System.exit(0);
    }
}