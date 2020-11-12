import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;


public class WBoard extends JPanel implements ActionListener {

    private final int B_WIDTH = 805; // window width
    private final int B_HEIGHT = 800; // window height
    private int DELAY = 50;

    private boolean leftDirection = false; // for moving left the bar
    private boolean rightDirection = false; // for moving right the bar
    private boolean BallUpDirection = false; // moving the ball up
    private boolean BallDownDirection = false;// moving the ball down
    private boolean BallLeftDirection = false; // moving the ball left
    private boolean BallRightDirection = false; // moving the ball right


    private boolean inGame = true; // to know if the game still running

    private int BRICKS = 80;
    private int barX = B_WIDTH / 2 - 100;
    private int barY = B_WIDTH - 20;
    private int ballX = barX + 50;
    private int ballY = barY - 25;


    private final Brick bricks[] = new Brick[BRICKS]; // storing the bricks

    private Timer timer;


    class Brick {
        private int x;
        private int y;

        public Brick(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean checkHit(int ballX, int ballY) {
            int i;
            for (i = this.x - 10; i <= this.x + 50; i++) {
                if (i == ballX && (this.y + 50 == ballY || this.y == ballY)) {
                    return true;
                }

            }
            return false;
        }
    }

    public WBoard() {

        initBoard();
    }

    private void initBoard() {



        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        initGame();


    }

    private void initGame() {
        int OFFSET_X = 0;
        int OFFSET_Y = 100;

        for (int z = 0; z < BRICKS; z++) {
            if (z % 16 == 0 && z != 0) {
                OFFSET_Y += 50;
                OFFSET_X = 0;
            }
            bricks[z] = new Brick(OFFSET_X, OFFSET_Y);
            OFFSET_X += 50;
        }

        timer = new Timer(DELAY, this); // starting the timer which calls actionPerformed until it stops
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkCollision();
            moveBall();
            moveBar();
        }

        repaint();
    }

    private void moveBar() {

        if (leftDirection) {
            if (barX >= 20) {
                barX -= 20;
                leftDirection = false;
            } else {
                barX = 20;
            }

        }
        else if (rightDirection) {
            if (barX <= B_WIDTH - 100) {
                barX += 20;
                rightDirection = false;
            } else {
                barX = B_WIDTH - 100;
            }

        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        final int BRICKWIDTH = 50;
        final int BRICKHEIGHT = 20;
        final int BALLKWIDTH = 15;
        final int BALLKHEIGHT = 15;
        int z;

        if (inGame) {
            g.setColor(Color.white);

            Font small = new Font("Helvetica", Font.BOLD, 14);
            // drawing bricks
//            Random rand = new Random();
//            float R = rand.nextFloat();
//            float G = rand.nextFloat();
//            float B = rand.nextFloat();
//            Color randomColor = new Color(R,G,B);
            g.setColor(Color.white);
            for (z = 0; z < bricks.length; z++) {
                if (bricks[z] != null)
                    g.fill3DRect(bricks[z].x, bricks[z].y, BRICKWIDTH, BRICKHEIGHT, true);

            }
            // draw bar
            g.drawLine(barX, barY, barX + 100, barY);
            g.setColor(Color.blue);
            // draw ball
            g.drawOval(ballX, ballY, BALLKWIDTH, BALLKHEIGHT);

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


    private void moveBall() {

        // if the ball comes from down left
        if (BallUpDirection && BallLeftDirection) {
            ballY -= 10;
            ballX -= 8;
            // if the ball comes from down right
        } else if (BallUpDirection && BallRightDirection) {
            ballY -= 10;
            ballX += 8;

            // if the ball comes up  left
        } else if (BallDownDirection && BallLeftDirection) {
            ballY += 10;
            ballX -= 8;

            // if the ball comes from up right
        } else if (BallDownDirection && BallRightDirection) {
            ballY += 10;
            ballX += 8;

            // if the ball comes from down
        } else if (BallUpDirection) {
            ballY -= 10;
            // if the ball comes from up
        } else if (BallDownDirection) {
            ballY += 10;
        }
    }

    private void checkCollision() {

        if (BRICKS % 5 == 0) {

        }
        // check if we hit a brick

        for (int i = 0; i < BRICKS; i++) {
            if (bricks[i] != null && bricks[i].checkHit(ballX, ballY)) {

                // check from which angle we hit the brick
                if (BallDownDirection && BallRightDirection && ballY > bricks[i].y) {
                    BallLeftDirection = true;
                    BallRightDirection = false;
                } else if (BallUpDirection && BallRightDirection) {
                    BallUpDirection = false;
                    BallDownDirection = true;

                } else if (BallUpDirection && BallLeftDirection) {
                    BallUpDirection = false;
                    BallDownDirection = true;

                } else if (BallDownDirection && BallRightDirection) {
                    BallDownDirection = false;
                    BallUpDirection = true;
                } else if (BallDownDirection && BallLeftDirection) {
                    BallDownDirection = false;
                    BallUpDirection = true;
                } else if (BallUpDirection) {
                    BallDownDirection = true;
                    BallUpDirection = false;
                }
                //BRICKS--;
                bricks[i] = null;
            }

        }


        // check if we hit the bar
        for (
                int i = barX - 10;
                i < barX + 100; i++) {
            if (ballX == i && ballY == barY - 25) {
                BallDownDirection = false;
                BallUpDirection = true;
                BallRightDirection = false;
                BallLeftDirection = false;

                // if we hit the left side of the bar
                if (ballX >= barX - 10 && ballX <= barX + 30) {
                    BallLeftDirection = true;

                }
                // if we hit the right side of the bar
                else if (ballX >= barX + 60) {
                    BallRightDirection = true;
                }


            }
        }

        // if we hit the ceiling
        if (ballY < 0) {
            BallUpDirection = false;
            BallDownDirection = true;


        }
        // if we hit the left side of the board
        if (ballX == 0) {
            //if the ball comes from down and left
            if (BallUpDirection && BallLeftDirection) {
                BallLeftDirection = false;
                BallRightDirection = true;
            }
            // if the ball comes from up and left
            else if (BallDownDirection && BallLeftDirection) {
                BallRightDirection = true;
                BallLeftDirection = false;
            }
        }
        // if we hit the right side of the board
        if (ballX >= B_WIDTH) {
            // if the ball comes from down and right
            if (BallUpDirection && BallRightDirection) {
                BallRightDirection = false;
                BallLeftDirection = true;

            }
            // if the ball comes from up and right
            else if (BallDownDirection && BallRightDirection) {
                BallRightDirection = false;
                BallLeftDirection = true;

            }
        }

        if (ballY > B_HEIGHT) {
            inGame = false;
        }

    }


    private class TAdapter extends KeyAdapter {


        @Override
        public void keyPressed(KeyEvent e) {


            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;

            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
            }


        }
    }
}
