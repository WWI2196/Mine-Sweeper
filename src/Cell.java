import javax.swing.*;
import java.awt.*;

/**
 * Represents a single cell in the Minesweeper game.
 * Extends JButton to provide clickable functionality.
 */
public class Cell extends JButton {
    private final int row;
    private final int col;
    private boolean isMine;
    private boolean isRevealed;
    private boolean isFlagged;
    private int adjacentMines;

    /**
     * Constructor for Cell
     * @param row Row position in the grid
     * @param col Column position in the grid
     */
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.isMine = false;
        this.isRevealed = false;
        this.isFlagged = false;
        this.adjacentMines = 0;

        initializeCell();
    }

    /**
     * Initializes the cell's visual properties
     */
    private void initializeCell() {
        setPreferredSize(new Dimension(GameConstants.CELL_SIZE, GameConstants.CELL_SIZE));
        setMargin(new Insets(0, 0, 0, 0));
        setFont(new Font("Arial", Font.BOLD, 16));
        setBorder(BorderFactory.createRaisedBevelBorder());
        setFocusPainted(false);
        setBackground(GameConstants.CELL_BACKGROUND_COLOR);
    }

    /**
     * Reveals the cell's content
     */
    public void reveal() {
        if (!isRevealed && !isFlagged) {
            isRevealed = true;
            setBorder(BorderFactory.createLoweredBevelBorder());

            if (isMine) {
                // Show mine
                setBackground(GameConstants.MINE_CELL_COLOR);
                setText("💣");
            } else if (adjacentMines > 0) {
                // Show number
                setText(String.valueOf(adjacentMines));
                setForeground(GameConstants.NUMBER_COLORS[adjacentMines]);
                setBackground(GameConstants.REVEALED_CELL_COLOR);
            } else {
                // Empty cell
                setText("");
                setBackground(GameConstants.REVEALED_CELL_COLOR);
            }
        }
    }

    /**
     * Flags the cell as potentially containing a mine
     */
    public void flag() {
        if (!isRevealed) {
            isFlagged = true;
            setText("🚩");
            setForeground(Color.RED);
        }
    }

    /**
     * Removes the flag from the cell
     */
    public void unflag() {
        if (!isRevealed) {
            isFlagged = false;
            setText("");
        }
    }

    /**
     * Highlights the cell when the mouse is over it
     */
    public void highlight() {
        if (!isRevealed && !isFlagged) {
            setBackground(GameConstants.HOVER_CELL_COLOR);
        }
    }

    /**
     * Removes the highlight from the cell
     */
    public void unhighlight() {
        if (!isRevealed && !isFlagged) {
            setBackground(GameConstants.CELL_BACKGROUND_COLOR);
        }
    }

    /**
     * Marks the cell as a wrongly flagged mine (for game over)
     */
    public void markWrongFlag() {
        if (isFlagged && !isMine) {
            setText("❌");
            setBackground(GameConstants.WRONG_FLAG_COLOR);
        }
    }

    // Getters and setters
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public int getAdjacentMines() {
        return adjacentMines;
    }

    public void setAdjacentMines(int count) {
        adjacentMines = count;
    }

    /**
     * Creates a visual effect when the cell is clicked
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Add a slight 3D effect for unrevealed cells
        if (!isRevealed) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                                RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Add highlight on top and left edges
            g2d.setColor(Color.WHITE);
            g2d.drawLine(0, 0, getWidth() - 1, 0);
            g2d.drawLine(0, 0, 0, getHeight() - 1);
            
            // Add shadow on bottom and right edges
            g2d.setColor(Color.GRAY);
            g2d.drawLine(1, getHeight() - 1, getWidth() - 1, getHeight() - 1);
            g2d.drawLine(getWidth() - 1, 1, getWidth() - 1, getHeight() - 1);
        }
    }

    /**
     * Custom UI drawing for different cell states
     */
    @Override
    public void paintBorder(Graphics g) {
        if (!isRevealed) {
            super.paintBorder(g);
        } else {
            // Flat border for revealed cells
            g.setColor(Color.GRAY);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
    }
}