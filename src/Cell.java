import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/*
 Represents a single cell in the Minesweeper game.
 */
public class Cell extends JButton {
    private final int row;
    private final int col;
    private boolean isMine;
    private boolean isRevealed;
    private boolean isFlagged;
    private int adjacentMines;

    private static ImageIcon mineIcon;
    private static ImageIcon flagIcon;
    private static ImageIcon wrongIcon;
    
    
    static {
        try {
            // Load images from images folder
            mineIcon = new ImageIcon(Cell.class.getResource("/images/mine.png"));
            flagIcon = new ImageIcon(Cell.class.getResource("/images/flag.png"));
            wrongIcon = new ImageIcon(Cell.class.getResource("/images/wrong.png"));
            
            // Scale images to fit cells
            int iconSize = GameConstants.CELL_SIZE - 6; // Padding
            mineIcon = scaleIcon(mineIcon, iconSize);
            flagIcon = scaleIcon(flagIcon, iconSize);
            wrongIcon = scaleIcon(wrongIcon, iconSize);
            
        } catch (Exception e) {
            System.err.println("Error loading images: " + e.getMessage());
        }
    }

    
     // function to scale an ImageIcon to the specified size
     
    private static ImageIcon scaleIcon(ImageIcon icon, int size) {
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.isMine = false;
        this.isRevealed = false;
        this.isFlagged = false;
        this.adjacentMines = 0;

        initializeCell();
    }

    private void initializeCell() {
        int size = GameConstants.CELL_SIZE;
        setPreferredSize(new Dimension(size, size));
        setMinimumSize(new Dimension(size, size));
        setMaximumSize(new Dimension(size, size));

        setMargin(new Insets(1, 1, 1, 1));

        setFont(GameConstants.BUTTON_FONT);

        setBorderPainted(true);
        setFocusPainted(false);
        setContentAreaFilled(true);
        setBackground(GameConstants.CELL_BACKGROUND_COLOR);
        setBorder(BorderFactory.createLineBorder(GameConstants.BACKGROUND_COLOR, 1));

        // to center align the content
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);

        // to add hover effect
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!isRevealed && !isFlagged) {
                    setBackground(GameConstants.HOVER_CELL_COLOR);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!isRevealed && !isFlagged) {
                    setBackground(GameConstants.CELL_BACKGROUND_COLOR);
                }
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                            RenderingHints.VALUE_ANTIALIAS_ON);

        // to create rectangle background
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);

        // to create border
        g2d.setColor(new Color(0, 0, 0, 20));
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);

        super.paintComponent(g2d);
        g2d.dispose();
    }

    public void reveal() {
        if (!isRevealed && !isFlagged) {
            isRevealed = true;
            setBorder(BorderFactory.createLoweredBevelBorder());

            if (isMine) {
                setIcon(mineIcon);
                setBackground(GameConstants.MINE_CELL_COLOR);
            } else if (adjacentMines > 0) {
                setText(String.valueOf(adjacentMines));
                setIcon(null);
                setForeground(GameConstants.NUMBER_COLORS[adjacentMines]);
                setBackground(GameConstants.REVEALED_CELL_COLOR);
            } else {
                setText("");
                setIcon(null);
                setBackground(GameConstants.REVEALED_CELL_COLOR);
            }
        }
    }

    public void flag() {
        if (!isRevealed) {
            isFlagged = true;
            setIcon(flagIcon);
        }
    }

    public void unflag() {
        if (!isRevealed) {
            isFlagged = false;
            setIcon(null);
            setText("");
        }
    }

    public void markWrongFlag() {
        if (isFlagged && !isMine) {
            setIcon(wrongIcon);
            setBackground(GameConstants.WRONG_FLAG_COLOR);
        }
    }

    public void highlight() {
        if (!isRevealed && !isFlagged) {
            setBackground(GameConstants.HOVER_CELL_COLOR);
        }
    }

    public void unhighlight() {
        if (!isRevealed && !isFlagged) {
            setBackground(GameConstants.CELL_BACKGROUND_COLOR);
        }
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    public boolean isMine() { return isMine; }
    public void setMine(boolean mine) { isMine = mine; }
    public boolean isRevealed() { return isRevealed; }
    public boolean isFlagged() { return isFlagged; }
    public int getAdjacentMines() { return adjacentMines; }
    public void setAdjacentMines(int count) { adjacentMines = count; }
}