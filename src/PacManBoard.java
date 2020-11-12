import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class PacManBoard extends JPanel implements ActionListener {


    private final int DOT_SIZE = 10; // the dots which the pacMan eats size
    private final int RAND_POS = 70; // for random dots atm
    private final int B_WIDTH = 1000; // window width
    private final int B_HEIGHT = 750; // window height
    private final int ROWS = 63;
    private final int COLS = 83;
    private int stage;
    private int DELAY = 80; // not final - leaving an option to change the monsters movement speed.
    private int pacX = 450;
    private int pacY = 450;
    private int monsterX = 200;
    private int monsterY = 200;


    private boolean leftDirection = false; // for moving left
    private boolean rightDirection = false; // for moving right
    private boolean upDirection = false;  // for moving up
    private boolean downDirection = false; // for moving down
    private boolean inGame = true; // to know if the game still running
    private boolean wasUp; // after we moved up , want the angle of the mouth to stay up even if we stopped moving
    private boolean wasDown;
    private boolean wasRight;
    private boolean wasLeft;
    private boolean isOpenMoth = true; // for opening and closing the mouth of the pacman while moving.


    private final Dot dots[] = new Dot[20]; // storing the dots
    private final Point points[][] = new Point[63][83];

    private Timer timer;
    private Image pacUp;
    private Image pacDown;
    private Image pacRight;
    private Image pacLeft;
    private Image pacMovingUp;
    private Image pacMovingDown;
    private Image pacMovingRight;
    private Image pacMovingLeft;


    class Dot {
        private int x;
        private int y;

        public Dot(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean checkHit() {
            int i, j;
            for (i = this.x - 25; i <= this.x + 25; i++) {
                for (j = this.y - 25; j <= this.y + 25; j++) {
                    if (i == pacX && j == pacY) {
                        return true;
                    }
                }


            }
            return false;
        }
    }

    class Point {
        int x;
        int y;
        boolean isOk;
        HashMap<String, Point> neighbours;

        public Point(int x, int y, boolean isOk) {
            this.x = x;
            this.y = y;
            this.isOk = isOk;
            neighbours = new HashMap<>();
        }

        public String toString() {
            return "PointX = " + this.x + " PointY = " + this.y + " isOk " + this.isOk;

        }
    }

    public PacManBoard(int stage) {
        this.stage = stage;
        initBoard();
    }


    private void initBoard() {


        addKeyListener(new PacManBoard.TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();

    }

    private void loadImages() {

        // for pac man pics with mouth open
        ImageIcon iid = new ImageIcon("pacman-DOWN.png");
        pacDown = iid.getImage();
        pacDown = pacDown.getScaledInstance(50, 50, Image.SCALE_DEFAULT);

        ImageIcon iia = new ImageIcon("pacman-UP.png");
        pacUp = iia.getImage();
        pacUp = pacUp.getScaledInstance(50, 50, Image.SCALE_DEFAULT);

        ImageIcon iih = new ImageIcon("pacman-LEFT.png");
        pacLeft = iih.getImage();
        pacLeft = pacLeft.getScaledInstance(50, 50, Image.SCALE_DEFAULT);

        ImageIcon iif = new ImageIcon("pacman-RIGHT.png");
        pacRight = iif.getImage();
        pacRight = pacRight.getScaledInstance(50, 50, Image.SCALE_DEFAULT);

        // for pac man pics with mouth shut
        ImageIcon iib = new ImageIcon("pacman-LEFTFULL.png");
        pacMovingLeft = iib.getImage();
        pacMovingLeft = pacMovingLeft.getScaledInstance(50, 50, Image.SCALE_DEFAULT);

        ImageIcon iic = new ImageIcon("pacman-FULLRIGHT.png");
        pacMovingRight = iic.getImage();
        pacMovingRight = pacMovingRight.getScaledInstance(50, 50, Image.SCALE_DEFAULT);

        ImageIcon iio = new ImageIcon("pacman-UPFULL.png");
        pacMovingUp = iio.getImage();
        pacMovingUp = pacMovingUp.getScaledInstance(50, 50, Image.SCALE_DEFAULT);

        ImageIcon iie = new ImageIcon("pacman-DOWNFULL.png");
        pacMovingDown = iie.getImage();
        pacMovingDown = pacMovingDown.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
    }


    private void initGame() {

        locateDots();
        creatingPoints();
        timer = new Timer(DELAY, this); // starting the timer which calls actionPerformed until it stops
        timer.start();

        pacMan myRunnable = new pacMan(100); // this thread is for the moving of Pac-man
        Thread t = new Thread(myRunnable);
        t.start();
//
//        Monster monster = new Monster();
//        Thread thread = new Thread(monster);
//        thread.start();


    }

    private void creatingPoints() {
        int offsetX = 40;
        int offsetY = 30;
        int count = 0;
        for (int i = 0; i < points.length; i++) {
            if (i != 0) {
                offsetX = 40;
                offsetY += 10;
            }
            for (int j = 0; j < COLS; j++) {
                // for corners.
                if (i == 0 || j == 0 || i == ROWS - 1 || j == COLS - 1 || i==18 &&(j>=17 &&j<=25) || j==25 && (i>=18 && i<=26)){
                    points[i][j] = new Point(offsetX, offsetY, false);
                } else {
                    points[i][j] = new Point(offsetX, offsetY, true);
                    // putting neighbours
                    points[i][j].neighbours.put("UP",points[i-1][j]);
                    points[i][j].neighbours.put("DOWN",points[i+1][j]);
                    points[i][j].neighbours.put("RIGHT",points[i][j+1]);
                    points[i][j].neighbours.put("DOWN",points[i][j-1]);
                }
                offsetX += 10;
              //  System.out.println("pointX : " + points[i][j].x + "PointY" + points[i][j].y + " " + points[i][j].isOk + " " + count++);
            }


        }
    }

    private void locateDots() {
        int r, p;
        int i = 0;

        for (; i < dots.length; i++) {
            do {
                r = (int) (Math.random() * RAND_POS);
                p = (int) (Math.random() * RAND_POS);
            } while (r > 900 || p > 700);
            dots[i] = new Dot(r * DOT_SIZE, p * DOT_SIZE);
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.blue);
        // if its stage 1
//        if (stage == 1) {
//            g.drawLine(40, 400, 150, 400);
//            g.drawArc(62, 392, 90, 20, 0, 10);
//            g.drawLine(40, 405, 145, 405);
//            g.drawArc(57, 398, 90, 20, 0, 10);
//            g.drawLine(153, 405, 153, 470);
//            g.drawLine(148,410,148,465);
//            g.drawArc(57, 457, 90, 20, 0, 10);
//            g.drawLine(145,467,45,467);
//            g.drawArc(63, 463, 89, 20, 0, 10);
//            g.drawLine(149,474,45,474);
//            g.drawArc(-46, 462, 90, 25, 0, -15);
//            g.drawLine(42,478,42,500);
//            g.drawArc(-50, 454, 100, 100, 80, -27);
//        }


        // for this drawing we need 80 vertexes in each row and 60 in each column , total :4800
        g.drawLine(50, 40, 900, 40);
        g.drawLine(50, 40, 50, 690);
        g.drawLine(50, 690, 900, 690);
        g.drawLine(900, 690, 900, 40);

        g.drawLine(250, 250, 300, 250);
        g.drawLine(300, 250, 300, 300);
        //current pacMan


        // for the angle of the mouth of the pac-man pic.
        // if its open
        if (isOpenMoth) {
            if (upDirection)
                g.drawImage(pacUp, pacX, pacY, this);
            else if (downDirection)
                g.drawImage(pacDown, pacX, pacY, this);
            else if (rightDirection)
                g.drawImage(pacRight, pacX, pacY, this);
            else if (leftDirection)
                g.drawImage(pacLeft, pacX, pacY, this);
            else if (wasUp)
                g.drawImage(pacUp, pacX, pacY, this);
            else if (wasDown)
                g.drawImage(pacDown, pacX, pacY, this);
            else if (wasRight)
                g.drawImage(pacRight, pacX, pacY, this);
            else if (wasLeft)
                g.drawImage(pacLeft, pacX, pacY, this);

            isOpenMoth = false;
        }
        // if its closed
        else {

            if (upDirection)
                g.drawImage(pacMovingUp, pacX, pacY, this);
            else if (downDirection)
                g.drawImage(pacMovingDown, pacX, pacY, this);
            else if (rightDirection)
                g.drawImage(pacMovingRight, pacX, pacY, this);
            else if (leftDirection)
                g.drawImage(pacMovingLeft, pacX, pacY, this);
            else if (wasUp)
                g.drawImage(pacUp, pacX, pacY, this);
            else if (wasDown)
                g.drawImage(pacDown, pacX, pacY, this);
            else if (wasRight)
                g.drawImage(pacRight, pacX, pacY, this);
            else if (wasLeft)
                g.drawImage(pacLeft, pacX, pacY, this);

            isOpenMoth = true;
        }

        // drawing monsters
        // g.drawOval(monsterX,monsterY,40,40);
        // painting the dots
        g.setColor(Color.white);
        for (int i = 0; i < dots.length; i++) {
            if (dots[i].x >= 0)
                g.drawOval(dots[i].x, dots[i].y, 10, 10);
        }

        System.out.println("PacX : " + pacX + "PacY" + pacY);


    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // movePacMan(); i used a Thread i did , it works also.
        //checkCollision();
        checkEat();
        repaint();

    }

    private boolean checkCollision() {


        // -5 because we start from x=50

        int x = pacX / 10 - 4;
        // -4 because we start from y=40

        int y = (pacY / 10 - 3);
        // System.out.println("X = " + x +" Y = " + y);

        // System.out.println("Pac X = " +points[y][x].x + "Pac Y = " + points[y][x].y);


        if(upDirection){
            if(points[y-1][x].isOk){
                return false; // no collision
            }
            else {
                return true; // yes collision
            }
        }

        if(downDirection){
            if(points[y+1][x].isOk){
                return false; // no collision
            }
            else {
                return true; // yes collision
            }
        }
        if(leftDirection){
            if(points[y][x-1].isOk){
                return false; // no collision
            }
            else {
                return true; // yes collision
            }
        }
        if(rightDirection){
            if(points[y][x+1].isOk){
                return false; // no collision
            }
            else {
                return true; // yes collision
            }
        }

        return false;
    }


    private void checkEat() {

        for (int i = 0; i < 20; i++) {
            if (dots[i].checkHit()) {
                dots[i].x = -1;
                dots[i].y = -1;
            }
        }
    }

    private void movePacMan() {

        if (leftDirection && upDirection) {
            pacY -= 30;

        } else if (leftDirection && downDirection) {
            pacY += 30;

        } else if (rightDirection && upDirection) {
            pacY -= 30;

        } else if (rightDirection && downDirection) {
            pacY += 30;

        } else if (leftDirection) {
            pacX -= 30;

        } else if (upDirection) {
            pacY -= 30;

        } else if (rightDirection) {
            pacX += 30;

        } else if (downDirection) {
            pacY += 30;
        }
    }

    private class TAdapter extends KeyAdapter {


        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT) leftDirection = true;
            if (key == KeyEvent.VK_RIGHT) rightDirection = true;
            if (key == KeyEvent.VK_UP) upDirection = true;
            if (key == KeyEvent.VK_DOWN) downDirection = true;


        }

        @Override
        public void keyReleased(KeyEvent e) {
            wasUp = false;
            wasDown = false;
            wasRight = false;
            wasLeft = false;


            if (e.getKeyCode() == KeyEvent.VK_LEFT) {

                leftDirection = false;
                wasLeft = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

                rightDirection = false;
                wasRight = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                upDirection = false;
                wasUp = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                downDirection = false;
                wasDown = true;
            }


        }


    }

    private class Monster implements Runnable {

        @Override
        public void run() {
            try {

                while (true) {

                    if (monsterX < pacX) {
                        monsterX += 10;
                    } else if (monsterX > pacX) {
                        monsterX -= 10;
                    }

                    if (monsterY > pacY) {
                        monsterY -= 10;
                    } else if (monsterY < pacY) {
                        monsterY += 10;
                    }

                    Thread.sleep(140);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(0);
            }
        }

    }


    private class pacMan implements Runnable {

        private int var;

        public pacMan(int var) {
            this.var = var;
        }

        public void run() {
            try {

                while (true) {

                    if (!checkCollision()) {
                        if (leftDirection && upDirection) {
                            pacY -= 10;

                        } else if (leftDirection && downDirection) {
                            pacY += 10;

                        } else if (rightDirection && upDirection) {
                            pacY -= 10;

                        } else if (rightDirection && downDirection) {
                            pacY += 10;

                        } else if (leftDirection) {
                            pacX -= 10;

                        } else if (upDirection) {
                            pacY -= 10;

                        } else if (rightDirection) {
                            pacX += 10;

                        } else if (downDirection) {
                            pacY += 10;
                        }

                    }
                    Thread.sleep(70);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(0);
            }
        }
    }

}
