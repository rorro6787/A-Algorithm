package aAlgorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Tablero {
    String[][] tab;
    private final int fila = 60, columna = 80;
    private double[] obstaclePercent = {1,2,3,4,5,6,7,8,9}; // For the additional task
    private boolean pathFound;
    private Point start, end;

    final String ANSI_RESET = "\u001B[0m";
    final String ANSI_RED = "\u001B[31m";
    final String ANSI_GREEN = "\u001B[42m";
    private long timeForCompletion;
    protected Tablero(int porcentajeOfObstacles) {
        pathFound = false;
        tab = new String[fila][columna];
        for(int i = 0; i < (this.fila*this.columna*obstaclePercent[porcentajeOfObstacles])/(10); ++i) {
            tab[new Random().nextInt(this.fila)][new Random().nextInt(this.columna)] = "*";
        }
        initialConditions("I");
        initialConditions("G");
    }

    private void initialConditions(String c) {
        int fila = new Random().nextInt(this.fila - 1);
        int columna = new Random().nextInt(this.columna - 1);
        if(this.tab[fila][columna] == "*") {
            throw new RuntimeException("Trying to" + (c == "I" ? " start " : " end ") +
                                       "in a position already occupied with an obstacle");
        }
        if(c == "G") {
            this.pathFound = this.tab[fila][columna] == "I";
            this.tab[fila][columna] = this.tab[fila][columna] == "I" ? "+" : c;
            end = new Point(fila, columna, null);
        }
        else {
            this.tab[fila][columna] = c;
            start = new Point(fila, columna, null);
        }
    }
    protected boolean getPathFound() { return pathFound; }
    protected void setPathFound() { this.pathFound = true; }
    protected void setTab(int fila, int columna, String c) { this.tab[fila][columna] = c; }
    protected int getFila() { return fila; }

    protected String getPos(int fila, int columna) { return this.tab[fila][columna]; }
    protected int getColumna() { return columna; }

    protected void writeInFile(String f) throws FileNotFoundException {
        try(PrintWriter pw = new PrintWriter(new File(f))) {
            pw.print(this.toString());
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < this.fila; ++i) {
            for(int j = 0; j < this.columna; ++j) {
                /*String aux;
                if(this.tab[i][j] == "I" || this.tab[i][j] == "G") {
                    aux = ANSI_RED + this.tab[i][j] + ANSI_RESET;
                }
                else if(this.tab[i][j] == "+") {
                    aux = ANSI_GREEN + this.tab[i][j] + ANSI_RESET;
                }
                else {
                    aux = this.tab[i][j] == "*" ? "*" : " ";
                }
                sb.append(aux + " ");*/
                sb.append(this.tab[i][j] == "I" || this.tab[i][j] == "G" || this.tab[i][j] == "+" ||
                          this.tab[i][j] == "*" ? this.tab[i][j] + " " : "  ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    protected int algorithm() {
        timeForCompletion = System.currentTimeMillis();
        PriorityQueue<Point> pq = new PriorityQueue<Point>(new Comparator<Point>() {
            @Override
            public int compare(Point arg0, Point arg1) {
                if(f(arg0) == f(arg1)) return 0;
                else if(f(arg0) > f(arg1)) return 1;
                else return -1;
            }
        });
        Map<Point, Double> g = new HashMap<Point, Double>();
        Map<Point, Point> padre = new HashMap<Point, Point>();
        Point inicial = new Point(start.getX(), start.getY(), null);
        Point objetivo = new Point(end.getX(), end.getY(), null);
        g.put(inicial, 0.0);
        pq.add(inicial);

        while(!pq.isEmpty()) {
            Point actual = pq.poll();
            if(actual.equals(objetivo)) {
                setPathFound();
                break;
            }
            for(Point sucesor : actual.getNeighbors(this.tab, this.fila, this.columna)) {
                double gScore = g.get(actual) + 1;
                if(!g.containsKey(sucesor) || gScore < g.get(sucesor)) {
                    g.put(sucesor, gScore);
                    int h = h(sucesor);
                    padre.put(sucesor, actual);
                    pq.offer(sucesor);
                }
            }
        }
        int length = 0;
        if(getPathFound()) {
            ++length;
            Point actual = new Point(objetivo.getX(), objetivo.getY(), null);
            while(padre.containsKey(actual)) {
                ++length;
                actual = padre.get(actual);
                if(!actual.equals(inicial)) {
                    setTab(actual.getX(), actual.getY(), "+");
                }
            }
        }
        timeForCompletion = System.currentTimeMillis() - timeForCompletion;
        return length;
    }
    private int f(Point n) {
        return g(n) + h(n);
    }

    private int g(Point n) {
        int moves = 0;
        while (n != null) {
            n = n.getParent();
            ++moves;
        }
        return moves;
    }

    private int h(Point n) {
        return Math.abs(n.getX() - end.getX()) + Math.abs(n.getY() - end.getY());
    }

    public long getTimeForCompletion() {
        return timeForCompletion;
    }
}
