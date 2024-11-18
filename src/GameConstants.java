import java.awt.Color;
import java.awt.Font;

/**
 * Contains all constant values used in the Minesweeper game.
 */
public final class GameConstants {
    // Prevent instantiation
    private GameConstants() {}

    // Board configurations
    public static final int SMALL_BOARD_SIZE = 10;
    public static final int LARGE_BOARD_SIZE = 15;
    public static final int SMALL_BOARD_MINES = 10;
    public static final int LARGE_BOARD_MINES = 20;

    // Cell dimensions and appearance
    public static final int CELL_SIZE = 40;   
    public static final int CELL_PADDING = 4;
    public static final int CELL_FONT_SIZE = 18;

    // Colors
    public static final Color PRIMARY_COLOR = new Color(79, 70, 229);    // Indigo
    public static final Color BACKGROUND_COLOR = new Color(249, 250, 251); // Light gray
    public static final Color ACCENT_COLOR = new Color(236, 72, 153);    // Pink

    public static final Color CELL_BACKGROUND_COLOR = new Color(255, 255, 255); // White
    public static final Color REVEALED_CELL_COLOR = new Color(243, 244, 246);  // Light gray
    public static final Color HOVER_CELL_COLOR = new Color(238, 242, 255);     // Light blue
    public static final Color MINE_CELL_COLOR = new Color(254, 226, 226);      // Light red
    public static final Color WRONG_FLAG_COLOR = new Color(254, 202, 202);     // Light red
    
    // Number colors for adjacent mine counts (index corresponds to number)
    public static final Color[] NUMBER_COLORS = {
        Color.GRAY,                    // 0 - unused
        new Color(59, 130, 246),       // 1 - Blue
        new Color(16, 185, 129),       // 2 - Green
        new Color(239, 68, 68),        // 3 - Red
        new Color(30, 58, 138),        // 4 - Dark blue
        new Color(157, 23, 77),        // 5 - Dark red
        new Color(14, 165, 233),       // 6 - Light blue
        new Color(31, 41, 55),         // 7 - Dark gray
        new Color(107, 114, 128)       // 8 - Gray
    };

    // Window settings
    public static final int MIN_WINDOW_WIDTH = 400;
    public static final int MIN_WINDOW_HEIGHT = 500;
    public static final int PANEL_PADDING = 5;
    public static final String GAME_TITLE = "Minesweeper";
    public static final String MINE_COUNT_FORMAT = "Mines: %d";

    // UI text
    public static final String NEW_GAME_BUTTON_TEXT = "New Game";
    public static final String QUIT_BUTTON_TEXT = "Quit";
    public static final String HELP_BUTTON_TEXT = "Help";

    // Menu items
    public static final String MENU_GAME = "Game";
    public static final String MENU_HELP = "Help";
    public static final String MENU_NEW_GAME = "New Game";
    public static final String MENU_EXIT = "Exit";
    
    // Button styling
    public static final int BUTTON_RADIUS = 8;    // Rounded corners
    public static final int BUTTON_PADDING = 12;
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    // Action commands
    public static final String ACTION_NEW_GAME = "new_game";
    public static final String ACTION_QUIT = "quit";

    // Messages
    public static final String GAME_OVER_TITLE = "Game Over";
    public static final String GAME_WON_MESSAGE = "Congratulations! You won!\nTime: %s";
    public static final String GAME_LOST_MESSAGE = "Game Over! You hit a mine!\nTime: %s";
    public static final String QUIT_CONFIRM_MESSAGE = "Are you sure you want to quit?";
    public static final String NEW_GAME_CONFIRM_MESSAGE = "Current game will be lost. Start new game?";

    // Help messages
    public static final String[] HELP_MESSAGES = {
        "Left click to reveal a cell",
        "Right click to place/remove a flag",
        "Numbers show adjacent mines",
        "Flag all mines to win",
        "First click is always safe"
    };

    // Game validation limits
    public static final int MAX_BOARD_SIZE = 50;
    public static final int MIN_BOARD_SIZE = 5;
    public static final int MAX_MINES_PERCENT = 90;
    public static final int MIN_MINES = 1;
}