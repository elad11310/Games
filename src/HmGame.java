import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


class Word {
    String word;
    Level level;

    public Word(String word, Level level) {
        this.word = word;
        this.level = level;
    }
}

enum Level { // for words level
    EASY,
    MEDIUM,
    HARD
}

public class HmGame extends JFrame implements ActionListener {

    private Level level;
    private ArrayList<Word> wordsMap;
    private int turnsLeft;
    private int currectPress;
    private JPanel p;
    private String currentWord;


    public HmGame(Level level) {
        p = new JPanel(); // creating panel
        this.level = level;
        wordsMap = new ArrayList<>();
        turnsLeft = 0;
        currectPress = 0;
        init();

    }

    private void init() {

        // Window settings
        p.setLayout(null); // setting layout
        this.add(p);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // when we close the window the Thread will die.
        this.setSize(700, 700);
        this.setLocationRelativeTo(null); // setting the window in the center of the screen.
        //String PathOfFile = "mineSweeper.jpg";
        //this.setIconImage(new ImageIcon(PathOfFile).getImage()); // setting icon on the right.
        this.setResizable(false); // setting non resize for window
        this.setTitle("Hang Man"); // setting name

        // drawing buttons
        JButton btnNextWord = new JButton("Next Word");
        btnNextWord.setBounds(100, 200, 100, 30);
        btnNextWord.addActionListener(this);
        btnNextWord.setVisible(true);
        p.add(btnNextWord);

        int offsetX = 60, offsetY = 500;
        char c = 65;
        for (int i = 0; i <= 1; i++) {
            offsetY += 45;
            offsetX = 60;
            for (int j = 0; j <= 12; j++) {
                JButton button = new JButton();
                button.addActionListener(this); // setting action listener
                button.setBounds(offsetX, offsetY, 45, 45);
                offsetX += 45;
                button.setText("" + c);
                button.setFont(new Font("Arial", Font.BOLD, 10));
                c++;
                p.add(button); // add button to the panel


            }


            // init words.
        }
        try {
            readWords();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        nextWord();


    }

    private void readWords() throws FileNotFoundException {

        int level = 0;
        // pass the path to the file as a parameter

        File file =
                new File("Words.txt");

        Scanner sc = new Scanner(file);
        String[] words;
        while (sc.hasNextLine()) {
            words = sc.nextLine().split(",");
            switch (level) {
                case 0:
                    for (String word : words) {
                        wordsMap.add(new Word(word, Level.EASY));
                    }
                    break;
                case 1:
                    for (String word : words) {
                        wordsMap.add(new Word(word, Level.MEDIUM));
                    }
                    break;
                case 2:
                    for (String word : words) {
                        wordsMap.add(new Word(word, Level.HARD));
                    }
                    break;


            }
            level++;
        }
    }

    private void nextWord() {
        turnsLeft = 0;
        enableButtons();

        if (!wordsMap.isEmpty()) {
            for (Word word : wordsMap) {
                if (word.level == level) { // searching words by the level of the game
                    drawWord(word.word);
                    currentWord = word.word;
                    wordsMap.remove(word);
                    return;
                }


            }
            JOptionPane.showMessageDialog(null, "Well played,Game is over");
        }
    }

    private void enableButtons() {
        for (Component b : p.getComponents()) {
            if (b instanceof JButton) {
                b.setEnabled(true);
            }
        }
    }

    private void drawWord(String word) {
        int offSetX = 100, offSetY = 300;


        // first remove the current labels
        for (Component c : p.getComponents()) {
            if (c instanceof JLabel) {
                p.remove(c);
                this.repaint();

            }

        }
        // add new labels according to the new word
        for (int i = 0; i < word.length(); i++) {
            JLabel l = new JLabel("_");
            l.setBounds(offSetX, offSetY, 30, 50);
            l.setName("" + word.charAt(i));
            l.setVisible(true);
            p.add(l);
            offSetX += 30;
            System.out.println(l.getName());
        }

    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.draw(new Line2D.Float(600, 100, 600, 500));
        g2.draw(new Line2D.Float(550, 500, 650, 500));
        g2.draw(new Line2D.Float(500, 100, 600, 100));
        g2.draw(new Line2D.Float(500, 100, 500, 150));
        g2.draw(new Line2D.Float(600, 130, 550, 100));
        switch (turnsLeft) {
            case 1:
                g2.drawOval(450, 150, 100, 60);
                break;

            case 2:
                g2.drawOval(450, 150, 100, 60);
                g2.draw(new Line2D.Float(500, 210, 500, 350));
                break;
            case 3:
                g2.drawOval(450, 150, 100, 60);
                g2.draw(new Line2D.Float(500, 210, 500, 350));
                g2.draw(new Line2D.Float(500, 350, 470, 390));
                break;

            case 4:
                g2.drawOval(450, 150, 100, 60);
                g2.draw(new Line2D.Float(500, 210, 500, 350));
                g2.draw(new Line2D.Float(500, 350, 470, 390));
                g2.draw(new Line2D.Float(500, 350, 530, 390));
                break;
            case 5:
                g2.drawOval(450, 150, 100, 60);
                g2.draw(new Line2D.Float(500, 210, 500, 350));
                g2.draw(new Line2D.Float(500, 350, 470, 390));
                g2.draw(new Line2D.Float(500, 350, 530, 390));
                g2.draw(new Line2D.Float(500, 270, 460, 300));
                break;

            case 6:
                g2.drawOval(450, 150, 100, 60);
                g2.draw(new Line2D.Float(500, 210, 500, 350));
                g2.draw(new Line2D.Float(500, 350, 470, 390));
                g2.draw(new Line2D.Float(500, 350, 530, 390));
                g2.draw(new Line2D.Float(500, 270, 460, 300));
                g2.draw(new Line2D.Float(500, 270, 540, 300));
                break;
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        boolean isCurrect = false;
        String s = e.getActionCommand();

        if (s.equals("Next Word")) {
            nextWord();

            // checking a button click
        } else {
            Object source = e.getSource(); // casting to button
            if (source instanceof JButton) {

                // cast it to a button and take the text
                JButton btn = (JButton) e.getSource();
                String buttonClicked = btn.getText();
                // itearting through all the labels and check if the text was clicked on the button equals to the label name(that should represents the char hidden there)
                for (Component c : p.getComponents()) {
                    if (c instanceof JLabel) {
                        if (buttonClicked.toLowerCase().equals(((JLabel) c).getName())) {
                            currectPress++;
                            btn.setEnabled(false);
                            ((JLabel) c).setText(buttonClicked);
                            isCurrect = true;

                        }

                    }
                }

                // if there is a win
                if (currectPress == currentWord.length() && !wordsMap.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nice,next word");
                    nextWord();
                    currectPress = 0;

                }

                // if the user choice wrong
                if (!isCurrect) {
                    btn.setEnabled(false);
                    turnsLeft++;
                    repaint();

                    if (turnsLeft == 6) {
                        JOptionPane.showMessageDialog(null, "Game-Over");
                    }

                }
            }
        }
    }
}
