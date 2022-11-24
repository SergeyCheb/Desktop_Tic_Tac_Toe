package tictactoe;

import javax.swing.*;

public class MyJButton extends JButton {

    MyJButton() {
        super();
    }

    MyJButton(String text, String name) {
        super(text);
        super.setName(name);
    }

}

