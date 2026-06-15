package _11_code_tictactoe;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;

public class TicTacToeGame {

    private final Board board;
    private final Deque<Player> players;

    public TicTacToeGame(int boardSize, List<Player> playerList) {
        this.board = new Board(boardSize);
        this.players = new ArrayDeque<>(playerList);
    }

    public String startGame() {
        Scanner scanner = new Scanner(System.in);
        boolean gameOver = false;

        while (!gameOver) {
            board.printBoard();

            Player currentPlayer = players.removeFirst();
            System.out.println(currentPlayer.getName() + "'s turn (symbol: " + currentPlayer.getPiece().getSymbol() + ")");

            int row = -1;
            int col = -1;
            boolean validInput = false;

            while (!validInput) {
                System.out.print("Enter row (0 to " + (board.getSize() - 1) + "): ");
                row = scanner.nextInt();
                System.out.print("Enter col (0 to " + (board.getSize() - 1) + "): ");
                col = scanner.nextInt();

                boolean placed = board.addPiece(row, col, currentPlayer.getPiece());
                if (!placed) {
                    System.out.println("Invalid move. Cell is either occupied or out of range. Try again.");
                } else {
                    validInput = true;
                }
            }

            if (isWinner(row, col, currentPlayer.getPiece().getSymbol())) {
                board.printBoard();
                return currentPlayer.getName() + " wins!";
            }

            if (board.getFreeCells().isEmpty()) {
                board.printBoard();
                return "It's a draw!";
            }

            players.addLast(currentPlayer);
        }

        return "Game ended";
    }

    private boolean isWinner(int row, int col, PieceSymbol symbol) {
        int size = board.getSize();
        PlayingPiece[][] grid = board.getGrid();

        // Check row
        boolean rowWin = true;
        for (int c = 0; c < size; c++) {
            if (grid[row][c] == null || grid[row][c].getSymbol() != symbol) {
                rowWin = false;
                break;
            }
        }
        if (rowWin) return true;

        // Check column
        boolean colWin = true;
        for (int r = 0; r < size; r++) {
            if (grid[r][col] == null || grid[r][col].getSymbol() != symbol) {
                colWin = false;
                break;
            }
        }
        if (colWin) return true;

        // Check main diagonal (only if on the diagonal)
        if (row == col) {
            boolean diagWin = true;
            for (int i = 0; i < size; i++) {
                if (grid[i][i] == null || grid[i][i].getSymbol() != symbol) {
                    diagWin = false;
                    break;
                }
            }
            if (diagWin) return true;
        }

        // Check anti-diagonal
        if (row + col == size - 1) {
            boolean antiDiagWin = true;
            for (int i = 0; i < size; i++) {
                if (grid[i][size - 1 - i] == null || grid[i][size - 1 - i].getSymbol() != symbol) {
                    antiDiagWin = false;
                    break;
                }
            }
            if (antiDiagWin) return true;
        }

        return false;
    }
}
