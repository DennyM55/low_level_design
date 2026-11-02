package com.denny.lld.projects.tictactoe;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Player player1 = new Player("Alice", new PlayingPiece(PieceSymbol.X));
        Player player2 = new Player("Bob", new PlayingPiece(PieceSymbol.O));

        TicTacToeGame game = new TicTacToeGame(3, List.of(player1, player2));

        String result = game.startGame();
        System.out.println("Result: " + result);
    }
}
