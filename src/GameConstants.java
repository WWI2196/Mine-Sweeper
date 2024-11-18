import java.awt.Color;
import java.awt.Font;

/*
  class to contain all constant values used in the game
 */
public final class GameConstants {
    // to prevent instantiation
    private GameConstants() {}

    public static final int SMALL_BOARD_SIZE = 10;
    public static final int LARGE_BOARD_SIZE = 15;
    public static final int SMALL_BOARD_MINES = 10;
    public static final int LARGE_BOARD_MINES = 20;

    public static final int CELL_SIZE = 40;   
    public static final int CELL_PADDING = 4;
    public static final int CELL_FONT_SIZE = 18;

    public static final Color PRIMARY_COLOR = new Color(79, 70, 229);    // indigo
    public static final Color BACKGROUND_COLOR = new Color(249, 250, 251); // light gray
    public static final Color ACCENT_COLOR = new Color(236, 72, 153);    // pink

    public static final Color CELL_BACKGROUND_COLOR = new Color(255, 255, 255); // white
    public static final Color REVEALED_CELL_COLOR = new Color(243, 244, 246);  // light gray
    public static final Color HOVER_CELL_COLOR = new Color(238, 242, 255);     // light blue
    public static final Color MINE_CELL_COLOR = new Color(254, 226, 226);      // light red
    public static final Color WRONG_FLAG_COLOR = new Color(254, 202, 202);     // light red

    public static final Color[] NUMBER_COLORS = {
        Color.GRAY,                    
        new Color(59, 130, 246),       // 1 - blue
        new Color(16, 185, 129),       // 2 - green
        new Color(239, 68, 68),        // 3 - red
        new Color(30, 58, 138),        // 4 - dark blue
        new Color(157, 23, 77),        // 5 - dark red
        new Color(14, 165, 233),       // 6 - light blue
        new Color(31, 41, 55),         // 7 - dark gray
        new Color(107, 114, 128)       // 8 - gray
    };

    public static final int MIN_WINDOW_WIDTH = 400;
    public static final int MIN_WINDOW_HEIGHT = 500;
    public static final int PANEL_PADDING = 5;
    public static final String GAME_TITLE = "Minesweeper";
    public static final String MINE_COUNT_FORMAT = "Mines: %d";

    public static final String NEW_GAME_BUTTON_TEXT = "New Game";
    public static final String QUIT_BUTTON_TEXT = "Quit";
    public static final String HELP_BUTTON_TEXT = "Help";

    public static final String MENU_GAME = "Game";
    public static final String MENU_HELP = "Help";
    public static final String MENU_NEW_GAME = "New Game";
    public static final String MENU_EXIT = "Exit";

    public static final int BUTTON_RADIUS = 8;    // Rounded corners
    public static final int BUTTON_PADDING = 12;
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    public static final String ACTION_NEW_GAME = "new_game";
    public static final String ACTION_QUIT = "quit";

    public static final String GAME_OVER_TITLE = "Game Over";
    public static final String GAME_WON_MESSAGE = "Congratulations! You won!\nTime: %s";
    public static final String GAME_LOST_MESSAGE = "Game Over! You hit a mine!\nTime: %s";
    public static final String QUIT_CONFIRM_MESSAGE = "Are you sure you want to quit?";
    public static final String NEW_GAME_CONFIRM_MESSAGE = "Current game will be lost. Start new game?";

    public static final String[] HELP_MESSAGES = {
        "Left click to reveal a cell",
        "Right click to place/remove a flag",
        "Numbers show adjacent mines",
        "Flag all mines to win",
        "First click is always safe"
    };

    public static final int MAX_BOARD_SIZE = 50;
    public static final int MIN_BOARD_SIZE = 5;
    public static final int MAX_MINES_PERCENT = 90;
    public static final int MIN_MINES = 1;
}