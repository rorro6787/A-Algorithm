package aAlgorithm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LaberintoView extends JFrame {

    private JTextField porcentajeObstaculosTextField;
    private JTextArea matrizTextArea;
    private JTextArea mensajeTextArea;
    private JProgressBar progressBar;
    private AAlgorithm laberinto;

    public LaberintoView() {
        setTitle("Laberinto");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior
        JPanel topPanel = new JPanel();
        JLabel porcentajeLabel = new JLabel("Porcentaje de obstáculos:");
        porcentajeObstaculosTextField = new JTextField(10);
        JButton generarButton = new JButton("Generar laberinto");
        JButton resolverButton = new JButton("Resolver laberinto");
        resolverButton.setEnabled(false);
        generarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarButton.setEnabled(false);
                resolverButton.setEnabled(true);
                generarLaberinto();
            }
        });

        porcentajeObstaculosTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    generarButton.setEnabled(false);
                    resolverButton.setEnabled(true);
                    generarLaberinto();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        topPanel.add(porcentajeLabel);
        topPanel.add(porcentajeObstaculosTextField);
        topPanel.add(generarButton);

        // Panel central
        JPanel centerPanel = new JPanel(new BorderLayout());

        matrizTextArea = new JTextArea(30, 60);
        matrizTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(matrizTextArea);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel inferior
        JPanel bottomPanel = new JPanel(new BorderLayout());

        mensajeTextArea = new JTextArea(2, 60);
        mensajeTextArea.setEditable(false);
        mensajeTextArea.setForeground(Color.RED);
        JScrollPane mensajeScrollPane = new JScrollPane(mensajeTextArea);
        mensajeScrollPane.setBorder(BorderFactory.createTitledBorder("Mensaje"));

        bottomPanel.add(mensajeScrollPane, BorderLayout.NORTH);

        // Botón "Resolver laberinto"
        resolverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resolverButton.setEnabled(false);
                generarButton.setEnabled(true);
                resolverLaberinto();
            }
        });
        bottomPanel.add(resolverButton, BorderLayout.CENTER);

        // Barra de progreso
        progressBar = new JProgressBar(0, 100);
        bottomPanel.add(progressBar, BorderLayout.SOUTH);

        // Añadir los paneles al frame
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        mensajeTextArea.setText("Introduzca un porcentaje de obstáculos entre 10 y 90");
        mensajeTextArea.setForeground(Color.blue);
    }

    private void generarLaberinto() {
        // Obtener el porcentaje de obstáculos del TextField
        String porcentajeStr = porcentajeObstaculosTextField.getText();
        try {
            int porcentaje = Integer.parseInt(porcentajeStr);
            if(porcentaje < 10 || porcentaje > 90) {
                throw new NumberFormatException();
            }
            // Lógica para generar el laberinto

            // Imprimir el laberinto en la matrizTextArea
            progressBar.setStringPainted(true);
            progressBar.setValue(0);
            boolean possible = false;
            while(!possible) {
                try {
                    laberinto = new AAlgorithm(porcentaje);
                    possible = true;
                }
                catch (RuntimeException e) {}
            }
            matrizTextArea.setText(laberinto.toString());
            mensajeTextArea.setForeground(Color.ORANGE);
            mensajeTextArea.setText("Laberinto Creado"); // Limpiar mensaje
            progressBar.setValue(0); // Reiniciar barra de progreso
        } catch(NumberFormatException e) {
            mensajeTextArea.setForeground(Color.RED);
            mensajeTextArea.setText("Valor no válido. Por favor, introduce un número entero.");
        }
    }

    private void resolverLaberinto() {
        // Lógica para resolver el laberinto

        // Imprimir el resultado en la matrizTextArea
        laberinto.algorithm();
        if(!laberinto.getPathFound()) {
            mensajeTextArea.setForeground(Color.RED);
            mensajeTextArea.setText("Se trata de un laberinto sin salida. No hay Solución...");
            progressBar.setValue(-1);
        }
        else {
            matrizTextArea.setText(laberinto.toString());
            progressBar.setValue(100);
            mensajeTextArea.setForeground(Color.GREEN);
            mensajeTextArea.setText("¡Laberinto resuelto en " + laberinto.getTimeForCompletion() + " ms!");
            progressBar.setValue(100);
        }
    }
}
