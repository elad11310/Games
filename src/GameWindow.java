import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameWindow extends JFrame implements ActionListener {

    private  double mines=0;
    private int count=0;
    private int size;
    private JButton matrix[][];
    private boolean[][] mat;
    private Color[] colors = {Color.blue, Color.green, Color.red, new Color(50, 0, 119), new Color(150, 0, 2),
            Color.pink, Color.yellow, Color.black}; // setting arr of colors.;


    public GameWindow(final int size) {
        this.size = size;
        matrix = new JButton[size][size];
        mat = new boolean[size][size];

        init();


    }

    private void init() {

        JPanel p = new JPanel(); // creating panel
        p.setLayout(new GridLayout(size, size)); // setting layout
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // when we close the window the Thread will die.
        this.setSize(700, 700);
        this.setLocationRelativeTo(null); // setting the window in the center of the screen.
        String PathOfFile = "mineSweeper.jpg";
        this.setIconImage(new ImageIcon(PathOfFile).getImage()); // setting icon on the right.
        this.setResizable(false); // setting non resize for window
        this.setTitle("MineSweeper"); // setting name

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
        setMines();

    }

    private void setMines() {
         mines = 0.4* (size * size); // setting mines number - 40% of the board

        for (int i = 0; i < mines; i++) {
            int rowRand = (int) (Math.random() * size);
            int colRand = (int) (Math.random() * size);
            mat[rowRand][colRand] = true;
        }


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        count++; // check how many good selections
        if(count == size*size -mines ){
            // win
        }
        Object source = e.getSource(); // casting to button
        if (source instanceof JButton) {
            JButton btn = (JButton) source;
            String loc[] = btn.getToolTipText().split(",");
            int i = Integer.parseInt(loc[0]); // takes coordinates of the button
            int j = Integer.parseInt(loc[1]);

            if (mat[i][j] == true) {
                btn.setText("*");

            }
            //System.out.println(btn.getToolTipText());
            else {
                int numOfMines = getNumOfMines(i, j);
                if (numOfMines == 0) {
                    checkMines(btn, i, j);
                } else {
                    btn.setText("" + numOfMines);
                    btn.setFont(new Font("Arial",Font.BOLD,17));
                    btn.setForeground(colors[Integer.parseInt(btn.getText())]);
                }

            }


        }


    }

    private void checkMines(JButton btn, int i, int j) {

        if (getNumOfMines(i, j) != 0) {
            return;
        }
        btn.setText("0");
        btn.setForeground(colors[0]);
        btn.setFont(new Font("Arial",Font.BOLD,17));
/*
        if (j - 1 >= 0 && getNumOfMines(i, j - 1) == 0 ) {
            checkMines(matrix[i][j -1], i, j - 1);
        }
        if (i-1 >=0 && getNumOfMines(i-1, j ) == 0 ) {
            checkMines(matrix[i-1][j], i-1, j );
        }*/
        if (j + 1 < mat.length && getNumOfMines(i, j + 1) == 0 ) {
            checkMines(matrix[i][j + 1], i, j + 1);
        }
        if (
                i + 1 < mat.length && getNumOfMines(i + 1, j) == 0 ) {
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
