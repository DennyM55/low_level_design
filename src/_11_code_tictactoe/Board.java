package _11_code_tictactoe;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final int size;
    private final PlayingPiece[][] grid;

    public Board(int size) {
        this.size = size;
        this.grid = new PlayingPiece[size][size];
    }

    public int getSize() {
        return size;
    }

    public PlayingPiece[][] getGrid() {
        return grid;
    }

    public boolean addPiece(int row, int col, PlayingPiece piece) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            return false;
        }
        if (grid[row][col] != null) {
            return false;
        }
        grid[row][col] = piece;
        return true;
    }

    public List<int[]> getFreeCells() {
        List<int[]> freeCells = new ArrayList<>();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (grid[row][col] == null) {
                    freeCells.add(new int[]{row, col});
                }
            }
        }
        return freeCells;
    }

    public void printBoard() {
        System.out.println();
        for (int row = 0; row < size; row++) {
            StringBuilder sb = new StringBuilder();
            for (int col = 0; col < size; col++) {
                if (grid[row][col] == null) {
                    sb.append("  -");
                } else {
                    sb.append("  ").append(grid[row][col].getSymbol());
                }
                if (col < size - 1) {
                    sb.append(" |");
                }
            }
            System.out.println(sb);
            if (row < size - 1) {
                System.out.println("   " + "-".repeat(size * 4 - 1));
            }
        }
        System.out.println();
    }
}
