import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

/*
this project represents the childhood game MineSweeper , including option to choose size of board and high scores.
also Snake game , Hang man game and WallBreaker game.
 */
public class Main {
    public static void main(String[] args) throws IOException {

//        MsGame game = new MsGame(15);
//        game.setVisible(true);
//        GameChoose g = new GameChoose();
//        g.setVisible(true);
//        HmGame hmGame = new HmGame(Level.HARD);
//        hmGame.setVisible(true);
//        EventQueue.invokeLater(() -> {
//            JFrame ex = new SnakeGame();
//            ex.setVisible(true);
//        });
//          SnakeGame snakeGame = new SnakeGame();
//        snakeGame.setVisible(true);
////
//        WallBreaker wallBreaker = new WallBreaker();
//        wallBreaker.setVisible(true);
//        TicTacToe ticTacToe = new TicTacToe();
//        ticTacToe.setVisible(true);
        PacMan pacMan = new PacMan();
        pacMan.setVisible(true);
    }


}
