package software.aoc.day07.a;

import java.util.List;

public class GridMap {

    private final List<String> grid;
    private final int height;
    private final int width;

    public GridMap(List<String> grid) {
        this.grid = grid;
        this.height = grid.size();
        // Asumimos que el ancho es la longitud de la primera línea (o max length)
        this.width = grid.isEmpty() ? 0 : grid.get(0).length();
    }

    public char getCell(int row, int col) {
        if (row < 0 || row >= height) return ' '; // Fuera de límites vertical
        String line = grid.get(row);
        if (col < 0 || col >= line.length()) return ' '; // Fuera de límites horizontal
        return line.charAt(col);
    }

    public int findStartIndex() {
        // Buscamos la 'S' en la primera fila (o en cualquier parte si fuera necesario)
        for (int r = 0; r < height; r++) {
            int index = grid.get(r).indexOf('S');
            if (index != -1) {
                return index;
            }
        }
        return -1;
    }

    public boolean isInsideBounds(int col) {
        return col >= 0 && col < width;
    }

    public int getHeight() {
        return height;
    }
}
