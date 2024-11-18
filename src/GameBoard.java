import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/*
  Handles the game board components and game logic.
 */
public class GameBoard extends JPanel {
    private Cell[][] cells;
    private final int rows;
    private final int cols;
    private final int totalMines;
    private int remainingMines;
    private boolean firstClick;
    private final List<GameListener> gameListeners;

    public interface GameListener {
        void onGameStart();
        void onGameOver(boolean won);
        void onMineCountChanged(int remainingMines);
    }

    public GameBoard(int size, int mines) {
        this.rows = size;
        this.cols = size;
        this.totalMines = mines;
        this.remainingMines = mines;
        this.firstClick = true;
        this.gameListeners = new ArrayList<>();

        initializeBoard();
    }

    private void initializeBoard() {
    // to set the gridLayout with no gaps between cells
    setLayout(new GridLayout(rows, cols, 0, 0));
    setBorder(BorderFactory.createLineBorder(GameConstants.PRIMARY_COLOR, 2));
    setBackground(GameConstants.BACKGROUND_COLOR);
    cells = new Cell[rows][cols];

    // to create cells
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            cells[i][j] = new Cell(i, j);
            cells[i][j].addMouseListener(new CellMouseListener(cells[i][j]));
            add(cells[i][j]);
        }
    }

    // to set board size based on cell dimensions
    int boardWidth = cols * GameConstants.CELL_SIZE;
    int boardHeight = rows * GameConstants.CELL_SIZE;
    setPreferredSize(new Dimension(boardWidth, boardHeight));
    setMinimumSize(new Dimension(boardWidth, boardHeight));
}

    private void placeMines(int firstRow, int firstCol) {
        Random random = new Random();
        int minesPlaced = 0;

        while (minesPlaced < totalMines) {
            int row = random.nextInt(rows);
            int col = random.nextInt(cols);

            // to avoid placing mine on first click or already mined cell
            if (!cells[row][col].isMine() && 
                (row < firstRow - 1 || row > firstRow + 1 || 
                 col < firstCol - 1 || col > firstCol + 1)) {
                cells[row][col].setMine(true);
                minesPlaced++;
            }
        }

        // to calculate numbers for all cells
        calculateNumbers();
    }

    // function to calculate numbers for the cells
    private void calculateNumbers() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!cells[i][j].isMine()) {
                    cells[i][j].setAdjacentMines(countAdjacentMines(i, j));
                }
            }
        }
    }

    // to count mines adjacent to a cell
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

    // to reveal a cell and its adjacent cells if empty
    private void revealCell(int row, int col) {
        if (!isValidCell(row, col) || cells[row][col].isRevealed() || cells[row][col].isFlagged()) {
            return;
        }

        cells[row][col].reveal();

        if (cells[row][col].getAdjacentMines() == 0) {
            // to reveal all adjacent cells for empty cell
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    revealCell(row + i, col + j);
                }
            }
        }
    }

    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    //to checks if the game is won
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

    // to handle game over 
    private void gameOver(boolean won) {
        // Reveal all cells
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (won && cells[i][j].isMine()) {
                    cells[i][j].flag(); // to flag all mines on win
                } else {
                    cells[i][j].reveal();
                    //to mark wrongly flagged cells with X
                    if (!won && cells[i][j].isFlagged() && !cells[i][j].isMine()) {
                        cells[i][j].markWrongFlag();
                    }
                }
            }
        }

        for (GameListener listener : gameListeners) {
            listener.onGameOver(won);
        }
    }

    private class CellMouseListener extends MouseAdapter {
        private final Cell cell;

        public CellMouseListener(Cell cell) {
            this.cell = cell;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            // to ignore mouse clicks if game is over
            if (cell.isRevealed() && cell.isMine()) {
                return;
            }

            // to start game on first click
            if (firstClick) {
                for (GameListener listener : gameListeners) {
                    listener.onGameStart();
                }
            }

            if (e.getButton() == MouseEvent.BUTTON3) {
                // right click - flag
                if (!cell.isRevealed()) {
                    if (cell.isFlagged()) {
                        cell.unflag();
                        remainingMines++;
                    } else {
                        cell.flag();
                        remainingMines--;
                    }
                    // to notify mine count change
                    for (GameListener listener : gameListeners) {
                        listener.onMineCountChanged(remainingMines);
                    }
                }
            } else if (e.getButton() == MouseEvent.BUTTON1 && !cell.isFlagged()) {
                // left click - reveal
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

    public void addGameListener(GameListener listener) {
        gameListeners.add(listener);
    }

     public int getBoardDimension() {
         return rows;
     }

     public String getBoardSizeString() {
         return rows + "x" + cols;
     }

    public int getMineCount() {
        return totalMines;
    }
}