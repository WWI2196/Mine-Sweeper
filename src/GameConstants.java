import java.awt.Color;

/**
 * Contains all constant values used in the Minesweeper game.
 * This includes board sizes, colors, and other game configuration values.
 */
public final class GameConstants {
    // Prevent instantiation
    private GameConstants() {}

    // Board configurations
    public static final int SMALL_BOARD_SIZE = 10;
    public static final int LARGE_BOARD_SIZE = 15;
    public static final int SMALL_BOARD_MINES = 10;
    public static final int LARGE_BOARD_MINES = 20;

    // Cell dimensions
    public static final int CELL_SIZE = 30;
    public static final int CELL_PADDING = 2;
    public static final int CELL_FONT_SIZE = 16;

    // Game window settings
    public static final int MIN_WINDOW_WIDTH = 400;
    public static final int MIN_WINDOW_HEIGHT = 500;
    public static final int PANEL_PADDING = 5;
    public static final String GAME_TITLE = "Minesweeper";

    // Timer settings
    public static final int TIMER_DELAY = 1000; // milliseconds
    public static final String TIME_FORMAT = "Time: %03d";
    public static final String MINE_COUNT_FORMAT = "Mines: %03d";

    // Cell colors
    public static final Color CELL_BACKGROUND_COLOR = new Color(220, 220, 220);
    public static final Color REVEALED_CELL_COLOR = new Color(190, 190, 190);
    public static final Color HOVER_CELL_COLOR = new Color(200, 200, 200);
    public static final Color MINE_CELL_COLOR = new Color(255, 150, 150);
    public static final Color WRONG_FLAG_COLOR = new Color(255, 200, 200);
    public static final Color SAFE_REVEAL_COLOR = new Color(150, 255, 150);

    // Number colors for adjacent mine counts
    public static final Color[] NUMBER_COLORS = {
        Color.GRAY,      // 0 - unused
        new Color(0, 0, 255),      // 1 - blue
        new Color(0, 123, 0),      // 2 - green
        new Color(255, 0, 0),      // 3 - red
        new Color(0, 0, 123),      // 4 - dark blue
        new Color(123, 0, 0),      // 5 - dark red
        new Color(0, 123, 123),    // 6 - cyan
        new Color(0, 0, 0),        // 7 - black
        new Color(123, 123, 123)   // 8 - gray
    };

    // Game UI text
    public static final String NEW_GAME_BUTTON_TEXT = "New Game";
    public static final String FLAG_MODE_BUTTON_TEXT = "Flag Mode";
    public static final String QUIT_BUTTON_TEXT = "Quit";
    public static final String HELP_BUTTON_TEXT = "Help";

    // Dialog messages
    public static final String GAME_OVER_TITLE = "Game Over";
    public static final String GAME_WON_MESSAGE = "Congratulations! You won!\nTime: %s";
    public static final String GAME_LOST_MESSAGE = "Game Over! You hit a mine!\nTime: %s";
    public static final String QUIT_CONFIRM_MESSAGE = "Are you sure you want to quit?";
    public static final String NEW_GAME_CONFIRM_MESSAGE = "Current game will be lost. Start new game?";

    // Cell symbols
    public static final String MINE_SYMBOL = "💣";
    public static final String FLAG_SYMBOL = "🚩";
    public static final String WRONG_FLAG_SYMBOL = "❌";
    public static final String QUESTION_MARK_SYMBOL = "?";

    // Menu items
    public static final String MENU_GAME = "Game";
    public static final String MENU_OPTIONS = "Options";
    public static final String MENU_HELP = "Help";
    public static final String MENU_NEW_GAME = "New Game";
    public static final String MENU_STATISTICS = "Statistics";
    public static final String MENU_EXIT = "Exit";

    // Key bindings
    public static final String ACTION_NEW_GAME = "new_game";
    public static final String ACTION_QUIT = "quit";
    public static final String ACTION_TOGGLE_FLAG = "toggle_flag";
    public static final String ACTION_REVEAL = "reveal";

    // Status bar messages
    public static final String STATUS_READY = "Ready to play";
    public static final String STATUS_PLAYING = "Game in progress";
    public static final String STATUS_GAME_OVER = "Game Over";
    public static final String STATUS_VICTORY = "Victory!";

    // Help text
    public static final String[] HELP_MESSAGES = {
        "Left click to reveal a cell",
        "Right click to place/remove a flag",
        "Numbers show adjacent mines",
        "Flag all mines to win",
        "First click is always safe"
    };

    // Difficulty settings
    public static enum Difficulty {
        BEGINNER(10, 10),
        INTERMEDIATE(15, 20);

        private final int boardSize;
        private final int mineCount;

        Difficulty(int boardSize, int mineCount) {
            this.boardSize = boardSize;
            this.mineCount = mineCount;
        }

        public int getBoardSize() {
            return boardSize;
        }

        public int getMineCount() {
            return mineCount;
        }
    }

    // Animation durations (milliseconds)
    public static final int REVEAL_ANIMATION_DURATION = 100;
    public static final int FLAG_ANIMATION_DURATION = 100;
    public static final int GAME_OVER_ANIMATION_DURATION = 500;


    // Performance settings
    public static final int MAX_BOARD_SIZE = 50;
    public static final int MIN_BOARD_SIZE = 5;
    public static final int MAX_MINES_PERCENT = 90;
    public static final int MIN_MINES = 1;
}