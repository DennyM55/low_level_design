# TicTacToe — Code

This package contains the complete Java implementation of the TicTacToe game designed in `_10_design_tictactoe`.

## Files

| File | Role |
|---|---|
| `PieceSymbol.java` | Enum for X, O, HASH symbols |
| `PlayingPiece.java` | Represents a piece placed on the board |
| `Player.java` | Represents a player with a name and piece |
| `Board.java` | Manages the NxN grid |
| `TicTacToeGame.java` | Game controller with loop and win check |
| `Main.java` | Entry point — creates players and starts the game |

## How To Run

From the `src` directory:

```bash
javac _11_code_tictactoe/*.java
java _11_code_tictactoe.Main
```

The game is interactive. Enter row and column numbers when prompted.

## Design Notes

- `Deque<Player>` is used for turn rotation. The current player is removed from the front and added to the back after a valid move.
- Win check is O(N): only the row, column, and diagonals through the last move are checked.
- `Board.addPiece()` returns false for occupied or out-of-bounds cells. The game loop retries.
- The board size and player list are passed to the constructor — easy to extend to NxN or 3-player games.

See `_10_design_tictactoe/README.md` for the full design walkthrough.
