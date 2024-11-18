import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
class to handle the main window, game initialization, and user interface.
 */
public class Minisweeper extends JFrame {
    private GameBoard board;
    private GameTimer gameTimer;
    private JLabel timerLabel;
    private JLabel mineCountLabel;
    private boolean gameStarted;
    private JComboBox<String> sizeSelector;

    // to initializes the main game window.
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
    }

    private void initializeComponents() {
        timerLabel = new JLabel("Time: 00:00");
        timerLabel.setFont(GameConstants.LABEL_FONT);
        timerLabel.setForeground(GameConstants.PRIMARY_COLOR);
        
        mineCountLabel = new JLabel(String.format(GameConstants.MINE_COUNT_FORMAT, 
                                                GameConstants.SMALL_BOARD_MINES));
        mineCountLabel.setFont(GameConstants.LABEL_FONT);
        mineCountLabel.setForeground(GameConstants.PRIMARY_COLOR);

        gameTimer = new GameTimer(timerLabel);

        sizeSelector = new JComboBox<>(new String[]{"10x10", "15x15"});
        comboBox(sizeSelector);

        gameStarted = false;
    }

    private void initializeGame() {
        // window properties
        setBackground(GameConstants.BACKGROUND_COLOR);
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Set up menu bar 
        setJMenuBar(createMenuBar());

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

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(GameConstants.BACKGROUND_COLOR);
        mainPanel.add(createTopPanel(), BorderLayout.NORTH);
        mainPanel.add(board, BorderLayout.CENTER);
        mainPanel.add(createStatusBar(), BorderLayout.SOUTH);
        
        setContentPane(mainPanel);

        // to center the window
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(GameConstants.BACKGROUND_COLOR);
        
        // game menu
        JMenu gameMenu = new JMenu(GameConstants.MENU_GAME);
        gameMenu.setFont(GameConstants.LABEL_FONT);
        gameMenu.setForeground(GameConstants.PRIMARY_COLOR);
        
        JMenuItem newGameItem = new JMenuItem(GameConstants.MENU_NEW_GAME);
        JMenuItem exitItem = new JMenuItem(GameConstants.MENU_EXIT);

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

    // to create top panel 
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(GameConstants.BACKGROUND_COLOR);
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        // create new game button
        JButton newGameButton = createButtons(GameConstants.NEW_GAME_BUTTON_TEXT);
        newGameButton.addActionListener(e -> startNewGame(getSelectedBoardSize()));

        topPanel.add(mineCountLabel);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(newGameButton);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(sizeSelector);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(timerLabel);

        return topPanel;
    }

    // create status bar at the bottom
    private JPanel createStatusBar() {
        JPanel statusBar = new JPanel();
        statusBar.setBackground(GameConstants.BACKGROUND_COLOR);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JLabel statusLabel = new JLabel("Left click to reveal | Right click to flag");
        statusLabel.setFont(GameConstants.LABEL_FONT);
        statusLabel.setForeground(new Color(107, 114, 128));
        statusBar.add(statusLabel);
        
        return statusBar;
    }

    private JButton createButtons(String text) {
        JButton button = new JButton(text);
        button.setFont(GameConstants.BUTTON_FONT);
        button.setBackground(GameConstants.PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        
        // to add hover effect
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

    // custom combo box
    private void comboBox(JComboBox<String> comboBox) {
        comboBox.setFont(GameConstants.LABEL_FONT);
        comboBox.setBackground(GameConstants.CELL_BACKGROUND_COLOR);
        comboBox.setForeground(GameConstants.PRIMARY_COLOR);
        ((JComponent) comboBox.getRenderer()).setOpaque(true);
        comboBox.addActionListener(e -> handleBoardSizeChange(comboBox.getSelectedItem().toString()));
    }

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
        
        JOptionPane.showMessageDialog(this,
            new JLabel(helpText.toString()),
            "Help",
            JOptionPane.INFORMATION_MESSAGE);
    }

    // to start a new game
    private void startNewGame(String size) {
        int boardSize = size.equals("10x10") ? GameConstants.SMALL_BOARD_SIZE 
                                           : GameConstants.LARGE_BOARD_SIZE;
        int mineCount = size.equals("10x10") ? GameConstants.SMALL_BOARD_MINES 
                                            : GameConstants.LARGE_BOARD_MINES;

        // to reset game state
        gameStarted = false;
        gameTimer.reset();
        
        // to remove old board and create new one
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

        ((JPanel)getContentPane()).add(board, BorderLayout.CENTER);
        updateMineCount(mineCount);
        pack();
        revalidate();
        repaint();
    }

    // to handle game over
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
            dispose();
            System.exit(0); // Exit the application
        }
    }

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

    // to update the mine count
    private void updateMineCount(int count) {
        mineCountLabel.setText(String.format(GameConstants.MINE_COUNT_FORMAT, count));
    }

    // to get the selected board size
    private String getSelectedBoardSize() {
        return (String) sizeSelector.getSelectedItem();
    }

    // to handle borad size changes
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