import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;

public class PacManBoard extends JPanel implements ActionListener, MouseListener {


    private final int DOT_SIZE = 10; // the dots which the pacMan eats size
    private final int RAND_POS = 70; // for random dots atm
    private final int B_WIDTH = 1000; // window width
    private final int B_HEIGHT = 750; // window height
    private final int ROWS = 63;
    private final int COLS = 83;
    private int DELAY = 80; // not final - leaving an option to change the monsters movement speed.
    protected int pacX;
    protected int pacY;


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
    protected final Point points[][] = new Point[63][83];


    private Timer timer;
    private Image pacUp;
    private Image pacDown;
    private Image pacRight;
    private Image pacLeft;
    private Image pacMovingUp;
    private Image pacMovingDown;
    private Image pacMovingRight;
    private Image pacMovingLeft;
    private Image redMonsterDown;
    private Image redMonsterUp;
    private Image redMonsterLeft;
    private Image redMonsterRight;

    private Ghost redGhost;

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        System.out.println(x + " " + y);
        System.out.println("okkk");
    }

    @Override
    public void mousePressed(MouseEvent e) {


    }

    @Override
    public void mouseReleased(MouseEvent e) {


    }

    @Override
    public void mouseEntered(MouseEvent e) {


    }

    @Override
    public void mouseExited(MouseEvent e) {


    }


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

    public PacManBoard(int stage) throws IOException {
        initBoard();
    }

    public PacManBoard() {

    }


    private void initBoard() throws IOException {


        addKeyListener(new PacManBoard.TAdapter());
        addMouseListener(this);
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();

    }

    private void loadImages() {

        // for pac man pics with mouth open
        ImageIcon iid = new ImageIcon("images/pacman-DOWN.png");
        pacDown = iid.getImage();
        pacDown = pacDown.getScaledInstance(50, 50, Image.SCALE_DEFAULT);


        iid = new ImageIcon("images/pacman-UP.png");
        pacUp = iid.getImage();
        pacUp = pacUp.getScaledInstance(50, 50, Image.SCALE_DEFAULT);

        iid = new ImageIcon("images/pacman-LEFT.png");
        pacLeft = iid.getImage();
        pacLeft = pacLeft.getScaledInstance(50, 50, Image.SCALE_DEFAULT);

        iid = new ImageIcon("images/pacman-RIGHT.png");
        pacRight = iid.getImage();
        pacRight = pacRight.getScaledInstance(50, 50, Image.SCALE_DEFAULT);

        // for pac man pics with mouth shut

        iid = new ImageIcon("images/pacman-LEFTFULL.png");
        pacMovingLeft = iid.getImage();
        pacMovingLeft = pacMovingLeft.getScaledInstance(50, 50, Image.SCALE_DEFAULT);

        iid = new ImageIcon("images/pacman-FULLRIGHT.png");
        pacMovingRight = iid.getImage();
        pacMovingRight = pacMovingRight.getScaledInstance(50, 50, Image.SCALE_DEFAULT);

        iid = new ImageIcon("images/pacman-UPFULL.png");
        pacMovingUp = iid.getImage();
        pacMovingUp = pacMovingUp.getScaledInstance(50, 50, Image.SCALE_DEFAULT);

        iid = new ImageIcon("images/pacman-DOWNFULL.png");
        pacMovingDown = iid.getImage();
        pacMovingDown = pacMovingDown.getScaledInstance(50, 50, Image.SCALE_DEFAULT);

        // monsters images

        iid = new ImageIcon("images/redU.png");
        redMonsterUp = iid.getImage();
        redMonsterUp = redMonsterUp.getScaledInstance(50, 50, Image.SCALE_DEFAULT);

        iid = new ImageIcon("images/redD.png");
        redMonsterDown = iid.getImage();
        redMonsterDown = redMonsterDown.getScaledInstance(50, 50, Image.SCALE_DEFAULT);

        iid = new ImageIcon("images/redR.png");
        redMonsterRight = iid.getImage();
        redMonsterRight = redMonsterRight.getScaledInstance(50, 50, Image.SCALE_DEFAULT);

        iid = new ImageIcon("images/redL.png");
        redMonsterLeft = iid.getImage();
        redMonsterLeft = redMonsterLeft.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
    }


    private void initGame() throws IOException {

        //locateDots();
        creatingPoints();
        timer = new Timer(DELAY, this); // starting the timer which calls actionPerformed until it stops
        timer.start();

        pacMan pacMan = new pacMan(100); // this thread is for the moving of Pac-man
        Thread t = new Thread(pacMan);
        t.start();
//
        redGhost = new Ghost(new chaseAggresive(), pacX, pacY);
        Thread thread = new Thread(redGhost);
        thread.start();



    }

    private void creatingPoints() throws IOException {
        int offsetX = 0, offsetY = 0, x = 0, y = 0;
        String[] toSplit;
        File file = new File("stage1.txt");
        boolean ifFirst = true;
        BufferedReader br = null;
        br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null) {
            toSplit = st.split(",");
            offsetX = Integer.parseInt(toSplit[0]);
            offsetY = Integer.parseInt(toSplit[1]);
            if (ifFirst) {
                x = offsetX / 10;
                y = offsetY / 10;
                ifFirst = false;
                points[offsetY / 10 - y][offsetX / 10 - x] = new Point(offsetX, offsetY, false);
            } else {
                System.out.println((offsetY / 10 - y) + " " + (offsetX / 10 - x));
                points[offsetY / 10 - y][offsetX / 10 - x] = new Point(offsetX, offsetY, false);
            }

        }

        offsetX = x * 10;
        offsetY = y * 10;
        for (int i = 0; i < points.length; i++) {
            if (i != 0) {
                offsetX = x * 10;
                offsetY += 10;
            }
            for (int j = 0; j < COLS; j++) {
                // for corners.
                if (points[i][j] == null) {
                    points[i][j] = new Point(offsetX, offsetY, true);
                    // putting neighbours
                    if (i - 1 >= 0) {
                        points[i][j].neighbours.put("UP", points[i - 1][j]);
                    }
                    if (i + 1 < ROWS) {
                        points[i][j].neighbours.put("DOWN", points[i + 1][j]);
                    }
                    if (j + 1 < COLS) {
                        points[i][j].neighbours.put("RIGHT", points[i][j + 1]);
                    }
                    if (j - 1 >= 0) {
                        points[i][j].neighbours.put("LEFT", points[i][j - 1]);
                    }

                }
                offsetX += 10;
            }


        }
        //starting pac man spot
        pacX = points[1][1].x;
        pacY = points[1][1].y;

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
        // drawing the obstacles.
        g.setColor(Color.blue);
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (points[i][j].isOk == false) {
                    g.drawLine(points[i][j].x, points[i][j].y, points[i][j].x + 10, points[i][j].y + 10);
                }
            }
        }


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
        g.drawImage(redMonsterLeft, redGhost.x-60, redGhost.y-100, this);
      //  g.drawImage(redMonsterLeft, pacX-60, pacY-100, this);
        // painting the dots
        g.setColor(Color.white);
//        for (int i = 0; i < dots.length; i++) {
//            if (dots[i].x >= 0)
//                g.drawOval(dots[i].x, dots[i].y, 10, 10);
//        }

        System.out.println("PacX : " + pacX + "PacY" + pacY);
       // System.out.println("PacX : " + redGhost.x + "PacY" + redGhost.y);

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        // movePacMan(); i used a Thread i did , it works also.
        //checkCollision();
        // checkEat();
        repaint();

    }

    private boolean checkCollision() {


        // -5 because we start from x=50

        int x = pacX / 10 - 5;
        // -4 because we start from y=40

        int y = (pacY / 10 - 8);

        // System.out.println("Pac X = " +points[y][x].x + "Pac Y = " + points[y][x].y);

        // all the y-1 , y+5 and etc its because the pacMan real X and Real Y on top left of the picture!!!!!!!!!!
        if (upDirection) {
            if (points[y - 1][x].isOk && points[y - 1][x + 1].isOk && points[y - 1][x + 2].isOk && points[y - 1][x + 3].isOk && points[y - 1][x + 4].isOk) {
                return false; // no collision
            } else {
                return true; // yes collision
            }
        }

        if (downDirection) {
            if (points[y + 5][x].isOk && points[y + 5][x + 1].isOk && points[y + 5][x + 2].isOk && points[y + 5][x + 3].isOk && points[y + 5][x + 4].isOk) {
                return false; // no collision
            } else {
                return true; // yes collision
            }
        }
        if (leftDirection) {
            if (points[y][x - 1].isOk && points[y + 1][x - 1].isOk && points[y + 2][x - 1].isOk && points[y + 3][x - 1].isOk && points[y + 4][x - 1].isOk) {
                return false; // no collision.
            } else {
                return true; // yes collision
            }
        }
        if (rightDirection) {
            if (points[y][x + 5].isOk && points[y + 1][x + 5].isOk && points[y + 2][x + 5].isOk && points[y + 3][x + 5].isOk && points[y + 4][x + 5].isOk) {
                return false; // no collision
            } else {
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
//
//    private class Monster implements Runnable {
//
//
//        @Override
//        public void run() {
//            try {
//
//                while (true) {
////                    Ghost ghost = new Ghost(new chaseAggresive(),250,250);
////                    ghost.chaseBehaviour.chase();
//                    monsterRedX +=10;
//                    monsterRedY +=10;
//
//
//                    Thread.sleep(140);
//                }
//
//            } catch (Exception ex) {
//                ex.printStackTrace();
//                System.exit(0);
//            }
//        }
//
//    }


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
