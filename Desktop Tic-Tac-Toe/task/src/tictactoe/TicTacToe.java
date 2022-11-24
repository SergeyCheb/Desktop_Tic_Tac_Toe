package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class TicTacToe extends JFrame implements ActionListener {
    public TicTacToe() {
        super("Tic-Tac-Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 450);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // добавил панель с меню и добавил туда вкладку "Game"
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu gameMenu = new JMenu("Game");
        gameMenu.setMnemonic(KeyEvent.VK_G);
        gameMenu.setName("MenuGame");
        menuBar.add(gameMenu);

        JMenuItem menuHumanHuman = new JMenuItem("Human vs Human");
        menuHumanHuman.setMnemonic(KeyEvent.VK_H);
        menuHumanHuman.setName("MenuHumanHuman");
        JMenuItem menuHumanRobot = new JMenuItem("Human vs Robot");
        menuHumanRobot.setMnemonic(KeyEvent.VK_R);
        menuHumanRobot.setName("MenuHumanRobot");
        JMenuItem menuRobotHuman = new JMenuItem("Robot vs Human");
        menuRobotHuman.setMnemonic(KeyEvent.VK_U);
        menuRobotHuman.setName("MenuRobotHuman");
        JMenuItem menuRobotRobot = new JMenuItem("Robot vs Robot");
        menuRobotRobot.setMnemonic(KeyEvent.VK_O);
        menuRobotRobot.setName("MenuRobotRobot");
        JMenuItem menuExit = new JMenuItem("Exit");
        menuExit.setMnemonic(KeyEvent.VK_X);
        menuExit.setName("MenuExit");

        gameMenu.add(menuHumanHuman);
        gameMenu.add(menuHumanRobot);
        gameMenu.add(menuRobotHuman);
        gameMenu.add(menuRobotRobot);
        gameMenu.addSeparator();
        gameMenu.add(menuExit);

        menuExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menuHumanHuman.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
                player1 = Player.Human;
                player2 = Player.Human;
                buttonPlayer1.setText("Human");
                buttonPlayer2.setText("Human");
                startGame();
            }
        });

        menuHumanRobot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
                player1 = Player.Human;
                player2 = Player.Computer;
                buttonPlayer1.setText("Human");
                buttonPlayer2.setText("Robot");
                startGame();
            }
        });

        menuRobotHuman.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
                player1 = Player.Computer;
                player2 = Player.Human;
                buttonPlayer1.setText("Robot");
                buttonPlayer2.setText("Human");
                startGame();
            }
        });

        menuRobotRobot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
                player1 = Player.Computer;
                player2 = Player.Computer;
                buttonPlayer1.setText("Robot");
                buttonPlayer2.setText("Robot");
                startGame();
            }
        });

        //upper panel with buttons of choice of mode of game
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 3, 0, 0));
        //MyJButton buttonPlayer1 = new MyJButton("Human", "ButtonPlayer1");
        buttonPlayer1 = new MyJButton("Human", "ButtonPlayer1");
        buttonPlayer1.addActionListener(this);
        topPanel.add(buttonPlayer1);
        buttonStartReset = new MyJButton("Start", "ButtonStartReset");
        buttonStartReset.addActionListener(this);
        topPanel.add(buttonStartReset);
        buttonPlayer2 = new MyJButton("Human", "ButtonPlayer2");
        buttonPlayer2.addActionListener(this);
        topPanel.add(buttonPlayer2);

        add(topPanel, BorderLayout.NORTH);

        JPanel mainField = new JPanel();
        mainField.setLayout(new GridLayout(3, 3, 0, 0));

        Font font = new Font("MyFont", Font.BOLD, 72);

        for (int i = 3, i1 = 0; i > 0; --i, ++i1) {
            for (int j = 'A', j1 = 0; j <= 'C'; ++j, ++j1) {
                arrMyButtons[i1][j1] = new MyJButton(" ",
                        "Button" + Character.toString(j) + Integer.toString(i));
                arrMyButtons[i1][j1].setFont(font);
                arrMyButtons[i1][j1].setFocusPainted(false);
                arrMyButtons[i1][j1].setEnabled(false);
                mainField.add(arrMyButtons[i1][j1]);
            }
        }
        add(mainField, BorderLayout.CENTER);

        JPanel statusBar = new JPanel();
        statusBar.setBackground(Color.gray);
        statusBar.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 2));

        jLabel = new JLabel("Game is not started");
        jLabel.setName("LabelStatus");
        jLabel.setForeground(Color.blue);

        statusBar.add(jLabel);

        add(statusBar, BorderLayout.SOUTH);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String act = e.getActionCommand();
        //in case of pushing "reset" button
        if (act.equals("Reset")) {
            resetGame();
            jLabel.setText("Game is not started");
        } else {
            // если нажата кнопка выбора игроков
            if (act.equals("Human")) {
                JButton tempJButton = (JButton) e.getSource();
                if (tempJButton.getName().equals("ButtonPlayer1")) {
                    player1 = Player.Computer;
                } else {
                    player2 = Player.Computer;
                }
                tempJButton.setText("Robot");
            }
            if (act.equals("Robot")) {
                JButton tempJButton = (JButton) e.getSource();
                if (tempJButton.getName().equals("ButtonPlayer1")) {
                    player1 = Player.Human;
                } else {
                    player2 = Player.Human;
                }
                tempJButton.setText("Human");
            }

            // it is needed to change content of status bar
            if (act.equals("Start")) {
                startGame();
            }

            if (act.equals(" ")) {
                JButton JB = (JButton) e.getSource();
                if (isX) {
                    JB.setText("X");
                    checkStateAfterMoving();
                    this.isX = !this.isX;
                    jLabel.setText("The turn of " + whoIsPlayer2() + " (O)");
                    if (player2.equals(Player.Computer) && this.state.equals(Filler.NOBODY)) {
                        robotMove();
                    }
                } else {
                    JB.setText("O");
                    checkStateAfterMoving();
                    this.isX = !this.isX;
                    jLabel.setText("The turn of " + whoIsPlayer1() + " (X)");
                    if (player1.equals(Player.Computer) && this.state.equals(Filler.NOBODY)) {
                        robotMove();
                    }
                }
            }
            checkStateAfterMoving();
        }
    }

    JLabel jLabel;
    private Boolean isX = true;
    MyJButton[][] arrMyButtons = new MyJButton[3][3];

    MyJButton buttonPlayer1;
    MyJButton buttonStartReset;
    MyJButton buttonPlayer2;

    Filler state = Filler.NOBODY;
    Player player1 = Player.Human;
    Player player2 = Player.Human;

    private void robotMove() {
        List<MyJButton> tempButtonList = Arrays.stream(arrMyButtons)
                .flatMap(buttonAr -> Arrays.stream(buttonAr))
                .filter(button -> button.getText().equals(" "))
                .collect(Collectors.toList());
        int tempSize = tempButtonList.size();
        if (tempSize != 0) {
            MyJButton tempButton = tempButtonList.get((new Random()).nextInt(tempSize));
            if (isX) {
                tempButton.setText("X");
                jLabel.setText("The turn of " + whoIsPlayer2() + " (O)");
            } else {
                tempButton.setText("O");
                jLabel.setText("The turn of " + whoIsPlayer1() + " (X)");
            }
            isX = !isX;
        }
    }

    private void checkStateAfterMoving() {
        // it is necessary to test if anybody wins on this step
        this.state = Functions.whoWin(arrMyButtons);
        // if anybody wins then it is needed to change content of status bar and
        // to disable button actions.
        if (!this.state.equals(Filler.NOBODY)) {
            //jLabel.setText(this.state.equals(Filler.X) ? "X wins" : "O wins");
            jLabel.setText(this.state.equals(Filler.X) ? "The " + whoIsPlayer1() + " (X) wins"  : "The " + whoIsPlayer2() + " (O) wins");
            for (MyJButton[] i : arrMyButtons) {
                for (MyJButton j : i) {
                    j.setEnabled(false);
                    j.removeActionListener(this);
                }
            }
        } else if (!Functions.isEmptyCells(arrMyButtons)) { //There are
            //no necessity to disable button actions with removeActionListener because after "reset"
            //button will be pushed, ones(actionListeners) will be deleted and added immediately again.
            jLabel.setText("Draw");
            for (MyJButton[] i : arrMyButtons) {
                for (MyJButton j : i) {
                    j.setEnabled(false);
                    j.removeActionListener(this);
                }
            }
        }
    }

    private void startGame() {
        // jLabel.setText("Game in progress");
        jLabel.setText("The turn of " + whoIsPlayer1() + " (X)");
        buttonStartReset.setText("Reset");
        state = Filler.NOBODY;
        for (MyJButton[] i : arrMyButtons) {
            for (MyJButton j : i) {
                j.addActionListener(this);
                j.setEnabled(true);
            }
        }
        buttonPlayer1.setEnabled(false);
        buttonPlayer2.setEnabled(false);

        //case when both players are robot.
        if (player1.equals(Player.Computer) && player2.equals(Player.Computer)) {
            while (this.state.equals(Filler.NOBODY) && !this.jLabel.getText().equals("Draw")) {
                robotMove();
                checkStateAfterMoving();
            }
        } else if (player1.equals(Player.Computer) && player2.equals(Player.Human)) {
            robotMove();
        }
    }

    private void resetGame() {
        for (MyJButton[] i : arrMyButtons) {
            for (MyJButton j : i) {
                j.setText(" ");
                j.removeActionListener(this);
                j.setEnabled(false);
            }
        }
        buttonStartReset.setText("Start");
        isX = true;
        buttonPlayer1.addActionListener(this);
        buttonPlayer2.addActionListener(this);
        buttonPlayer1.setEnabled(true);
        buttonPlayer2.setEnabled(true);
    }

    private String whoMove() {
        return isX ? "(X)" : "(O)";
    }

    private String whoIsPlayer1()
    {
        return player1.equals(Player.Human) ? "Human Player" : "Robot Player";
    }

    private String whoIsPlayer2()
    {
        return player2.equals(Player.Human) ? "Human Player" : "Robot Player";
    }


}