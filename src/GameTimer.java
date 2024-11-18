import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.Font;

/*
  class to handle the game timer .
 */
public class GameTimer {
    private Timer timer;
    private int seconds;
    private final JLabel display;
    private boolean isRunning;

    public GameTimer(JLabel display) {
        this.display = display;
        this.seconds = 0;
        this.isRunning = false;
        
        // to set the font and initial display
        display.setFont(new Font("Arial", Font.BOLD, 14));
        initializeTimer();
        updateDisplay();
    }

    private void initializeTimer() {
        ActionListener timerAction = e -> {
            seconds++;
            updateDisplay();
        };
        timer = new Timer(1000, timerAction);
        timer.setInitialDelay(0);
    }

    // to set the timer MM:SS format
    private void updateDisplay() {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        display.setText(String.format("Time: %02d:%02d", minutes, remainingSeconds));
    }

    // to start the timer
    public void start() {
        if (!isRunning) {
            timer.start();
            isRunning = true;
        }
    }

    //to stop the timer
    public void stop() {
        if (isRunning) {
            timer.stop();
            isRunning = false;
        }
    }

    // to reset the timer
    public void reset() {
        stop();
        seconds = 0;
        updateDisplay();
    }

    public String getFormattedTime() {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }

    public boolean isRunning() {
        return isRunning;
    }

    // to pause the timer
    public void pause() {
        if (isRunning) {
            timer.stop();
            isRunning = false;
        }
    }

    // to resume the timer
    public void resume() {
        if (!isRunning) {
            timer.start();
            isRunning = true;
        }
    }

    public void dispose() {
        stop();
        timer = null;
    }
}