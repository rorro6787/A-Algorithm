package aAlgorithm;

import java.io.FileNotFoundException;

public class AAlgorithm {
    private Tablero tablero;

    public AAlgorithm(int porcentajeOfObstacles) { tablero = new Tablero(porcentajeOfObstacles); }

    public Tablero getTablero() { return tablero; }

    public int algorithm() { return tablero.algorithm(); }

    public void writeInFile(String f) {
        try {
            tablero.writeInFile(f);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public boolean getPathFound() {
        return tablero.getPathFound();
    }

    @Override
    public String toString() { return tablero.toString(); }
}
