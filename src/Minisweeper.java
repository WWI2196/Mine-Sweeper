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
        
        // Initialize components before creating panels
        initializeComponents();
        initializeGame();
        setupKeyBindings();
    }

    /**
     * Initialize all component variables
     */
    private void initializeComponents() {
        // Initialize labels
        timerLabel = new JLabel("Time: 0");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        mineCountLabel = new JLabel(String.format(GameConstants.MINE_COUNT_FORMAT, 
                                                GameConstants.SMALL_BOARD_MINES));
        mineCountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Initialize timer
        gameTimer = new GameTimer(timerLabel);
        
        // Initialize size selector
        sizeSelector = new JComboBox<>(new String[]{"10x10", "15x15"});
        
        // Initialize game state
        gameStarted = false;
    }

    /**
     * Initializes the game components and layout.
     */
    private void initializeGame() {
        // Set up menu bar
        setJMenuBar(createMenuBar());

        // Initialize the top panel
        JPanel topPanel = createTopPanel();
        
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

        // Set up the main layout
        setLayout(new BorderLayout(5, 5));
        add(topPanel, BorderLayout.NORTH);
        add(board, BorderLayout.CENTER);

        // Add status bar
        add(createStatusBar(), BorderLayout.SOUTH);

        // Pack and center the window
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * Creates the top panel with game controls.
     */
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

        // Create mine counter
        mineCountLabel = new JLabel(String.format(GameConstants.MINE_COUNT_FORMAT, 
                                                GameConstants.SMALL_BOARD_MINES));
        mineCountLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Create new game button
        JButton newGameButton = new JButton(GameConstants.NEW_GAME_BUTTON_TEXT);
        newGameButton.addActionListener(e -> startNewGame(getSelectedBoardSize()));

        // Create board size selector
        sizeSelector = new JComboBox<>(new String[]{"10x10", "15x15"});
        sizeSelector.addActionListener(e -> handleBoardSizeChange(sizeSelector.getSelectedItem().toString()));

        // Add components to top panel
        topPanel.add(mineCountLabel);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(newGameButton);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(sizeSelector);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(timerLabel);

        return topPanel;
    }

    /**
     * Creates the menu bar for the game
     */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // Game Menu
        JMenu gameMenu = new JMenu(GameConstants.MENU_GAME);
        JMenuItem newGameItem = new JMenuItem(GameConstants.MENU_NEW_GAME);
        JMenuItem exitItem = new JMenuItem(GameConstants.MENU_EXIT);
        
        newGameItem.addActionListener(e -> startNewGame(getSelectedBoardSize()));
        exitItem.addActionListener(e -> handleGameExit());
        
        newGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, 
                                 InputEvent.CTRL_DOWN_MASK));
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 
                              InputEvent.CTRL_DOWN_MASK));
        
        gameMenu.add(newGameItem);
        gameMenu.addSeparator();
        gameMenu.add(exitItem);
        
        // Help Menu
        JMenu helpMenu = new JMenu(GameConstants.MENU_HELP);
        JMenuItem helpItem = new JMenuItem(GameConstants.MENU_HELP);
        helpItem.addActionListener(e -> showHelp());
        helpMenu.add(helpItem);
        
        menuBar.add(gameMenu);
        menuBar.add(helpMenu);
        
        return menuBar;
    }

    /**
     * Creates the status bar at the bottom of the window.
     */
    private JPanel createStatusBar() {
        JPanel statusBar = new JPanel();
        statusBar.setBorder(BorderFactory.createLoweredBevelBorder());
        statusBar.add(new JLabel("Left click to reveal | Right click to flag"));
        return statusBar;
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

        add(board, BorderLayout.CENTER);
        updateMineCount(mineCount);
        pack();
        revalidate();
    }

    /**
     * Shows the help dialog
     */
    private void showHelp() {
        StringBuilder helpText = new StringBuilder("<html><body style='width: 250px; padding: 5px;'>");
        helpText.append("<h2>How to Play Minesweeper</h2>");
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
     * Handles the game over state.
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
        } else {
            System.exit(0);
        }
    }

    /**
     * Handles the game exit request.
     */
    private void handleGameExit() {
        int choice = JOptionPane.showConfirmDialog(this,
            GameConstants.QUIT_CONFIRM_MESSAGE,
            "Quit Game",
            JOptionPane.YES_NO_OPTION);
            
        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    /**
     * Updates the mine count display.
     */
    private void updateMineCount(int count) {
        mineCountLabel.setText(String.format(GameConstants.MINE_COUNT_FORMAT, count));
    }

    /**
     * Gets the currently selected board size.
     */
    private String getSelectedBoardSize() {
        return (String) sizeSelector.getSelectedItem();
    }

    /**
     * Handles board size change events.
     */
    private void handleBoardSizeChange(String newSize) {
        if (gameStarted) {
            int choice = JOptionPane.showConfirmDialog(this,
                GameConstants.NEW_GAME_CONFIRM_MESSAGE,
                "New Game",
                JOptionPane.YES_NO_OPTION);
                
            if (choice == JOptionPane.YES_OPTION) {
                startNewGame(newSize);
            } else {
                // Reset the combo box to previous selection
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

        // New Game shortcut (Ctrl + N)
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK),
            GameConstants.ACTION_NEW_GAME);
        actionMap.put(GameConstants.ACTION_NEW_GAME, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame(getSelectedBoardSize());
            }
        });

        // Quit shortcut (Ctrl + Q)
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
     * Main method to start the application.
     */
    public static void main(String[] args) {
        try {
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new Minisweeper().setVisible(true);
        });
    }
}