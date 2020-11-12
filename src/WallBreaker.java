import javax.swing.*;

public class WallBreaker extends JFrame {

    public WallBreaker(){
        initGUI();
    }

    private void initGUI() {
        add(new WBoard()); // setting the board

        setResizable(false); // cant resize the window
        pack(); // The pack() method is defined in Window class in Java and it sizes the frame so that all its contents are at or above their preferred sizes.

        setTitle("WallBreaker"); // setting the window title
        setLocationRelativeTo(null); // setting window in the middle of the display
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // finishing the thread when quiting the game

    }
}
