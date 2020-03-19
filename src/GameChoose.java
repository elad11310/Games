import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameChoose extends JFrame implements ActionListener{

    int chose;

    public GameChoose() {
        init();
    }

    private void init() {
        // Window settings
        JPanel p = new JPanel(); // creating panel
        p.setLayout(null); // setting layout
        p.setBackground(Color.white); // setting background
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // when we close the window the Thread will die.
        this.setSize(700, 700);
        this.setLocationRelativeTo(null); // setting the window in the center of the screen.
        this.setResizable(false); // setting non resize for window
        this.setTitle("Games"); // setting name

        // Scroll bar
        JTextField textfield = new JTextField("0000");
        add(textfield);

        JScrollPane scrollPane =
                new JScrollPane(
                        new JList<>(
                                new String[]{
                                        "4", "5",
                                        "6", "7",
                                        "8", "9",
                                        "10",
                                }));
        scrollPane.getVerticalScrollBar().addAdjustmentListener(
                e -> textfield.setText(String.format("%04d", e.getValue())));


        scrollPane.setBounds(100, 400, 50, 100);
        scrollPane.setVisible(true);
        p.add(scrollPane);

        scrollPane.addMouseListener(new MouseAdapter() {
            // int clicksCount=0l
            public void mouseClicked(MouseEvent e) {
                System.out.println("Ok");
                if (e.getClickCount() == 1)
                    System.out.println("Ok");
                String s = scrollPane.getName();
                System.out.println(s);
            }
        });


        // Labels
        JLabel labelMS = new JLabel();
        JLabel labelHM = new JLabel();
        JLabel labelHello = new JLabel("ENTER A GAME :");

        labelHM.setIcon(new ImageIcon("HangManImg.png"));
        labelMS.setIcon(new ImageIcon("mineSweeperImg.png"));

        Dimension size = labelMS.getPreferredSize(); // size of the mineSweeper icon
        Dimension size2 = labelHM.getPreferredSize(); // size of the hang man icon

        labelHello.setBounds(200, 40, 300, 30);
        labelMS.setBounds(100, 200, size.width, size.height);
        labelHM.setBounds(320, 180, size2.width, size2.height);

        labelHello.setFont(new

                Font("Berlin Sans FB Demi", Font.BOLD, 27));

        labelMS.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                MsGame ms = new MsGame(10);
                ms.setVisible(true);
                dispose();
            }
        });

        labelHM.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                HmGame hg = new HmGame(Level.EASY);
                hg.setVisible(true);
                dispose();
            }
        });


        p.add(labelMS);
        p.add(labelHello);
        p.add(labelHM);
        this.add(p);


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof  JScrollPane){
            System.out.println("OK");
        }

    }
}

