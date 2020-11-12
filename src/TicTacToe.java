import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




public class TicTacToe extends JFrame{



    public TicTacToe(){
        initGUI();
    }
    private void initGUI() {



        add(new BoardTicTacToe()); // setting the board

        setResizable(false); // cant resize the window
        pack(); // The pack() method is defined in Window class in Java and it sizes the frame so that all its contents are at or above their preferred sizes.
        setTitle("TicTacToe"); // setting the window title
        setLocationRelativeTo(null); // setting window in the middle of the display
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // finishing the thread when quiting the game

    }

//    private char player = 'X', opponent = 'O';
//    Player p1 = new Player(player,"elad",0);
//    Player p2 = new Player(opponent,"Computer",0);
//
//    private char board[][];
//    private JButton ButtonsMatrix[][];
//    private boolean inGame;
//    private int turn;
//    private Timer timer;
//    private int DELAY = 140;
//    private String winner;
//    private boolean vsComputer;
//
//
//    public TicTacToe() {
//        board = new char[3][3];
//        ButtonsMatrix = new JButton[3][3];
//        inGame = true;
//        // i start for now.
//        turn = 0;
//
//        initBoard();
//        initFrame();
//
//
//    }
//
//    private void initFrame() {
//
//         Window settings
//         JPanel p = new JPanel(); // creating panel
//         p.setLayout(new GridLayout(3, 3)); // setting layout
//         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // when we close the window the Thread will die.
//         this.setSize(400, 400);
//         this.setLocationRelativeTo(null); // setting the window in the center of the screen.
//         String PathOfFile = "mineSweeper.jpg";
//        this.setIconImage(new ImageIcon(PathOfFile).getImage()); // setting icon on the right.
//        this.setResizable(false); // setting non resize for window
//         this.setTitle("TicTacToe"); // setting name
//
//
//        // init matrix of buttons
//        for (int r = 0; r < 3; r++) {
//            for (int c = 0; c < 3; c++) {
//                JButton button = new JButton();
//                ButtonsMatrix[r][c] = button;
//                button.addActionListener(this); // setting action listener
//                button.setToolTipText(r + "," + c); // setting id for button
//               // button.setText(board[r][c]+"");
//                p.add(button); // add button to the panel
//            }
//
//        }
////        JLabel background=new JLabel(new ImageIcon("ticTacToe2.jpg"));
////        background.setLayout(new FlowLayout());
////        this.add(background);
//
//        //this.add(p);
//
//    }
//
//
//    private void initBoard() {
//        for (int i = 0; i < board.length; i++) {
//            for (int j = 0; j < board[i].length; j++) {
//                board[i][j] = '_';
//            }
//        }
//
//
//        if(vsComputer) {
//            timer = new Timer(DELAY, this); // starting the timer which calls actionPerformed until it stops
//            timer.start();
//        }
//
//    }
//
//    private boolean movesLeft() {
//        for (int i = 0; i < board.length; i++) {
//            for (int j = 0; j < board[i].length; j++) {
//                // if there is an empty place
//                if (board[i][j] == '_') {
//                    return true;
//                }
//            }
//        }
//        // in case the board is full.
//        return false;
//
//    }
//
//    private int checkWin() {
//
//        // checking rows
//
//        for (int row = 0; row < 3; row++) {
//            // if we find a winner in a row
//            if (board[row][0] == board[row][1] && board[row][1] == board[row][2] && board[row][0] != '_') {
//                // check if its me
//                if (board[row][0] == player) {
//                    return 10;
//                }
//                // its the computer
//                else
//                    return -10;
//            }
//        }
//
//        // checking cols
//
//        for (int col = 0; col < 3; col++) {
//            // if we find a winner in a row
//            if (board[0][col] == board[1][col] && board[1][col] == board[2][col] && board[0][col] != '_') {
//                // check if its me
//                if (board[0][col] == player) {
//                    return 10;
//                }
//                // its the computer
//                else
//                    return -10;
//            }
//        }
//
//        // checking diagonals
//
//        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '_') {
//            // check if its me
//            if (board[0][0] == player) {
//                return 10;
//            }
//            // its the computer
//            else
//                return -10;
//        }
//
//        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != '_') {
//            // check if its me
//            if (board[0][2] == player) {
//                return 10;
//            }
//            // its the computer
//            else
//                return -10;
//        }
//        // in case there is no winner
//        return 0;
//
//    }
//
//    private int minimax(char board[][], boolean isMax, int depth) {
//
//        // we use depth for example if i can win in 2 moves instead of 4 , so i want to take the 2 (10-2=8 ,10-4=6)
//        int calc = checkWin();
//
//        // if i won
//        if (calc == 10) {
//            return calc-depth;
//        }
//        // if computer won
//        if (calc == -10) {
//            return calc+depth;
//        }
//        // if there is no winner
//        if (!movesLeft()) {
//            return 0;
//        }
//
//        if (isMax) {
//            int best = -1000;
//            for (int i = 0; i < 3; i++) {
//                for (int j = 0; j < 3; j++) {
//                    // if there is an empty place
//
//                    if (board[i][j] == '_') {
//
//                        // checking my possible option after the computers turn
//                        board[i][j] = player;
//                        best = Max(best, minimax(this.board, !isMax, depth + 1));
//
//
//                        // undo the move
//                        board[i][j] = '_';
//                    }
//
//                }
//
//            }
//
//            return best;
//        } else {
//            int best = 1000;
//            for (int i = 0; i < 3; i++) {
//                for (int j = 0; j < 3; j++) {
//                    // if there is an empty place
//
//                    if (board[i][j] == '_') {
//
//                        // checking my possible option after the computers turn
//                        board[i][j] = opponent;
//                        best = Min(best, minimax(this.board, !isMax, depth + 1));
//
//
//                        // undo the move
//                        board[i][j] = '_';
//                    }
//
//                }
//
//            }
//
//            return best;
//        }
//
//    }
//
//    private Move bestMove() {
//
//        int val = 1000;
//        Move move = new Move();
//        move.row = -1;
//        move.col = -1;
//
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                // if there is an empty place
//                if (board[i][j] == '_') {
//
//                    // mark the future computer step and opens the tree until the result
//                    board[i][j] = opponent;
//                    // checking whats the best move for the computer
//                    int moveVal = minimax(this.board, true, 0);
//                    // unmarking the step that we marked for computer choice
//                    board[i][j] = '_';
//
//
//                    // if we find a better move
//                    if (moveVal < val) {
//                        val = moveVal;
//                        move.row = i;
//                        move.col = j;
//                    }
//                }
//            }
//        }
//
//
//        return move;
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//
//        if (inGame) {
//            if (turn == 1) {
//                Move m = bestMove();
//                // System.out.println(m.row + " " + m.col);
//                board[m.row][m.col] = opponent;
//                ButtonsMatrix[m.row][m.col].setText(opponent + "");
//                turn = 0;
//            }
//
//            // only if its my turn.
//            else if (turn == 0) {
//                Object source = e.getSource(); // casting to button
//                if (source instanceof JButton) {
//                    JButton btn = (JButton) source;
//                    String loc[] = btn.getToolTipText().split(",");
//                    int i = Integer.parseInt(loc[0]); // takes coordinates of the button
//                    int j = Integer.parseInt(loc[1]);
//                    if(board[i][j]=='_') {
//                        board[i][j] = player;
//                        ButtonsMatrix[i][j].setText(player + "");
//                        turn = 1;
//                    }
//                }
//
//
//            }
//            // checking if there are no place in the board anymore, so determine winner or loser or a tie (draw).
//
//
//                int ans = checkWin();
//                if(ans == 10){
//                    inGame=false;
//                    winner = p1.name;
//                }
//                else if(ans==-10){
//                    inGame=false;
//                    winner = p2.name;
//                }
//                else if(ans==0 && !movesLeft())
//                {
//                    inGame=false;
//                    winner="draw";
//                }
//                if(ans==0 && !movesLeft()){
//                    JOptionPane.showMessageDialog(null, "Its a draw!");
//                }
//                else if (ans!=0){
//                    JOptionPane.showMessageDialog(null, winner + " Won!");
//                }
//
//            }
//
//
//    }
//
//    private int Max(int first, int second) {
//        return first > second ? first : second;
//    }
//
//    private int Min(int first, int second) {
//        return first < second ? first : second;
//    }
}