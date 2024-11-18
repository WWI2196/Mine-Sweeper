import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

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

    // Image icons for cell states
    private static ImageIcon mineIcon;
    private static ImageIcon flagIcon;
    private static ImageIcon wrongIcon;
    
    // Static initialization of images
    static {
        try {
            // Load images from resources
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
            // Create simple colored shapes as fallback
            mineIcon = createFallbackIcon("*", Color.BLACK);
            flagIcon = createFallbackIcon("F", Color.RED);
            wrongIcon = createFallbackIcon("X", Color.RED);
        }
    }

    /**
     * Scales an ImageIcon to the specified size
     */
    private static ImageIcon scaleIcon(ImageIcon icon, int size) {
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }

    /**
     * Creates a fallback icon with text if images fail to load
     */
    private static ImageIcon createFallbackIcon(String text, Color color) {
        int size = GameConstants.CELL_SIZE - 6;
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(new Font("Arial", Font.BOLD, size - 4));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(size, size));
        
        BufferedImage image = new BufferedImage(
            size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        label.paint(g2d);
        g2d.dispose();
        
        return new ImageIcon(image);
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

        // Use smaller padding for better fit
        setMargin(new Insets(1, 1, 1, 1));

        setFont(GameConstants.BUTTON_FONT);

        // Modern flat design
        setBorderPainted(true);
        setFocusPainted(false);
        setContentAreaFilled(true);
        setBackground(GameConstants.CELL_BACKGROUND_COLOR);
        setBorder(BorderFactory.createLineBorder(GameConstants.BACKGROUND_COLOR, 1));

        // Center-align content
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);

        // Add hover effect
        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (!isRevealed && !isFlagged) {
                    setBackground(GameConstants.HOVER_CELL_COLOR);
                }
            }

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

        // Draw rounded rectangle background
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);

        // Draw subtle border
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

    // Getters and setters
    public int getRow() { return row; }
    public int getCol() { return col; }
    public boolean isMine() { return isMine; }
    public void setMine(boolean mine) { isMine = mine; }
    public boolean isRevealed() { return isRevealed; }
    public boolean isFlagged() { return isFlagged; }
    public int getAdjacentMines() { return adjacentMines; }
    public void setAdjacentMines(int count) { adjacentMines = count; }
}