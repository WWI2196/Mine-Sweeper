import java.awt.Color;

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
    public static final int CELL_SIZE = 30;
    public static final int CELL_PADDING = 2;
    public static final int CELL_FONT_SIZE = 16;

    // Colors
    public static final Color CELL_BACKGROUND_COLOR = new Color(220, 220, 220);
    public static final Color REVEALED_CELL_COLOR = new Color(190, 190, 190);
    public static final Color HOVER_CELL_COLOR = new Color(200, 200, 200);
    public static final Color MINE_CELL_COLOR = new Color(255, 150, 150);
    public static final Color WRONG_FLAG_COLOR = new Color(255, 200, 200);
    
    // Number colors for adjacent mine counts (index corresponds to number)
    public static final Color[] NUMBER_COLORS = {
        Color.GRAY,                  // 0 - unused
        new Color(0, 0, 255),        // 1 - blue
        new Color(0, 123, 0),        // 2 - green
        new Color(255, 0, 0),        // 3 - red
        new Color(0, 0, 123),        // 4 - dark blue
        new Color(123, 0, 0),        // 5 - dark red
        new Color(0, 123, 123),      // 6 - cyan
        Color.BLACK,                 // 7 - black
        new Color(123, 123, 123)     // 8 - gray
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