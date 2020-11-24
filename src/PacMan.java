import javax.swing.*;
import java.io.IOException;

public class PacMan extends JFrame {

    public PacMan() throws IOException {
        initGUI();

    }

    private void initGUI() throws IOException {

        add(new PacManBoard(1)); // setting the board

        setResizable(false); // cant resize the window
        pack(); // The pack() method is defined in Window class in Java and it sizes the frame so that all its contents are at or above their preferred sizes.

        setTitle("PacMan"); // setting the window title
        setLocationRelativeTo(null); // setting window in the middle of the display
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // finishing the thread when quiting the game


    }
}
