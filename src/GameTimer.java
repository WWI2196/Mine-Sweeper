import javax.swing.*;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

/**
 * Handles the game timer functionality.
 * Provides methods to start, stop, and reset the timer,
 * and updates the display in the game UI.
 */
public class GameTimer {
    private Timer timer;
    private int seconds;
    private final JLabel display;
    private final DecimalFormat formatter;
    private boolean isRunning;

    /**
     * Constructor for GameTimer
     * @param display JLabel to show the timer value
     */
    public GameTimer(JLabel display) {
        this.display = display;
        this.seconds = 0;
        this.isRunning = false;
        this.formatter = new DecimalFormat("000");
        
        initializeTimer();
    }

    /**
     * Initializes the Swing timer
     */
    private void initializeTimer() {
        ActionListener timerAction = e -> {
            seconds++;
            updateDisplay();
        };
        
        timer = new Timer(1000, timerAction); // Fires every 1000ms (1 second)
        timer.setInitialDelay(0);
    }

    /**
     * Updates the timer display
     */
    private void updateDisplay() {
        String timeStr = formatter.format(seconds);
        display.setText("Time: " + timeStr);
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
     * Gets the current elapsed time in seconds
     * @return elapsed seconds
     */
    public int getElapsedSeconds() {
        return seconds;
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
     * Sets a custom time format
     * @param pattern DecimalFormat pattern string
     */
    public void setTimeFormat(String pattern) {
        formatter.applyPattern(pattern);
        updateDisplay();
    }

    /**
     * Adds time change listener
     * @param listener TimeChangeListener implementation
     */
    public void addTimeChangeListener(TimeChangeListener listener) {
        timer.addActionListener(e -> listener.onTimeChanged(seconds));
    }

    /**
     * Interface for time change events
     */
    public interface TimeChangeListener {
        void onTimeChanged(int newSeconds);
    }

    /**
     * Sets a time limit for the game
     * @param timeLimit time limit in seconds
     * @param listener TimeLimitListener implementation
     */
    public void setTimeLimit(int timeLimit, TimeLimitListener listener) {
        ActionListener timeLimitChecker = e -> {
            if (seconds >= timeLimit) {
                stop();
                listener.onTimeLimitReached();
            }
        };
        Timer limitTimer = new Timer(1000, timeLimitChecker);
        limitTimer.start();
    }

    /**
     * Interface for time limit events
     */
    public interface TimeLimitListener {
        void onTimeLimitReached();
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
     * Adds or subtracts time from the timer
     * @param secondsToAdd seconds to add (negative to subtract)
     */
    public void addTime(int secondsToAdd) {
        seconds = Math.max(0, seconds + secondsToAdd);
        updateDisplay();
    }

    /**
     * Sets the timer to a specific value
     * @param newSeconds seconds to set the timer to
     */
    public void setTime(int newSeconds) {
        if (newSeconds >= 0) {
            seconds = newSeconds;
            updateDisplay();
        }
    }

    /**
     * Changes the timer update interval
     * @param milliseconds new interval in milliseconds
     */
    public void setUpdateInterval(int milliseconds) {
        if (milliseconds > 0) {
            timer.setDelay(milliseconds);
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