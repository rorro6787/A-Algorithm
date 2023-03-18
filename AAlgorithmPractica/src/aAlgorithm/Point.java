package aAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Point {
    int x, y;
    Point parent;
    public Point(int x, int y, Point parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
    }

    public List<Point> getNeighbors(String[][] tab, int fila, int columna) {
        List<Point> neighbors = new ArrayList<>();
        int x = this.getX(), y = this.getY();
        if (x > 0 && tab[x - 1][y] != "*") {
            neighbors.add(new Point(x - 1, y, this));
        }
        if (x < fila - 1 && tab[x + 1][y] != "*") {
            neighbors.add(new Point(x + 1, y, this));
        }
        if (y > 0 && tab[x][y - 1] != "*") {
            neighbors.add(new Point(x, y - 1, this));
        }
        if (y < columna - 1 && tab[x][y + 1] != "*") {
            neighbors.add(new Point(x, y + 1, this));
        }
        return neighbors;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public Point getParent() { return parent; }

    @Override
    public boolean equals(Object obj) {
        boolean res = obj == this;
        if(!res && obj instanceof Point) {
            Point aux = (Point) obj;
            res = aux.x == this.x && aux.y == this.y;
        }
        return res;
    }

    @Override
    public int hashCode() { return Objects.hash(x, y); }

    @Override
    public String toString() { return "(" + x + ", " + y + ")"; }
}
