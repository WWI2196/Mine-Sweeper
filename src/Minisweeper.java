import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Main class for the Minesweeper game.
 * Handles the main window, game initialization, and user interface.
 */
public class Minisweeper extends JFrame {
    private GameBoard board;
    private GameTimer gameTimer;
    private JLabel timerLabel;
    private JLabel mineCountLabel;
    private boolean gameStarted;
    private JComboBox<String> sizeSelector;

    /**
     * Constructor initializes the main game window.
     */
    public Minisweeper() {
        setTitle(GameConstants.GAME_TITLE);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleGameExit();
            }
        });
        
        initializeComponents();
        initializeGame();
        setupKeyBindings();
    }

    /**
     * Initialize all component variables with modern styling
     */
    private void initializeComponents() {
        // Initialize labels with modern styling
        timerLabel = new JLabel("Time: 00:00");
        timerLabel.setFont(GameConstants.LABEL_FONT);
        timerLabel.setForeground(GameConstants.PRIMARY_COLOR);
        
        mineCountLabel = new JLabel(String.format(GameConstants.MINE_COUNT_FORMAT, 
                                                GameConstants.SMALL_BOARD_MINES));
        mineCountLabel.setFont(GameConstants.LABEL_FONT);
        mineCountLabel.setForeground(GameConstants.PRIMARY_COLOR);
        
        // Initialize timer
        gameTimer = new GameTimer(timerLabel);
        
        // Initialize size selector with modern styling
        sizeSelector = new JComboBox<>(new String[]{"10x10", "15x15"});
        styleComboBox(sizeSelector);
        
        // Initialize game state
        gameStarted = false;
    }

    /**
     * Initializes the game components and layout with modern styling.
     */
    private void initializeGame() {
        // Set window properties
        setBackground(GameConstants.BACKGROUND_COLOR);
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Set up menu bar with modern styling
        setJMenuBar(createModernMenuBar());

        // Initialize the game board with default size
        board = new GameBoard(GameConstants.SMALL_BOARD_SIZE, GameConstants.SMALL_BOARD_MINES);
        board.addGameListener(new GameBoard.GameListener() {
            @Override
            public void onGameStart() {
                if (!gameStarted) {
                    gameStarted = true;
                    gameTimer.start();
                }
            }

            @Override
            public void onGameOver(boolean won) {
                handleGameOver(won);
            }

            @Override
            public void onMineCountChanged(int remainingMines) {
                updateMineCount(remainingMines);
            }
        });

        // Create main panel with modern styling
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(GameConstants.BACKGROUND_COLOR);
        mainPanel.add(createModernTopPanel(), BorderLayout.NORTH);
        mainPanel.add(board, BorderLayout.CENTER);
        mainPanel.add(createModernStatusBar(), BorderLayout.SOUTH);
        
        setContentPane(mainPanel);

        // Pack and center the window
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * Creates modern styled menu bar
     */
    private JMenuBar createModernMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(GameConstants.BACKGROUND_COLOR);
        
        // Game Menu
        JMenu gameMenu = new JMenu(GameConstants.MENU_GAME);
        gameMenu.setFont(GameConstants.LABEL_FONT);
        gameMenu.setForeground(GameConstants.PRIMARY_COLOR);
        
        JMenuItem newGameItem = new JMenuItem(GameConstants.MENU_NEW_GAME);
        JMenuItem exitItem = new JMenuItem(GameConstants.MENU_EXIT);
        
        // Style menu items
        for (JMenuItem item : new JMenuItem[]{newGameItem, exitItem}) {
            item.setFont(GameConstants.LABEL_FONT);
            item.setBackground(GameConstants.BACKGROUND_COLOR);
        }
        
        newGameItem.addActionListener(e -> startNewGame(getSelectedBoardSize()));
        exitItem.addActionListener(e -> handleGameExit());
        
        newGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
        
        gameMenu.add(newGameItem);
        gameMenu.addSeparator();
        gameMenu.add(exitItem);
        
        // Help Menu
        JMenu helpMenu = new JMenu(GameConstants.MENU_HELP);
        helpMenu.setFont(GameConstants.LABEL_FONT);
        helpMenu.setForeground(GameConstants.PRIMARY_COLOR);
        
        JMenuItem helpItem = new JMenuItem(GameConstants.MENU_HELP);
        helpItem.setFont(GameConstants.LABEL_FONT);
        helpItem.setBackground(GameConstants.BACKGROUND_COLOR);
        helpItem.addActionListener(e -> showHelp());
        helpMenu.add(helpItem);
        
        menuBar.add(gameMenu);
        menuBar.add(helpMenu);
        
        return menuBar;
    }

    /**
     * Creates the modern top panel with game controls
     */
    private JPanel createModernTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(GameConstants.BACKGROUND_COLOR);
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        // Create modern new game button
        JButton newGameButton = createStyledButton(GameConstants.NEW_GAME_BUTTON_TEXT);
        newGameButton.addActionListener(e -> startNewGame(getSelectedBoardSize()));

        // Add components with modern spacing
        topPanel.add(mineCountLabel);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(newGameButton);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(sizeSelector);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(timerLabel);

        return topPanel;
    }

    /**
     * Creates modern status bar
     */
    private JPanel createModernStatusBar() {
        JPanel statusBar = new JPanel();
        statusBar.setBackground(GameConstants.BACKGROUND_COLOR);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JLabel statusLabel = new JLabel("Left click to reveal | Right click to flag");
        statusLabel.setFont(GameConstants.LABEL_FONT);
        statusLabel.setForeground(new Color(107, 114, 128));
        statusBar.add(statusLabel);
        
        return statusBar;
    }

    /**
     * Creates modern styled button
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(GameConstants.BUTTON_FONT);
        button.setBackground(GameConstants.PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(GameConstants.PRIMARY_COLOR.darker());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(GameConstants.PRIMARY_COLOR);
            }
        });

        return button;
    }

    /**
     * Styles combo box with modern look
     */
    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(GameConstants.LABEL_FONT);
        comboBox.setBackground(GameConstants.CELL_BACKGROUND_COLOR);
        comboBox.setForeground(GameConstants.PRIMARY_COLOR);
        ((JComponent) comboBox.getRenderer()).setOpaque(true);
        comboBox.addActionListener(e -> handleBoardSizeChange(comboBox.getSelectedItem().toString()));
    }

    /**
     * Shows the help dialog with modern styling
     */
    private void showHelp() {
        StringBuilder helpText = new StringBuilder("<html><body style='width: 250px; padding: 5px;'>");
        helpText.append("<div style='color: ").append(String.format("#%02x%02x%02x", 
                GameConstants.PRIMARY_COLOR.getRed(),
                GameConstants.PRIMARY_COLOR.getGreen(),
                GameConstants.PRIMARY_COLOR.getBlue())).append(";'>");
        helpText.append("<h2>How to Play Minesweeper</h2></div>");
        helpText.append("<ul>");
        for (String message : GameConstants.HELP_MESSAGES) {
            helpText.append("<li>").append(message).append("</li>");
        }
        helpText.append("</ul>");
        helpText.append("<h3>Keyboard Shortcuts:</h3>");
        helpText.append("<ul>");
        helpText.append("<li>Ctrl + N: New Game</li>");
        helpText.append("<li>Ctrl + Q: Quit Game</li>");
        helpText.append("</ul>");
        helpText.append("</body></html>");

        JOptionPane.showMessageDialog(this,
            new JLabel(helpText.toString()),
            "Help",
            JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Starts a new game with the specified board size.
     */
    private void startNewGame(String size) {
        int boardSize = size.equals("10x10") ? GameConstants.SMALL_BOARD_SIZE 
                                           : GameConstants.LARGE_BOARD_SIZE;
        int mineCount = size.equals("10x10") ? GameConstants.SMALL_BOARD_MINES 
                                            : GameConstants.LARGE_BOARD_MINES;

        // Reset game state
        gameStarted = false;
        gameTimer.reset();
        
        // Remove old board and create new one
        remove(board);
        board = new GameBoard(boardSize, mineCount);
        board.addGameListener(new GameBoard.GameListener() {
            @Override
            public void onGameStart() {
                if (!gameStarted) {
                    gameStarted = true;
                    gameTimer.start();
                }
            }

            @Override
            public void onGameOver(boolean won) {
                handleGameOver(won);
            }

            @Override
            public void onMineCountChanged(int remainingMines) {
                updateMineCount(remainingMines);
            }
        });

        // Update UI
        ((JPanel)getContentPane()).add(board, BorderLayout.CENTER);
        updateMineCount(mineCount);
        pack();
        revalidate();
        repaint();
    }

    /**
     * Handles the game over state with modern dialog
     */
    private void handleGameOver(boolean won) {
    gameTimer.stop();
    String message = won ? 
        String.format(GameConstants.GAME_WON_MESSAGE, gameTimer.getFormattedTime()) :
        String.format(GameConstants.GAME_LOST_MESSAGE, gameTimer.getFormattedTime());
    
    
    
    Object[] options = {"New Game", "Quit"};
    int choice = JOptionPane.showOptionDialog(this,
        message,
        GameConstants.GAME_OVER_TITLE,
        JOptionPane.YES_NO_OPTION,
        JOptionPane.INFORMATION_MESSAGE,
        null,
        options,
        options[0]);

    if (choice == 0) {
        startNewGame(getSelectedBoardSize());
    } else if (choice == 1 || choice == JOptionPane.CLOSED_OPTION) {
        dispose(); // Properly dispose of the window
        System.exit(0); // Exit the application
    }
}

    /**
     * Handles the game exit request with modern dialog
     */
    private void handleGameExit() {
        UIManager.put("OptionPane.background", GameConstants.BACKGROUND_COLOR);
        UIManager.put("Panel.background", GameConstants.BACKGROUND_COLOR);
        
        int choice = JOptionPane.showConfirmDialog(this,
            GameConstants.QUIT_CONFIRM_MESSAGE,
            "Quit Game",
            JOptionPane.YES_NO_OPTION);
            
        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    /**
     * Updates the mine count display
     */
    private void updateMineCount(int count) {
        mineCountLabel.setText(String.format(GameConstants.MINE_COUNT_FORMAT, count));
    }

    /**
     * Gets the currently selected board size
     */
    private String getSelectedBoardSize() {
        return (String) sizeSelector.getSelectedItem();
    }

    /**
     * Handles board size change events
     */
    private void handleBoardSizeChange(String newSize) {
        if (gameStarted) {
            UIManager.put("OptionPane.background", GameConstants.BACKGROUND_COLOR);
            UIManager.put("Panel.background", GameConstants.BACKGROUND_COLOR);
            
            int choice = JOptionPane.showConfirmDialog(this,
                GameConstants.NEW_GAME_CONFIRM_MESSAGE,
                "New Game",
                JOptionPane.YES_NO_OPTION);
                
            if (choice == JOptionPane.YES_OPTION) {
                startNewGame(newSize);
            } else {
                sizeSelector.setSelectedItem(board.getBoardSizeString());
            }
        } else {
            startNewGame(newSize);
        }
    }

    /**
     * Sets up keyboard shortcuts
     */
    private void setupKeyBindings() {
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getRootPane().getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK),
            GameConstants.ACTION_NEW_GAME);
        actionMap.put(GameConstants.ACTION_NEW_GAME, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame(getSelectedBoardSize());
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK),
            GameConstants.ACTION_QUIT);
        actionMap.put(GameConstants.ACTION_QUIT, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleGameExit();
            }
        });
    }

    /**
     * Main method to start the application
     * @param args
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
        }

        SwingUtilities.invokeLater(() -> {
            new Minisweeper().setVisible(true);
        });
    }
}