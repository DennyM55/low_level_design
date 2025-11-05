# TicTacToe — LLD Design

## Requirements Clarification

Before designing, these questions should be asked:

- How many players? (Default: 2, but design should support N players)
- What is the board size? (Default: 3x3, but design should support NxN)
- Who goes first? (The player who is added first)
- What symbols does each player use? (X and O by default, but configurable)
- Is there a bot player? (No, for now)
- How do we detect a win? (Row, column, or diagonal complete)
- What happens when the board is full and no winner? (Draw)

---

## Entities and Their Responsibilities

### Player

Represents a human player.

Fields:
- `name` — player's display name
- `symbol` — the piece symbol the player uses (e.g., X, O)

Responsibility: holds identity and symbol. Does not contain game logic.

---

### PieceSymbol (Enum)

Represents the symbol a player uses on the board.

Values: `X`, `O` (or any symbol for N-player variants)

Why enum: avoids magic strings and provides type safety.

---

### PlayingPiece

Represents a single piece on the board.

Fields:
- `symbol` — the PieceSymbol

Why a separate class: allows future extension (e.g., colored pieces, weighted pieces in variants).

---

### Board

Represents the NxN game grid.

Fields:
- `size` — the board dimension
- `grid` — a 2D array of `PlayingPiece` (null means empty)

Methods:
- `addPiece(row, col, piece)` — places a piece if the cell is empty
- `getFreeCells()` — returns all empty cell positions
- `printBoard()` — prints the current state

Responsibility: manages the grid state. Does not contain win-check logic.

---

### TicTacToeGame

The controller that drives the game loop.

Fields:
- `board` — the game board
- `players` — a deque (queue) of players for turn rotation

Methods:
- `startGame()` — game loop: pick player, take input, place piece, check result
- `isWinner(row, col, symbol)` — checks if the last placed piece wins
- `isGameOver()` — checks for a win or a draw

Responsibility: orchestrates the game. Uses Board and Player but does not own their internal logic.

---

## Class Diagram (Text)

```
PieceSymbol (enum)
  X, O

PlayingPiece
  - symbol: PieceSymbol

Player
  - name: String
  - playingPiece: PlayingPiece

Board
  - size: int
  - grid: PlayingPiece[][]
  + addPiece(row, col, piece): boolean
  + getFreeCells(): List<int[]>
  + printBoard(): void

TicTacToeGame
  - board: Board
  - players: Deque<Player>
  + startGame(): String
  - isWinner(row, col, symbol): boolean
```

---

## Relationships

```
TicTacToeGame has-a (composition) Board
TicTacToeGame has-a (aggregation) List<Player>
Board has-a (composition) PlayingPiece[][]
Player has-a (composition) PlayingPiece
PlayingPiece has-a PieceSymbol
```

---

## Game Flow (Sequence)

```
startGame()
│
├── Pick current player from front of deque
│
├── Get user input (row, col)
│
├── board.addPiece(row, col, piece)
│   └── Returns false if cell is taken → retry
│
├── isWinner(row, col, symbol)
│   ├── Check entire row
│   ├── Check entire column
│   ├── Check diagonal (if row == col)
│   └── Check anti-diagonal (if row + col == size - 1)
│
├── If winner → return player name
│
├── If board.getFreeCells().isEmpty() → return "DRAW"
│
└── Rotate player (move current player to back of deque)
```

---

## Win Condition Logic

After each move at position `(row, col)`, check:

1. **Row win**: all cells in `row` have the same symbol
2. **Column win**: all cells in `col` have the same symbol
3. **Diagonal win**: if `row == col`, check main diagonal
4. **Anti-diagonal win**: if `row + col == size - 1`, check anti-diagonal

This is O(N) per move check, which is optimal.

---

## Design Decisions

| Decision | Reasoning |
|---|---|
| `Deque<Player>` instead of index rotation | More idiomatic for turn-based games; easy to add/remove players |
| `Board` does not check winners | Single Responsibility: Board manages grid state only |
| `PlayingPiece` is a separate class from `Player` | Piece can be extended independently (color, weight) |
| `PieceSymbol` is an enum | Type safety; prevents invalid symbols |
| Console input in `TicTacToeGame` | Acceptable for this scope; in production, extract to an InputHandler |

---

## Extensibility

This design supports:

- N players with different symbols (add more enum values or use a char field)
- NxN board (pass size to Board constructor)
- Bot player (extend Player to BotPlayer with an `autoSelectCell()` method)
- GUI frontend (Board's grid can be read by a view component)

---

## See Code

Implementation is in `_11_code_tictactoe`.
