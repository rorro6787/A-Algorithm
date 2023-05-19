import aAlgorithm.LaberintoView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LaberintoView view = new LaberintoView();
                view.setVisible(true);
            }
        });
    }
}
