import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MsGame extends JFrame implements ActionListener {

    private double mines = 0;
    private int count = 0;
    private int size;
    private JButton matrix[][];
    private boolean[][] mat;
    private Color[] colors = {Color.blue, Color.green, Color.red, new Color(50, 0, 119), new Color(150, 0, 2),
            Color.pink, Color.yellow, Color.black}; // setting arr of colors.;


    public MsGame(final int size) {
        this.size = size;
        matrix = new JButton[size][size];
        mat = new boolean[size][size];

        init();


    }

    private void init() {

        // Window settings
        JPanel p = new JPanel(); // creating panel
        p.setLayout(new GridLayout(size, size)); // setting layout
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // when we close the window the Thread will die.
        this.setSize(700, 700);
        this.setLocationRelativeTo(null); // setting the window in the center of the screen.
        String PathOfFile = "mineSweeper.jpg";
        this.setIconImage(new ImageIcon(PathOfFile).getImage()); // setting icon on the right.
        this.setResizable(false); // setting non resize for window
        this.setTitle("MineSweeper"); // setting name

        // Menu bar settings
        MenuBar menu = new MenuBar(); // creating the setting menu bar
        this.setMenuBar(menu);
        Menu m = new Menu("File"); // creating and setting menu.
        menu.add(m);
        MenuItem item = new MenuItem("New Game");
        MenuItem item2 = new MenuItem("Chose Level");
        m.add(item);
        m.add(item2);
        item.addActionListener(this); // setting the listener to this menuItem.
        item2.addActionListener(this);
        //this.addMouseListener(this); // now listening for mouse events

        // init matrix of buttons
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                JButton button = new JButton();
                matrix[r][c] = button;
                button.addActionListener(this); // setting action listener
                button.setToolTipText(r + "," + c); // setting id for button
                p.add(button); // add button to the panel
            }

        }
        this.add(p);
        // setting mines on board
        setMines();

    }

    private void setMines() {
        mines = 0.4 * (size * size); // setting mines number - 40% of the board

        for (int i = 0; i < mines; i++) {
            int rowRand = (int) (Math.random() * size);
            int colRand = (int) (Math.random() * size);
            while (mat[rowRand][colRand] == true) { // make sure there are no multiple mines in same spot.
                rowRand = (int) (Math.random() * size);
                colRand = (int) (Math.random() * size);
            }
            mat[rowRand][colRand] = true;
            System.out.println(rowRand + "," + colRand);
        }


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        count++; // check how many good selections

        String s = e.getActionCommand(); // getting the string of the clicked item.
        if (s.equals("New Game")) {
            JOptionPane.showMessageDialog(null, "Get Ready!");
            cleanBoard();
            setMines();
            count=0;

        } else {
            if (count == (int) (size * size - mines)) {
                JOptionPane.showMessageDialog(null, "You Win!");
                showBoard();
            }
            Object source = e.getSource(); // casting to button
            if (source instanceof JButton) {
                JButton btn = (JButton) source;
                String loc[] = btn.getToolTipText().split(",");
                int i = Integer.parseInt(loc[0]); // takes coordinates of the button
                int j = Integer.parseInt(loc[1]);

                if (mat[i][j] == true) {
                    btn.setText("*");
                    JOptionPane.showMessageDialog(null, "Game Over!");
                    showBoard();

                } else {
                    int numOfMines = getNumOfMines(i, j);
                    if (numOfMines == 0) {
                        checkMines(btn, i, j);
                    } else {
                        btn.setText("" + numOfMines);
                        btn.setFont(new Font("Arial", Font.BOLD, 17));
                        btn.setForeground(colors[Integer.parseInt(btn.getText())]);
                    }

                }


            }


        }
    }

    private void cleanBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                mat[i][j] = false;
                matrix[i][j].setText("");
                matrix[i][j].setForeground(Color.black);
            }
        }
    }


    private void showBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (mat[i][j] == true) {
                    matrix[i][j].setText("*");

                } else {
                    matrix[i][j].setText("" + getNumOfMines(i, j));
                    matrix[i][j].setFont(new Font("Arial", Font.BOLD, 17));
                    matrix[i][j].setForeground(colors[Integer.parseInt(matrix[i][j].getText())]);
                }
            }
        }
    }

    private void checkMines(JButton btn, int i, int j) {
        count++;
        if (getNumOfMines(i, j) != 0) {
            return;
        }
        btn.setText("0");
        btn.setForeground(colors[0]);
        btn.setFont(new Font("Arial", Font.BOLD, 17));
/*
        if (j - 1 >= 0 && getNumOfMines(i, j - 1) == 0 ) {
            checkMines(matrix[i][j -1], i, j - 1);
        }
        if (i-1 >=0 && getNumOfMines(i-1, j ) == 0 ) {
            checkMines(matrix[i-1][j], i-1, j );
        }*/
        if (j + 1 < mat.length && getNumOfMines(i, j + 1) == 0) {
            checkMines(matrix[i][j + 1], i, j + 1);
        }
        if (
                i + 1 < mat.length && getNumOfMines(i + 1, j) == 0) {
            checkMines(matrix[i + 1][j], i + 1, j);
        }


    }

    private int getNumOfMines(int i, int j) {
        int countMines = 0;
        for (int q = i - 1; q <= i + 1; q++) {
            for (int p = j - 1; p <= j + 1; p++) {
                if (q >= 0 && p >= 0 && !mat[i][j] && q < mat.length && p < mat[0].length && mat[q][p] == true) {
                    countMines++;
                }
            }
        }
        return countMines;
    }
}
