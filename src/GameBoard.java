import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the game board component and handles game logic.
 */
public class GameBoard extends JPanel {
    private Cell[][] cells;
    private final int rows;
    private final int cols;
    private final int totalMines;
    private int remainingMines;
    private boolean firstClick;
    private final List<GameListener> gameListeners;

    /**
     * Interface for game event listeners
     */
    public interface GameListener {
        void onGameStart();
        void onGameOver(boolean won);
        void onMineCountChanged(int remainingMines);
    }

    /**
     * Constructor for GameBoard
     * @param size Board size (width and height)
     * @param mines Number of mines to place
     */
    public GameBoard(int size, int mines) {
        this.rows = size;
        this.cols = size;
        this.totalMines = mines;
        this.remainingMines = mines;
        this.firstClick = true;
        this.gameListeners = new ArrayList<>();

        initializeBoard();
    }

    /**
     * Initializes the game board
     */
    private void initializeBoard() {
        setLayout(new GridLayout(rows, cols));
        setBorder(BorderFactory.createLoweredBevelBorder());
        cells = new Cell[rows][cols];

        // Create cells
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cells[i][j] = new Cell(i, j);
                cells[i][j].addMouseListener(new CellMouseListener(cells[i][j]));
                add(cells[i][j]);
            }
        }

        setPreferredSize(new Dimension(
            cols * GameConstants.CELL_SIZE,
            rows * GameConstants.CELL_SIZE
        ));
    }

    /**
     * Places mines on the board, avoiding the first clicked cell
     * @param firstRow Row of first click
     * @param firstCol Column of first click
     */
    private void placeMines(int firstRow, int firstCol) {
        Random random = new Random();
        int minesPlaced = 0;

        while (minesPlaced < totalMines) {
            int row = random.nextInt(rows);
            int col = random.nextInt(cols);

            // Avoid placing mine on first click or already mined cell
            if (!cells[row][col].isMine() && 
                (row < firstRow - 1 || row > firstRow + 1 || 
                 col < firstCol - 1 || col > firstCol + 1)) {
                cells[row][col].setMine(true);
                minesPlaced++;
            }
        }

        // Calculate numbers for all cells
        calculateNumbers();
    }

    /**
     * Calculates the number of adjacent mines for each cell
     */
    private void calculateNumbers() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!cells[i][j].isMine()) {
                    cells[i][j].setAdjacentMines(countAdjacentMines(i, j));
                }
            }
        }
    }

    /**
     * Counts mines adjacent to a cell
     * @param row Cell row
     * @param col Cell column
     * @return Number of adjacent mines
     */
    private int countAdjacentMines(int row, int col) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;
                if (isValidCell(newRow, newCol) && cells[newRow][newCol].isMine()) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Reveals a cell and its adjacent cells if empty
     * @param row Cell row
     * @param col Cell column
     */
    private void revealCell(int row, int col) {
        if (!isValidCell(row, col) || cells[row][col].isRevealed() || cells[row][col].isFlagged()) {
            return;
        }

        cells[row][col].reveal();

        if (cells[row][col].getAdjacentMines() == 0) {
            // Reveal all adjacent cells for empty cell
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    revealCell(row + i, col + j);
                }
            }
        }
    }

    /**
     * Checks if coordinates are within board boundaries
     */
    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    /**
     * Checks if the game is won
     */
    private void checkWin() {
        int unrevealed = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!cells[i][j].isRevealed() && !cells[i][j].isMine()) {
                    unrevealed++;
                }
            }
        }
        if (unrevealed == 0) {
            gameOver(true);
        }
    }

    /**
     * Handles game over state
     * @param won True if game was won, false if lost
     */
    private void gameOver(boolean won) {
    // Reveal all cells
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            if (won && cells[i][j].isMine()) {
                cells[i][j].flag(); // Flag all mines on win
            } else {
                cells[i][j].reveal();
                // Mark wrongly flagged cells with X
                if (!won && cells[i][j].isFlagged() && !cells[i][j].isMine()) {
                    cells[i][j].markWrongFlag();
                }
            }
        }
    }

    // Notify listeners
    for (GameListener listener : gameListeners) {
        listener.onGameOver(won);
    }
}

    /**
     * Mouse listener for cell interaction
     */
    private class CellMouseListener extends MouseAdapter {
        private final Cell cell;

        public CellMouseListener(Cell cell) {
            this.cell = cell;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            // Ignore if game is over
            if (cell.isRevealed() && cell.isMine()) {
                return;
            }

            // Start game on first click
            if (firstClick) {
                for (GameListener listener : gameListeners) {
                    listener.onGameStart();
                }
            }

            if (e.getButton() == MouseEvent.BUTTON3) {
                // Right click - flag
                if (!cell.isRevealed()) {
                    if (cell.isFlagged()) {
                        cell.unflag();
                        remainingMines++;
                    } else {
                        cell.flag();
                        remainingMines--;
                    }
                    // Notify mine count change
                    for (GameListener listener : gameListeners) {
                        listener.onMineCountChanged(remainingMines);
                    }
                }
            } else if (e.getButton() == MouseEvent.BUTTON1 && !cell.isFlagged()) {
                // Left click - reveal
                if (firstClick) {
                    placeMines(cell.getRow(), cell.getCol());
                    firstClick = false;
                }

                if (cell.isMine()) {
                    cell.reveal();
                    gameOver(false);
                } else {
                    revealCell(cell.getRow(), cell.getCol());
                    checkWin();
                }
            }
        }
    }

    /**
     * Adds a game listener
     * @param listener The listener to add
     */
    public void addGameListener(GameListener listener) {
        gameListeners.add(listener);
    }

        /**
      * Gets the current board dimension (number of rows/columns)
      * @return The board dimension size (e.g., 10 for a 10x10 board)
      */
     public int getBoardDimension() {
         return rows;
     }

     /**
      * Gets the board size as a formatted string (e.g., "10x10")
      * @return The board size as a string
      */
     public String getBoardSizeString() {
         return rows + "x" + cols;
     }
     
    /**
     * Gets the current number of mines
     * @return The total number of mines
     */
    public int getMineCount() {
        return totalMines;
    }
}