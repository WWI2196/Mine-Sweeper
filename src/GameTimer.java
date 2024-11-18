import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.Font;

/**
 * Handles the game timer functionality with improved display format.
 */
public class GameTimer {
    private Timer timer;
    private int seconds;
    private final JLabel display;
    private boolean isRunning;

    /**
     * Constructor for GameTimer
     * @param display JLabel to show the timer value
     */
    public GameTimer(JLabel display) {
        this.display = display;
        this.seconds = 0;
        this.isRunning = false;
        
        // Set font and initial display
        display.setFont(new Font("Arial", Font.BOLD, 14));
        initializeTimer();
        updateDisplay();
    }

    /**
     * Initializes the Swing timer
     */
    private void initializeTimer() {
        ActionListener timerAction = e -> {
            seconds++;
            updateDisplay();
        };
        timer = new Timer(1000, timerAction);
        timer.setInitialDelay(0);
    }

    /**
     * Updates the timer display in MM:SS format
     */
    private void updateDisplay() {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        display.setText(String.format("Time: %02d:%02d", minutes, remainingSeconds));
    }

    /**
     * Starts the timer
     */
    public void start() {
        if (!isRunning) {
            timer.start();
            isRunning = true;
        }
    }

    /**
     * Stops the timer
     */
    public void stop() {
        if (isRunning) {
            timer.stop();
            isRunning = false;
        }
    }

    /**
     * Resets the timer to zero
     */
    public void reset() {
        stop();
        seconds = 0;
        updateDisplay();
    }

    /**
     * Gets formatted time string (MM:SS)
     * @return formatted time string
     */
    public String getFormattedTime() {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }

    /**
     * Checks if timer is currently running
     * @return true if timer is running
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Pauses the timer
     */
    public void pause() {
        if (isRunning) {
            timer.stop();
            isRunning = false;
        }
    }

    /**
     * Resumes the timer from where it was paused
     */
    public void resume() {
        if (!isRunning) {
            timer.start();
            isRunning = true;
        }
    }

    /**
     * Cleans up timer resources
     */
    public void dispose() {
        stop();
        timer = null;
    }
}