import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel implements ActionListener {

    private final int B_WIDTH = 400; // window width
    private final int B_HEIGHT = 400; // window height
    private final int DOT_SIZE = 10; // each dot in the window size
    private final int ALL_DOTS = 1600; // total dots
    private final int RAND_POS = 29; // for random
    private int DELAY = 140; // not final - leaving an option to change the snake movement speed.
    private boolean speedSnake = true;
    private final int LINE = 50;
    private int score = 0;
    private int lives = 3;
    private boolean stillAlive=false;

    private final int x[] = new int[ALL_DOTS]; // storing the snake X location
    private final int y[] = new int[ALL_DOTS]; // storing the snake Y location

    private int dots; // to count the length of the snake
    private int apple_x; // the X location of the apple
    private int apple_y; // the Y location of the apple


    private boolean leftDirection = false; // for moving left
    private boolean rightDirection = true; // for moving right
    private boolean upDirection = false;  // for moving up
    private boolean downDirection = false; // for moving down
    private boolean inGame = true; // to know if the game still running

    private Timer timer;
    private Image ball; // image for the body of the snake
    private Image apple; // image of the apple
    private Image head; // image of the head of the snake
    private Image heart; // image of the lives

    public Board() {

        initBoard();
    }

    private void initBoard() {


        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();


    }

    private void initGame() {

        dots = 3; // we start the snake in length of 3

        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10; // the head in 50,50 , second in 40,50 , third in 30,50 .....
            y[z] = 100;
        }

        locateApple();

        timer = new Timer(DELAY, this); // starting the timer which calls actionPerformed until it stops
        timer.start();

    }


    private void locateApple() {

        // random for the X location of the apple
        int r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * DOT_SIZE));

        // random for the Y location of the apple

        do {
            r = (int) (Math.random() * RAND_POS);
            apple_y = ((r * DOT_SIZE));
        } while (apple_y <= 50);
    }

    private void loadImages() {

        ImageIcon iid = new ImageIcon("dot.png");
        ball = iid.getImage();

        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("head.png");
        head = iih.getImage();

        ImageIcon iif = new ImageIcon("heart.png");
        heart = iif.getImage();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {
            //System.out.println("ok");
            checkApple();
            checkCollision();
            move();
        }

        repaint();

    }

    private void move() {

        for (int z = dots; z > 0; z--) { // each dot of the snake goes to the place of the ones before its (we start from the end of the snake), as it moves.
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (leftDirection) { // move left
            x[0] -= DOT_SIZE;
        }

        if (rightDirection) { // more right
            x[0] += DOT_SIZE;
        }

        if (upDirection) { // move up
            y[0] -= DOT_SIZE;
        }

        if (downDirection) { // move down
            y[0] += DOT_SIZE;
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        int z;
        int loc = 300;
        if (inGame) {
            g.setColor(Color.white);

            Font small = new Font("Helvetica", Font.BOLD, 14);
            //FontMetrics metr = getFontMetrics(small);
            g.setFont(small);
            g.drawString("Score:", 20, 25);
            g.drawString("Lives:", 250, 25);
            g.drawString(score + "", 100, 25);
            g.drawLine(0, LINE, B_WIDTH, LINE); // draw line
            g.drawImage(apple, apple_x, apple_y, this); // draw apple


            for (z = 0; z < lives; z++) {
                g.drawImage(heart,loc,15,this);
                loc+=30;
            }

            for (z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                } else {
                    g.drawImage(ball, x[z], y[z], this);
                }
            }


            Toolkit.getDefaultToolkit().sync();

        } else {

            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }

    private void checkCollision() {
        // to check if the head of the snake hits its body. so we need at least snake in size of 4.
        for (int z = dots; z > 0; z--) {
            if (z > 4 && x[0] == x[z] && y[0] == y[z]) {
                lives--;
                reload("");
            }
        }


        if (y[0] >= B_HEIGHT) { // if the snake hits the bottom of the board
            lives--;
            reload("down");
        }

        if (y[0] < LINE) { // if the snake hits the top of the board
            lives--;
            reload("up");
        }

        if (x[0] >= B_WIDTH) { // if the snake hits the the right side of the board
            lives--;
            reload("right");
        }

        if (x[0] < 0) { // if the snake hits the left size of the board
            lives--;
            reload("left");
        }

        if(lives==0){
            inGame=false;
        }

        if (!inGame) { // if there was a collision game is over
            timer.stop();
        }

    }

    private void reload(String mode){
        // returning the snake to its original start mode and angel.
        switch (mode){
            case "up":
                upDirection=false;
                break;
            case "down":
                downDirection=false;
                break;
            case "left":
                leftDirection=false;
            case"": // by a collision with the snake body
                upDirection=false;
                downDirection=false;
                leftDirection=false;
                break;

        }
        // stopping the game
        timer.stop();
        // relocating the sanke in its original start place.
        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10; // the head in 50,50 , second in 40,50 , third in 30,50 .....
            y[z] = 100;
        }
        stillAlive=true; // for starting the timer again after we lost 1 life and the game stopped.

        rightDirection=true; // facing the snake to rhe right side
    }
    private void checkApple() {

        if (x[0] == apple_x && y[0] == apple_y) { // if the head of the snake ate an apple
            dots++;
            locateApple(); // create new apple on board.
            speedSnake = true;
            score++;
        }

        if (dots % 10 == 0 && speedSnake) { // increasing the snake speed
            timer.setDelay(timer.getDelay() - 30);
            speedSnake = false;
        }
    }


    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            if(stillAlive){
                timer.start();
                stillAlive=false;
            }

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

        }
    }
}
